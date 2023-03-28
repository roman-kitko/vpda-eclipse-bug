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
package org.vpda.abstractclient.viewprovider.ui.commands;

import org.vpda.common.command.CommandEvent;
import org.vpda.common.comps.ui.AbstractComponent;
import org.vpda.common.comps.ui.Component;

/**
 * Holder event for {@link AbstractComponent}
 * 
 * @author kitko
 *
 */
public final class ProviderComponentCommandEvent implements CommandEvent {
    private static final long serialVersionUID = 3562606682582857753L;
    private final Object actionId;
    private final Component comp;

    @Override
    public Object getActionId() {
        return actionId;
    }

    @Override
    public Component getSource() {
        return comp;
    }

    /**
     * @param actionId
     * @param comp
     */
    public ProviderComponentCommandEvent(Object actionId, Component comp) {
        super();
        this.actionId = actionId;
        this.comp = comp;
    }

    /**
     * @param comp
     */
    public ProviderComponentCommandEvent(Component comp) {
        this(comp.getId(), comp);
    }

}
