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

import org.vpda.common.dto.model.DTOMetaModel;
import org.vpda.common.dto.runtime.DTOModelConfiguration;
import org.vpda.common.dto.runtime.DTOModelManager;
import org.vpda.common.dto.runtime.DTORepository;

final class DTOModelManagerImpl implements DTOModelManager {

    private final DTORepository repository;
    private final DTOModelConfiguration modelConfiguration;
    private volatile DTOMetaModel metaModel;

    DTOModelManagerImpl(DTORepository repository, DTOModelConfiguration modelConfiguration) {
        this.repository = repository;
        this.modelConfiguration = modelConfiguration;
    }

    @Override
    public DTOMetaModel getMetaModel() {
        if (metaModel != null) {
            return metaModel;
        }
        metaModel = new DTOModelBuilder(this.repository, this.modelConfiguration).buildModel();
        return metaModel;
    }

    @Override
    public DTORepository getRepository() {
        return repository;
    }

    @Override
    public DTOModelConfiguration getModelConfiguration() {
        return modelConfiguration;
    }

}
