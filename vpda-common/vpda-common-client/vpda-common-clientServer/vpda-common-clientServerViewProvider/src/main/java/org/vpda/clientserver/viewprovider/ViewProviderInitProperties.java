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
package org.vpda.clientserver.viewprovider;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Initial properties for initing of provider. User can pass this properties
 * into {@link ViewProviderInitData} within {@link ViewProviderOpenInfo}.
 * Application can subclass these properties to better fit concrete provider.
 * 
 * @author kitko
 *
 */
public class ViewProviderInitProperties implements Serializable {
    private static final long serialVersionUID = 7051446465593568923L;
    /** Initial properties */
    private final Map<String, Object> properties;

    /**
     * @return immutable init properties
     */
    public final Map<String, Object> getProperties() {
        return properties != null ? Collections.unmodifiableMap(properties) : Collections.<String, Object>emptyMap();
    }

    /**
     * Creates empty properties
     */
    public ViewProviderInitProperties() {
        properties = null;
    }

    /**
     * Creates initial properties with all properties from passed properties
     * 
     * @param properties
     */
    public ViewProviderInitProperties(Map<String, Object> properties) {
        this.properties = new HashMap<String, Object>(properties);
    }

    @Override
    public String toString() {
        return "ViewProviderInitProperties [properties=" + properties + "]";
    }

}
