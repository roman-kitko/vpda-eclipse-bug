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
package org.vpda.common.comps.ui;

import org.vpda.common.command.CommandEvent;
import org.vpda.common.comps.shortcuts.keyboard.KeyStroke;

/**
 * Event holding the whole component
 * 
 * @author kitko
 *
 */
public final class KeyStrokeCommandEvent implements CommandEvent {
    private final Object actionId;
    private final KeyStroke source;

    private static final long serialVersionUID = -3518352565764067471L;

    @Override
    public Object getActionId() {
        return actionId;
    }

    @Override
    public KeyStroke getSource() {
        return source;
    }

    /**
     * @param actionId
     * @param source
     */
    public KeyStrokeCommandEvent(Object actionId, KeyStroke source) {
        super();
        this.actionId = actionId;
        this.source = source;
    }

}
