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
package org.vpda.common.dto.model;

public interface SingleAttribute<X, Y> extends Attribute<X, Y> {
    /**
     * Return the type that represents the type of the attribute.
     * 
     * @return type of attribute
     */
    Type<X> getType();

    /**
     * Is the attribute an id attribute. This method will return true if the
     * attribute is an attribute that corresponds to a simple id, an embedded id, or
     * an attribute of an id class.
     * 
     * @return boolean indicating whether the attribute is an id
     */
    boolean isId();

    /**
     * Is the attribute a version attribute.
     * 
     * @return boolean indicating whether the attribute is a version attribute
     */
    boolean isVersion();

    boolean isExternalId();
}
