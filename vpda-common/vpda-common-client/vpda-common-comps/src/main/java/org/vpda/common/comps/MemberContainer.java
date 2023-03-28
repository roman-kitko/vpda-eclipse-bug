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

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Container of members
 * 
 * @author kitko
 * @param <T> type of members
 *
 */
public interface MemberContainer<T extends Member> extends Member {
    /**
     * @return all members in container
     */
    public List<T> getMembers();

    /**
     * @return members by id
     */
    public Map<String, ? extends T> getMembersMapping();

    /**
     * @param id
     * @return member by id
     */
    public T getMember(String id);

    /**
     * @return all members local ids
     */
    public List<String> getMembersLocalIds();

    /**
     * @return all members ids
     */
    public List<String> getMembersIds();

    @Override
    public default boolean isSingleMember() {
        return false;
    }

    @Override
    public default boolean isMemberContainer() {
        return true;
    }
    
    public List<? extends Member> getAllMembers();
    
    public default T locateMember(String id) {
        T member = getMember(id);
        if(member != null) {
            return member;
        }
        String[] resolveIdsPaths = MemberUtils.resolveIdsPaths(id);
        if(resolveIdsPaths.length <= 1) {
            return null;
        }
        if(resolveIdsPaths[0].equals(getId())) {
            String[] tail = Arrays.copyOfRange(resolveIdsPaths, 1, resolveIdsPaths.length);
            return locateMember(MemberUtils.makeId(tail));
        }
        T container = getMember(resolveIdsPaths[0]);
        if(container instanceof MemberContainer cont) {
            String[] tail = Arrays.copyOfRange(resolveIdsPaths, 1, resolveIdsPaths.length);
            return (T) cont.locateMember(MemberUtils.makeId(tail));
        }
        return null;
    }
}
