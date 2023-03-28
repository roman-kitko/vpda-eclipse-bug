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
/**
 * 
 */
package org.vpda.common.comps.moduleentry;

import java.util.ArrayList;
import java.util.List;

import org.vpda.common.comps.module.CommonComponentsModule;
import org.vpda.common.core.moduleEntry.CommonCoreModuleEntryPoint;
import org.vpda.common.core.moduleEntry.CommonCoreModuleEntryPointImpl;
import org.vpda.common.entrypoint.AbstractModuleEntryPoint;
import org.vpda.common.entrypoint.AppEntryPoint;
import org.vpda.common.entrypoint.ModuleEntryPoint;

/**
 * 
 * Implementation of CommonComponentsModuleEntryPoint
 * 
 * @author kitko
 *
 */
public class CommonComponentsModuleEntryPointImpl extends AbstractModuleEntryPoint implements CommonComponentsModuleEntryPoint {

    /**
     * 
     */
    public CommonComponentsModuleEntryPointImpl() {

    }

    @Override
    protected org.vpda.common.entrypoint.Module createModule() {
        return CommonComponentsModule.getInstance();
    }

    @Override
    public void entryPoint(AppEntryPoint appEntryPoint, org.vpda.common.entrypoint.Module module) {
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends ModuleEntryPoint> Class<T> getDefImplClassForModule(Class<T> moduleIface) {
        if (CommonCoreModuleEntryPoint.class.equals(moduleIface)) {
            return (Class<T>) CommonCoreModuleEntryPointImpl.class;
        }
        return null;
    }

    @Override
    public List<Class<? extends ModuleEntryPoint>> getRequiredModuleEntryPointsClasses(AppEntryPoint appEntryPoint) {
        List<Class<? extends ModuleEntryPoint>> modules = new ArrayList<Class<? extends ModuleEntryPoint>>();
        modules.add(CommonCoreModuleEntryPoint.class);
        return modules;
    }

}
