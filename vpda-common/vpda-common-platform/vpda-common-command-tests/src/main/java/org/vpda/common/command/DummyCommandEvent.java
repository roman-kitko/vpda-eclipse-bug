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

import org.vpda.common.command.CommandEvent;

/**
 * Command event without any information about its actionId or source. This
 * event can be accessed in singleton way.
 * 
 * @author kitko
 *
 */
public final class DummyCommandEvent implements CommandEvent {
    private static final long serialVersionUID = 1838347785206828564L;
    private static DummyCommandEvent instance = new DummyCommandEvent();

    @Override
    public Object getActionId() {
        return null;
    }

    @Override
    public Object getSource() {
        return null;
    }

    /**
     * @return instance
     */
    public static DummyCommandEvent getInstance() {
        return instance;
    }

    private DummyCommandEvent() {
    }

}
