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

import org.vpda.common.util.exceptions.VPDARuntimeException;

/**
 * Runnable command wrapper
 * 
 * @author kitko
 * @param <T>
 */
public final class RunnableCommand<T> implements Runnable {
    private final Command<T> delegate;
    private final CommandExecutor executor;
    private final CommandExecutionEnv env;
    private final CommandEvent event;

    /**
     * @param delegate
     * @param executor
     * @param env
     * @param event
     */
    public RunnableCommand(Command<T> delegate, CommandExecutor executor, CommandExecutionEnv env, CommandEvent event) {
        this.delegate = delegate;
        this.executor = executor;
        this.env = env;
        this.event = event;
    }

    @Override
    public void run() {
        try {
            executor.executeCommand(delegate, env, event);
        }
        catch (Exception e) {
            throw new VPDARuntimeException("Error running command", e);
        }
    }
}
