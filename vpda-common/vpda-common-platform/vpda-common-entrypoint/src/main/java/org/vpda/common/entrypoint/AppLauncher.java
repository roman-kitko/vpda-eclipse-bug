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

import java.util.List;

/**
 * Will launch Modules
 * 
 * @author kitko
 *
 */
public interface AppLauncher {
    /**
     * Launch modules from modulesConfiguration with listeners
     * 
     * @param appConf
     * @param listeners
     * @return entry point
     * @throws Exception
     */
    public AppEntryPoint createAndlaunchEntryPoint(AppConfiguration appConf, List<EntryPointListener> listeners) throws Exception;

    /**
     * Launch modules from modulesConfiguration without listeners
     * 
     * @param appConf
     * @return entry point
     * @throws Exception
     */
    public AppEntryPoint createAndlaunchEntryPoint(AppConfiguration appConf) throws Exception;

    /**
     * Creates Application
     * 
     * @param appConf
     * @return entry point
     * @throws Exception
     */
    public Application createApplication(AppConfiguration appConf) throws Exception;

    /**
     * Launch the application with implicit create of configuration
     * 
     * @throws Exception
     */
    public void launch() throws Exception;
}
