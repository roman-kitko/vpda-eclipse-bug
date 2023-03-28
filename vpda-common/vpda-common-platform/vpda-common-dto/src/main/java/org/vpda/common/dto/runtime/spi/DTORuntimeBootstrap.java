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
package org.vpda.common.dto.runtime.spi;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.vpda.common.dto.model.DTOMetaModel;
import org.vpda.common.dto.model.gen.DTOMetaClassesGenerator;
import org.vpda.common.dto.model.gen.MetaClassGeneratorBaseConfiguration;
import org.vpda.common.dto.model.gen.MetaClassesGeneratorLogger;
import org.vpda.common.dto.runtime.DTOModelConfiguration;
import org.vpda.common.dto.runtime.DTORuntime;
import org.vpda.common.dto.runtime.DTORuntimeConfigurationInput;
import org.vpda.common.dto.runtime.DTORuntimeFactory;

public final class DTORuntimeBootstrap {
    
    private DTORuntimeBootstrap() {}

    private static final DefaultDTORuntimeProviderResolver resolver = new DefaultDTORuntimeProviderResolver();

    public static DTORuntimeFactory createDTORuntimeFactory(DTORuntimeConfigurationInput input) {
        return resolveProvider().createDTORuntimeFactory(input);
    }

    public static DTOMetaClassesGenerator createDTOMetaClassesGenerator(MetaClassesGeneratorLogger logger) {
        return resolveProvider().createMetaClassesGenerator(logger);
    }

    public static DTORuntimeProvider resolveProvider() {
        return resolver.resolveDTORuntimeProvider();
    }
    
    public static DTORuntimeRegistry createDTORuntimeRegistry() {
        return resolveProvider().createDTORuntimeRegistry();
    }

    public static Map<String, DTORuntime> createAllModelsAndInitMetaClasses(DTORuntimeConfigurationInput input, MetaClassesGeneratorLogger logger) {
        Map<String, DTORuntime> map = new HashMap<>(3);
        DTOMetaClassesGenerator generator = createDTOMetaClassesGenerator(logger);
        DTORuntimeFactory factory = DTORuntimeBootstrap.createDTORuntimeFactory(input);
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        MetaClassGeneratorBaseConfiguration config = new MetaClassGeneratorBaseConfiguration() {
        };
        for (String unitName : input.getUnitNames()) {
            DTORuntime dtoRuntime = factory.createDTORuntime(unitName);
            DTOMetaModel metaModel = dtoRuntime.getMetaModel();
            generator.initializeGeneratedMetaClasses(metaModel, config, loader);
            map.put(unitName, dtoRuntime);
        }
        return map;
    }
    
    public static Map<String, DTORuntime> createAllModelsAndInitMetaClasses(DTORuntimeConfigurationInput input, MetaClassesGeneratorLogger logger, DTORuntimeRegistry registry){
        final class FilteredInput implements DTORuntimeConfigurationInput{
            @Override
            public Set<String> getUnitNames() {
                Set<String> finalNames = new HashSet<>(input.getUnitNames());
                finalNames.removeAll(registry.getUnitNames());
                return finalNames;
            }

            @Override
            public Set<Class<?>> getClassesForUnitName(String unitName) {
                return input.getClassesForUnitName(unitName);
            }

            @Override
            public DTOModelConfiguration getModelConfigurationForUnitName(String unitName) {
                return input.getModelConfigurationForUnitName(unitName);
            }
        }
        FilteredInput filteredInput = new FilteredInput();
        if(filteredInput.getUnitNames().isEmpty()) {
            return Collections.emptyMap();
        }
        Map<String, DTORuntime> map = createAllModelsAndInitMetaClasses(input, logger);
        map.forEach((unit, runtime) -> registry.registerRuntime(runtime));
        return map;
    }
  }
