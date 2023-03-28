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

/**
 * Simple CommandEvent with passed actionId and source
 * 
 * @author kitko
 *
 */
public final class BasicCommandEvent implements CommandEvent {
    private static final long serialVersionUID = -8346148352000320044L;
    private final Object source;
    private final Object actionId;

    /**
     * Creates BasicCommandEvent with actionId and source
     * 
     * @param actionId
     * @param source
     * 
     */
    public BasicCommandEvent(Object actionId, Object source) {
        this.actionId = actionId;
        this.source = source;
    }

    /**
     * Creates BasicCommandEvent with actionId and null source
     * 
     * @param actionId
     */
    public BasicCommandEvent(Object actionId) {
        this.actionId = actionId;
        this.source = null;
    }

    @Override
    public Object getActionId() {
        return actionId;
    }

    @Override
    public Object getSource() {
        return source;
    }

}
