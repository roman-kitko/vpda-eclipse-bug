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

/**
 * Target of progress notification
 * 
 * @author kitko
 *
 */
public interface ProgressNotifier {

    /**
     * Notifies target that progress has changed
     * 
     * @param progress
     * @param unit
     * @param message
     * @param exception
     */
    public void notifyProgressChanged(int progress, ProgressChangeUnit unit, String message, Exception exception);

    /**
     * Notifies target that execution has finished
     * 
     * @param result
     * @param exception
     */
    public void notifyExecutionFinished(Object result, Exception exception);

    /**
     * Sets maximum progress
     * 
     * @param maxProgress
     */
    public void setMaxProgress(int maxProgress);

    /**
     * Notify that execution was canceled
     */
    public void notifyExecutionCanceled();
}
