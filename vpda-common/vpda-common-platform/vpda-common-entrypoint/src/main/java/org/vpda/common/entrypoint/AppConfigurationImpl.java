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

import org.picocontainer.DefaultPicoContainer;
import org.picocontainer.MutablePicoContainer;
import org.picocontainer.PicoContainer;
import org.vpda.common.ioc.picocontainer.ChainPicoContainer;

/**
 * Container of application configuration
 * 
 * @author kitko
 *
 */
public final class AppConfigurationImpl implements AppConfiguration, Serializable {
    private static final long serialVersionUID = 2319416708005112121L;
    private final MutablePicoContainer rootContainer;
    private final ChainPicoContainer allModulesContainer;
    private static final String allModulesContainerKey = "allModulesContainer";

    @Override
    public MutablePicoContainer getRootContainer() {
        return rootContainer;
    }

    /**
     * Creates AppConfigurationImpl with no configuration
     */
    public AppConfigurationImpl() {
        this(null);
    }

    /**
     * Creates configuration with parent container
     * 
     * @param parentContainer
     */
    public AppConfigurationImpl(PicoContainer parentContainer) {
        rootContainer = new DefaultPicoContainer(parentContainer);
        rootContainer.addComponent(AppConfiguration.class, this);
        allModulesContainer = new ChainPicoContainer();
        rootContainer.addComponent(allModulesContainerKey, allModulesContainer);
    }

    @Override
    public PicoContainer getAllModulesContainer() {
        return allModulesContainer;
    }

}
