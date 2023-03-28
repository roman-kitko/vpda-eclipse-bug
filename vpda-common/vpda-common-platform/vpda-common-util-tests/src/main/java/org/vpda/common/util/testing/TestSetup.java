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
package org.vpda.common.util.testing;

import java.io.IOException;
import java.net.URL;
import java.util.logging.LogManager;

/**
 * Setup logging for tests
 * 
 * @author kitko
 *
 */
public final class TestSetup {
    private TestSetup() {
    }

    private static boolean loggingConfigurationLoaded;
    /** Name of logging configuration file for tests */
    public final static String TEST_LOG_CONFIG_FILE = "testLogging.properties";

    /**
     * Load logging configuration for tests
     * 
     * @throws SecurityException
     */
    public static void loadTestLoggingConfiguration() {
        if (loggingConfigurationLoaded) {
            return;
        }
        URL logUrl = null;
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        if (loader != null) {
            logUrl = loader.getResource(TEST_LOG_CONFIG_FILE);
        }
        if (logUrl == null) {
            logUrl = TestSetup.class.getClassLoader().getResource(TEST_LOG_CONFIG_FILE);
        }
        if (logUrl != null) {
            try {
                LogManager.getLogManager().readConfiguration(logUrl.openStream());
            }
            catch (SecurityException e) {
                e.printStackTrace();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        loggingConfigurationLoaded = true;
    }

    /** Test policy and logging */
    public static void setup() {
        loadTestLoggingConfiguration();
        org.vpda.internal.common.util.PolicyHelper.grantAllPermisions();
    }

}
