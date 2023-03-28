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

/**
 * Constants related to ViewProviderComponets
 * 
 * @author kitko
 *
 */
public final class ACConstants {
    private ACConstants() {
    }

    /** Root container */
    public static final String ROOT_CONTAINER = "root";

    /** Main toolbar container */
    public static final String MAIN_TOOLBAR_CONTAINER = "mainToolbar";

    /**
     * Key to component properties. It is used on client side to customize factory
     * that creates/updates real ui component from {@link AbstractComponent}
     */
    public static final String CUSTOM_VIEWPROVIDER_ONE_COMPONENT_FACTORY = "customViewProviderOneComponentFactory";
}
