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
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.vpda.common.dto.runtime.DTOModelConfiguration;
import org.vpda.common.dto.runtime.DTORuntime;
import org.vpda.common.dto.runtime.DTORuntimeConfigurationInput;
import org.vpda.common.dto.runtime.DTORuntimeFactory;
import org.vpda.internal.common.util.Assert;

public final class DTORuntimeFactoryImpl implements DTORuntimeFactory {

    private final DTORuntimeConfigurationInput input;

    private ConcurrentMap<String, DTORuntime> runtimes;

    public DTORuntimeFactoryImpl(DTORuntimeConfigurationInput input) {
        this.input = Assert.isNotNullArgument(input, "input");
        this.runtimes = new ConcurrentHashMap<>(2);
    }

    @Override
    public DTORuntime createDTORuntime(String unitName) {
        DTORuntime dtoRuntime = runtimes.get(unitName);
        if (dtoRuntime != null) {
            return dtoRuntime;
        }
        Set<Class<?>> classes = input.getClassesForUnitName(unitName);
        DTOModelConfiguration modelConfigurationForUnitName = input.getModelConfigurationForUnitName(unitName);
        dtoRuntime = new DTORuntimeImpl(unitName, classes, modelConfigurationForUnitName);
        runtimes.putIfAbsent(unitName, dtoRuntime);
        return dtoRuntime;
    }
}
