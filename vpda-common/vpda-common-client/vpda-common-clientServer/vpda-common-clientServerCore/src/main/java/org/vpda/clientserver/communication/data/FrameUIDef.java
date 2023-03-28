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
package org.vpda.clientserver.communication.data;

import java.io.Serializable;

import org.vpda.common.comps.ui.ToolBarAC;
import org.vpda.common.comps.ui.menu.MenuBarAC;

/**
 * UI definition of frame
 * 
 * @author kitko
 *
 */
public final class FrameUIDef implements Serializable {
    private static final long serialVersionUID = -256031785033744655L;

    private final MenuBarAC menuBarAC;

    private final ToolBarAC toolbar;

    /**
     * @param menuBarAC
     * @param toolbar
     */
    public FrameUIDef(MenuBarAC menuBarAC, ToolBarAC toolbar) {
        super();
        this.menuBarAC = menuBarAC;
        this.toolbar = toolbar;
    }

    /**
     * @return MenuBarAC
     */
    public MenuBarAC getMainMenuBar() {
        return menuBarAC;
    }

    /**
     * @return ToolBarAC
     */
    public ToolBarAC getToolBar() {
        return toolbar;
    }

}
