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
package org.vpda.common.entrypoint;

import java.io.Serializable;

import org.picocontainer.ComponentAdapter;
import org.picocontainer.MutablePicoContainer;
import org.picocontainer.PicoContainer;
import org.vpda.common.ioc.picocontainer.ChainPicoContainer;

/**
 * Default impl of {@link ModuleConfiguration}
 * 
 * @author kitko
 *
 */
public final class ModuleConfigurationImpl implements ModuleConfiguration, Serializable {
    private static final long serialVersionUID = 8380860976319117636L;
    private final ChainPicoContainer container;

    @Override
    public MutablePicoContainer getContainer() {
        return container;
    }

    /**
     * Will create ModuleConfigurationImpl
     * 
     * @param appConf
     */
    public ModuleConfigurationImpl(AppConfiguration appConf) {
        this(appConf, null);
    }

    /**
     * Creates conf with parrent container
     * 
     * @param appConf
     * @param parentContainer
     */
    public ModuleConfigurationImpl(AppConfiguration appConf, PicoContainer parentContainer) {
        this.container = new ChainPicoContainer();
        this.container.addDependendsOnContainer(appConf.getRootContainer());
        if (parentContainer != null) {
            for (ComponentAdapter adapter : parentContainer.getComponentAdapters()) {
                this.container.addAdapter(adapter);
            }
            this.container.addDependendsOnContainer(parentContainer);
        }
    }

}
