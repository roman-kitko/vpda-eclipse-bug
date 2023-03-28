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
package org.vpda.common.types;

import java.io.Serializable;

/**
 * @author kitko
 *
 */
public final class BasicBackendObjectSource implements BackendObjectSource, Serializable {
    private static final long serialVersionUID = 134024074838536859L;
    private final Object id;
    private final String type;
    private final Object stamp;

    /**
     * @param id
     * @param type
     * @param stamp
     * 
     */
    public BasicBackendObjectSource(Object id, String type, Object stamp) {
        this.id = id;
        this.type = type;
        this.stamp = stamp;
    }

    @Override
    public Object getBackendObjectId() {
        return id;
    }

    @Override
    public String getBackendObjectType() {
        return type;
    }

    @Override
    public Object getBackendObjectStamp() {
        return stamp;
    }

}
