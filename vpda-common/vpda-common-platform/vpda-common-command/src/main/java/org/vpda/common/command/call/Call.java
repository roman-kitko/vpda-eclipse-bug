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
package org.vpda.common.command.call;

import java.util.Collection;

/**
 * Call is encapsulation of information we need to perform call . It contains
 * name and information about parameters.
 * 
 * @author kitko
 *
 */
public interface Call {
    /**
     * @return name of server call
     */
    public String getName();

    /**
     * @return collection of parameter names
     */
    public Collection<String> getParamNames();

    /**
     * @param paramName
     * @return value by parameter name or null if no such param
     */
    public Object getParamValue(String paramName);
}
