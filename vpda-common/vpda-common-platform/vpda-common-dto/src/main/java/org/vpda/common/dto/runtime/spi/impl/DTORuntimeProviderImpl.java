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
package org.vpda.common.dto.runtime.spi.impl;

import org.vpda.common.dto.model.gen.DTOMetaClassesGenerator;
import org.vpda.common.dto.model.gen.MetaClassesGeneratorLogger;
import org.vpda.common.dto.model.gen.impl.DTOMetaClassesGeneratorImpl;
import org.vpda.common.dto.runtime.DTORuntimeFactory;
import org.vpda.common.dto.runtime.impl.DTORuntimeFactoryImpl;
import org.vpda.common.dto.runtime.DTORuntimeConfigurationInput;
import org.vpda.common.dto.runtime.spi.DTORuntimeProvider;
import org.vpda.common.dto.runtime.spi.DTORuntimeRegistry;

public final class DTORuntimeProviderImpl implements DTORuntimeProvider {

    @Override
    public DTORuntimeFactory createDTORuntimeFactory(DTORuntimeConfigurationInput configurationInput) {
        return new DTORuntimeFactoryImpl(configurationInput);
    }

    @Override
    public DTOMetaClassesGenerator createMetaClassesGenerator(MetaClassesGeneratorLogger logger) {
        return new DTOMetaClassesGeneratorImpl(logger);
    }

    @Override
    public DTORuntimeRegistry createDTORuntimeRegistry() {
        return new DTORuntimeRegistryImpl();
    }
}
