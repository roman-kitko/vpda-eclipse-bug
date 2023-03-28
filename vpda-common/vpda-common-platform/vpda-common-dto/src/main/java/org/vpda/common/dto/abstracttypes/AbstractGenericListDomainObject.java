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
package org.vpda.common.dto.abstracttypes;

import java.io.Serializable;

import org.vpda.common.dto.GenericListPersistentDO;
import org.vpda.common.dto.annotations.DTOMappedSuperIdentifiableClass;

/**
 * Generic list Domain object
 * 
 * @author kitko
 *
 */
@DTOMappedSuperIdentifiableClass
public abstract class AbstractGenericListDomainObject extends AbstractDomainObject implements GenericListPersistentDO, Serializable {
    private static final long serialVersionUID = 997455874665340185L;
    private String code;
    private String name;
    private String description;

    /**
     * @param id
     * @param code
     * @param name
     */
    public AbstractGenericListDomainObject(Object id, String code, String name) {
        super(id);
        this.code = code;
        this.name = name;
    }

    /**
     * Creates GenericListDTO
     */
    public AbstractGenericListDomainObject() {
    }

    /**
     * @return the code
     */
    @Override
    public String getCode() {
        return code;
    }

    /**
     * @param code the code to set
     */
    @Override
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * @return the name
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "GenericListDTO [id=" + getId() + ", code=" + code + ", name=" + name + "]";
    }

}
