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
package org.vpda.abstractclient.viewprovider.ui.module;

import org.vpda.abstractclient.core.module.AbstractClientNamespace;
import org.vpda.common.entrypoint.AbstractModule;
import org.vpda.common.entrypoint.BasicModuleKind;
import org.vpda.common.entrypoint.Module;
import org.vpda.common.entrypoint.ModuleKind;
import org.vpda.common.entrypoint.ModuleNamespace;

/**
 * Module for abstract client view providers.
 * 
 * @author kitko
 *
 */
public final class AbstractClientViewProviderModule extends AbstractModule {
    /**
     * 
     */
    private static final long serialVersionUID = 3098920993334846695L;
    /** Name of module */
    public final static String MODULE_NAME = "AbstractClientViewProvider";

    /**
     * Constructor for module
     */
    private AbstractClientViewProviderModule() {
    }

    @Override
    protected void initName() {
        name = MODULE_NAME;
    }

    /**
     * 
     * @return module instance
     */
    public static Module getInstance() {
        return AbstractModule.getModule(AbstractClientViewProviderModule.class);
    }

    @Override
    public ModuleKind getKind() {
        return BasicModuleKind.CONTRACT;
    }

    @Override
    public ModuleNamespace getModuleNamespace() {
        return AbstractClientNamespace.ABSTRACT_CLIENT;
    }

}
