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
package org.vpda.abstractclient.core.ui;

/**
 * Constants for menu. These constants are only some common menu constants
 * regardless of any application.
 * 
 * @author kitko
 *
 */
public final class MenuConstants {
    private MenuConstants() {
    }

    /** Path to default main menu localization node */
    public static final String MAIN_MENU_LOC_NODE_PATH = "menu/mainMenu";

    /** System menu id */
    public static final String SYSTEM_MENU_ID = "system";

    /** Tools menu id */
    public static final String VIEW_MENU_ID = "view";

    /** Windows menu id */
    public static final String WINDOW_MENU_ID = "window";

    /** Admin menu id */
    public static final String ADMIN_MENU_ID = "admin";

    /** help menu id */
    public static final String HELP_MENU_ID = "help";

    /** Logout menu item id */
    public static final String LOGOUT_MENU_ITEM_ID = "system.logout";

    /** Shutdown menu item id */
    public static final String SHUTDOWN_MENU_ITEM_ID = "system.shutdown";

    /** Preferences menu */
    public static final String PREFERENCES_MENU_ID = "user.preferences";

}
