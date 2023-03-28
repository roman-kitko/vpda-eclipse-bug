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
package org.vpda.common.comps;

/**
 * Member is abstraction of any item like component or field inside container
 * 
 * @author kitko
 *
 */
public interface Member {
    /**
     * @return local id that is unique only inside the container
     */
    public String getLocalId();

    /**
     * @return id that should be unique inside the context where whole container is
     *         used
     */
    public String getId();

    /**
     * @return id of container this member is part of
     */
    public String getParentId();

    /**
     * @return listener support
     */
    public MemberListenerSupport getMemberListenerSupport();

    /**
     * @return true if this item is single item
     */
    public default boolean isSingleMember() {
        return true;
    }

    /**
     * @return true if this item is container of other items
     */
    public default boolean isMemberContainer() {
        return false;
    }

}
