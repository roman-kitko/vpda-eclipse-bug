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

import org.vpda.common.comps.MemberUtils;

/**
 * Components utilities
 *
 */
public final class ComponentUtils {
    private ComponentUtils() {
    }

    /**
     * Computes component id from groupId and localId
     * 
     * @param groupId
     * @param localComponetId
     * @return id of component
     */
    public static String getComponentId(String groupId, String localComponetId) {
        return MemberUtils.getMemberId(groupId, localComponetId);
    }

    /**
     * Computes local id from component
     * 
     * @param componentId
     * @return localId
     */
    public static String getLocalComponentId(String componentId) {
        return MemberUtils.getLocalMemberId(componentId);
    }

    /**
     * Determine if localId of component is valid
     * 
     * @param localId
     * @return true if local id is valid
     */
    public static boolean isValidComponentLocalId(String localId) {
        return true;
    }

}
