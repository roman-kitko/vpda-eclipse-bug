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
package org.vpda.common.command.env;

import org.picocontainer.PicoContainer;
import org.vpda.common.ioc.objectresolver.PicoContainerObjectResolver;

/**
 * Execution environment that gets its values from PicoContainer
 * 
 * @author kitko
 *
 */
public final class PicoContainerExecutionEnv extends CEEnvDelegate {
    private static final long serialVersionUID = -6484438039552948015L;

    /**
     * Creates PicoContainerExecutionEnv from container
     * 
     * @param container
     */
    public PicoContainerExecutionEnv(PicoContainer container) {
        super(new PicoContainerObjectResolver(container));
    }

}
