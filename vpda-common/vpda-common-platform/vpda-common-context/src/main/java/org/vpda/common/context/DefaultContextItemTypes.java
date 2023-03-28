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
package org.vpda.common.context;

/**
 * Default or common types of context items
 * 
 * @author kitko
 *
 */
public enum DefaultContextItemTypes implements ContextItemType {
    /** System context */
    SYSTEM(System.class),
    /** Whole organization */
    ORGANIZATION(Organization.class),
    /** One branch in organization */
    BRANCH(Branch.class),
    /** Year */
    /** Application user */
    USER(User.class),
    /** Group of users */
    GROUP(Group.class),
    /** Tenant item type */
    TENANT(Tenant.class);

    private Class<?> javaClass;

    @Override
    public String getName() {
        return name();
    }

    private DefaultContextItemTypes(Class<?> javaClass) {
        this.javaClass = javaClass;
    }

    @Override
    public Class<?> getJavaClass() {
        return javaClass;
    }
}
