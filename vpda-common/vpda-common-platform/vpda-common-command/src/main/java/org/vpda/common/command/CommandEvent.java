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
/**
 * 
 */
package org.vpda.common.command;

import java.io.Serializable;

/**
 * Event that is cause of command execution. Event holds its source and some
 * action Id. Source is typically some component which fired the command
 * execution and actionId could be some discriminator(SUBMIT,CANCEL) about
 * action that is fired.
 * 
 * @author kitko
 *
 */
public interface CommandEvent extends Serializable {
    /**
     * @return source of command execution. This could be some component id,real
     *         component...
     */
    public Object getSource();

    /**
     * 
     * @return action Id (SUBMIT,CANCEL...)
     */
    public Object getActionId();
}
