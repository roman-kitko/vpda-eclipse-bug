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

import java.io.Serializable;

import org.vpda.common.types.EnabledObject;

/**
 * Enabled command that executes its delegate only when enabled, otherwise
 * returns null
 * 
 * @author kitko
 *
 * @param <T>
 */
public final class EnabledCommand<T> implements Command<T>, EnabledObject, Serializable {
    private static final long serialVersionUID = 4004063387538326458L;
    private final Command<T> delegate;
    private boolean enabled;
    private boolean disabledOnce;

    /**
     * @param delegate
     */
    public EnabledCommand(Command<T> delegate) {
        super();
        this.delegate = delegate;
        this.enabled = true;
    }

    /**
     * @param delegate
     * @param enabled
     */
    public EnabledCommand(Command<T> delegate, boolean enabled) {
        super();
        this.delegate = delegate;
        this.enabled = enabled;
    }

    @Override
    public T execute(CommandExecutor executor, CommandExecutionEnv env, CommandEvent event) throws Exception {
        if (!enabled) {
            enabled = disabledOnce;
            return null;
        }
        T result = delegate.execute(executor, env, event);
        return result;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public void setEnabled(boolean isEnabled) {
        this.enabled = isEnabled;
    }

    /**
     * @return the disableOnce
     */
    public boolean isDisabledOnce() {
        return disabledOnce;
    }

    /**
     * Disable just one time
     * 
     * @param disabledOnce
     */
    public void setDisabledOnce(boolean disabledOnce) {
        this.disabledOnce = disabledOnce;
        this.enabled = !disabledOnce;
    }

}
