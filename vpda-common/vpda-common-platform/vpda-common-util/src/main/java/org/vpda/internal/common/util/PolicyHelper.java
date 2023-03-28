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
package org.vpda.internal.common.util;

import java.net.URL;
import java.security.Policy;
import java.util.logging.Level;

import org.vpda.common.util.logging.LoggerMethodTracer;

/**
 * Policy helper class
 * 
 * @author kitko
 *
 */
public final class PolicyHelper {
    private static boolean allPolicySet;

    private PolicyHelper() {
    }

    /**
     * Grants all permisions to application
     * 
     *
     */
    public static void grantAllPermisions() {
        if (allPolicySet) {
            return;
        }
        URL url = PolicyHelper.class.getResource("policyAll");
        if (url != null) {
            LoggerMethodTracer.getLogger(PolicyHelper.class).log(Level.INFO, "Setting java.security.policy to " + url);
            System.setProperty("java.security.policy", url.toExternalForm());
            Policy.getPolicy().refresh();
        }
        allPolicySet = true;
    }

}
