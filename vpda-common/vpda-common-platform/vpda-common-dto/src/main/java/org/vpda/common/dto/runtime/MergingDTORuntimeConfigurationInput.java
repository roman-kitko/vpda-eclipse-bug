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

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.vpda.common.util.exceptions.VPDARuntimeException;

public final class MergingDTORuntimeConfigurationInput implements DTORuntimeConfigurationInput {

    private final Collection<DTORuntimeConfigurationInput> inputs;

    public MergingDTORuntimeConfigurationInput(DTORuntimeConfigurationInput... inputs) {
        this.inputs = new HashSet<>();
        for (DTORuntimeConfigurationInput input : inputs) {
            this.inputs.add(input);
        }
    }

    @Override
    public Set<String> getUnitNames() {
        Set<String> finalNames = new HashSet<>();
        inputs.forEach((DTORuntimeConfigurationInput input) -> finalNames.addAll(input.getUnitNames()));
        return finalNames;
    }

    @Override
    public Set<Class<?>> getClassesForUnitName(String unitName) {
        Set<Class<?>> finalClasses = new HashSet<>();
        inputs.forEach((DTORuntimeConfigurationInput input) -> {
            Set<Class<?>> set = input.getClassesForUnitName(unitName);
            if (set != null) {
                finalClasses.addAll(set);
            }
        });
        return finalClasses;
    }

    @Override
    public DTOModelConfiguration getModelConfigurationForUnitName(String unitName) {
        for (DTORuntimeConfigurationInput input : inputs) {
            DTOModelConfiguration cfg = input.getModelConfigurationForUnitName(unitName);
            if (cfg != null) {
                for (DTORuntimeConfigurationInput input2 : inputs) {
                    if (input == input2) {
                        continue;
                    }
                    DTOModelConfiguration cfg2 = input2.getModelConfigurationForUnitName(unitName);
                    if (cfg2 != null && !cfg2.equals(cfg)) {
                        throw new VPDARuntimeException("Unambiguos DTOModelConfiguration for unitName : " + unitName);
                    }
                }
                return cfg;
            }
        }

        inputs.forEach((DTORuntimeConfigurationInput input) -> {
            DTOModelConfiguration cfg = input.getModelConfigurationForUnitName(unitName);
            if (cfg != null) {
                inputs.forEach((DTORuntimeConfigurationInput input2) -> {
                    if (input2 != input) {
                        DTOModelConfiguration cfg2 = input2.getModelConfigurationForUnitName(unitName);
                        if (cfg2 != null && !cfg2.equals(cfg)) {
                            throw new VPDARuntimeException("Unambiguos DTOModelConfiguration for unitName : " + unitName);
                        }
                    }
                });
            }
        });
        return DefaultDTOModelConfiguration.getInstance();
    }

}
