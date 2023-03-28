/**
 * View provider driven applications - java application framework for developing RIA
 * Copyright (C) 2009-2022 Roman Kitko, Slovakia
 *
 * Licensed under the GNU GENERAL PUBLIC LICENSE, Version 3.0 (the "License").
 * You may not use this file except in compliance with the License.
 * You may obtain a copy of the License at http://www.gnu.org/licenses/gpl.html
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.vpda.common.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CancellationException;

/**
 * Support for observable objects implementations
 * 
 * @author kitko
 *
 */
public final class ProgressSupport implements ProgressObservable, ProgressNotifier {

    private List<ProgressObserver> observers;
    private boolean canceled;

    @Override
    public synchronized void addProgressObserver(ProgressObserver observer) {
        if (observers == null) {
            observers = new ArrayList<ProgressObserver>(2);
        }
        if (!observers.contains(observer)) {
            observers.add(observer);
        }
    }

    @Override
    public synchronized List<ProgressObserver> getObservers() {
        return observers != null ? new ArrayList<ProgressObserver>(observers) : Collections.<ProgressObserver>emptyList();
    }

    @Override
    public synchronized void removeProgressObserver(ProgressObserver observer) {
        if (observers != null) {
            observers.remove(observer);
        }

    }

    private synchronized void checkCancel() {
        if (canceled) {
            throw new CancellationException("Execution canceled");
        }
    }

    @Override
    public void notifyExecutionFinished(Object result, Exception exception) {
        checkCancel();
        if (observers != null) {
            for (ProgressObserver progressObserver : observers) {
                progressObserver.executionFinished(result, exception);
            }
        }
    }

    @Override
    public void notifyProgressChanged(int progress, ProgressChangeUnit unit, String message, Exception exception) {
        checkCancel();
        if (observers != null) {
            for (ProgressObserver progressObserver : observers) {
                progressObserver.progressChanged(progress, unit, message, exception);
            }
        }
    }

    @Override
    public void setMaxProgress(int maxProgress) {
        checkCancel();
        if (observers != null) {
            for (ProgressObserver progressObserver : observers) {
                progressObserver.setMaxProgress(maxProgress);
            }
        }
    }

    @Override
    public void notifyExecutionCanceled() {
        this.canceled = true;

    }

}
