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
package org.vpda.common.dto.runtime.impl;

import java.util.Set;

import org.vpda.common.dto.model.DTOMetaModel;
import org.vpda.common.dto.runtime.DTOModelConfiguration;
import org.vpda.common.dto.runtime.DTOModelManager;
import org.vpda.common.dto.runtime.DTORepository;
import org.vpda.common.dto.runtime.DTORuntime;
import org.vpda.internal.common.util.Assert;

final class DTORuntimeImpl implements DTORuntime {

    private final DTORepository repository;;
    private final DTOModelConfiguration modelConfiguration;
    private final DTOModelManager modelManager;
    private final String unitName;

    DTORuntimeImpl(String unitName, Set<Class<?>> classes, DTOModelConfiguration modelConfiguration) {
        this(unitName, new DTORepositoryImpl(classes), modelConfiguration);
    }

    DTORuntimeImpl(String unitName, DTORepository repository, DTOModelConfiguration modelConfiguration) {
        this.unitName = Assert.isNotEmptyArgument(unitName, "unitName");
        this.repository = repository;
        this.modelConfiguration = modelConfiguration;
        this.modelManager = new DTOModelManagerImpl(getDTORepository(), modelConfiguration);
    }

    @Override
    public DTORepository getDTORepository() {
        return repository;
    }

    @Override
    public DTOModelManager getDTOModelManager() {
        return modelManager;
    }

    @Override
    public DTOModelConfiguration getModelConfiguration() {
        return modelConfiguration;
    }

    @Override
    public DTOMetaModel getMetaModel() {
        return getDTOModelManager().getMetaModel();
    }

    @Override
    public String getUnitName() {
        return unitName;
    }

}
