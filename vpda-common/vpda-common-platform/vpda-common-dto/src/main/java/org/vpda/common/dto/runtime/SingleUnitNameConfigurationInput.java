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
package org.vpda.common.dto.runtime;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.vpda.internal.common.util.Assert;

public final class SingleUnitNameConfigurationInput implements DTORuntimeConfigurationInput {

    private final String unitName;
    private final Set<Class<?>> classes;
    private final DTOModelConfiguration model;

    public SingleUnitNameConfigurationInput(String unitName, Set<Class<?>> classes, DTOModelConfiguration model) {
        this.unitName = Assert.isNotEmptyArgument(unitName, "unitName");
        this.classes = Collections.unmodifiableSet(classes);
        this.model = Assert.isNotNullArgument(model, "model");
    }

    public SingleUnitNameConfigurationInput(String unitName, Class<?>... classes) {
        this.unitName = Assert.isNotEmptyArgument(unitName, "unitName");
        this.classes = Collections.unmodifiableSet(new HashSet<>(Arrays.asList(classes)));
        this.model = DefaultDTOModelConfiguration.getInstance();
    }

    public static SingleUnitNameConfigurationInput create(String unitName, Set<Class<?>> classes, DTOModelConfiguration model) {
        return new SingleUnitNameConfigurationInput(unitName, classes, model);
    }

    public static SingleUnitNameConfigurationInput createWithDefaultModel(String unitName, Class<?>... classes) {
        return new SingleUnitNameConfigurationInput(unitName, classes);
    }

    @Override
    public Set<String> getUnitNames() {
        return Collections.singleton(unitName);
    }

    @Override
    public Set<Class<?>> getClassesForUnitName(String unitName) {
        if (unitName.equals(this.unitName)) {
            return classes;
        }
        return Collections.emptySet();
    }

    @Override
    public DTOModelConfiguration getModelConfigurationForUnitName(String unitName) {
        return model;
    }

}