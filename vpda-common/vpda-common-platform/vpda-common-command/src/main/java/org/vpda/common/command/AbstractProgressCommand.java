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
package org.vpda.common.command;

import java.util.List;

import org.vpda.common.util.ProgressNotifier;
import org.vpda.common.util.ProgressObserver;
import org.vpda.common.util.ProgressSupport;

/**
 * Base abstraction for progress commands It handles comamnd progression notify
 * operation
 * 
 * @author kitko
 * @param <T>
 *
 */
public abstract class AbstractProgressCommand<T> implements ProgressCommand<T> {
    /** Support for progress notification */
    private transient final ProgressSupport progressSupport;

    /**
     * Creates new AbstractProgressCommand
     */
    protected AbstractProgressCommand() {
        super();
        progressSupport = new ProgressSupport();
    }

    @Override
    public final T execute(CommandExecutor executor, CommandExecutionEnv env, CommandEvent event) throws Exception {
        try {
            T result = doExecute(executor, env, event);
            if (canNotifyOnExecutionFinished()) {
                progressSupport.notifyExecutionFinished(result, null);
            }
            return result;
        }
        catch (Exception e) {
            progressSupport.notifyExecutionFinished(null, e);
            throw e;
        }
    }

    /**
     * @return ProgressSupport
     */
    protected synchronized ProgressNotifier getProgressNotifier() {
        return progressSupport;
    }

    @Override
    public synchronized void addProgressObserver(ProgressObserver observer) {
        progressSupport.addProgressObserver(observer);
    }

    @Override
    public synchronized void removeProgressObserver(ProgressObserver observer) {
        progressSupport.removeProgressObserver(observer);
    }

    @Override
    public synchronized List<ProgressObserver> getObservers() {
        return progressSupport.getObservers();
    }

    /**
     * Do real execution
     * 
     * @param executor
     * @param env
     * @param event
     * @return task result
     * @throws Exception
     */
    protected abstract T doExecute(CommandExecutor executor, CommandExecutionEnv env, CommandEvent event) throws Exception;

    /**
     * Test if we can notify that we are finished
     * 
     * @return true if we should notify our observers that execution just finished
     */
    protected boolean canNotifyOnExecutionFinished() {
        return true;
    }

}
