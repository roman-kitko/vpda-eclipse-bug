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

import org.vpda.common.dto.annotations.DTOEntity;

@DTOEntity
public final class BaseGenericListDomainObject extends AbstractGenericListDomainObject {

    private static final long serialVersionUID = 8087716592432279521L;

    public BaseGenericListDomainObject() {
        super();
    }

    /**
     * @param id
     * @param code
     * @param name
     */
    public BaseGenericListDomainObject(Object id, String code, String name) {
        super(id, code, name);
    }

}
