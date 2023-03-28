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
package org.vpda.common.util.exceptions;

/**
 * Interface for tracing Exception lifecycle - logging,handling
 * 
 * @author kitko
 *
 */
public interface TracedException {
    /**
     * 
     * @return true if already logged else false
     */
    public boolean isLogged();

    /**
     * Set that exception is already logged
     * 
     * @param b
     *
     */
    public void setLogged(boolean b);

    /**
     * @return true if we should log
     */
    public boolean shouldLog();

    /**
     * Mark to not log
     */
    public void doNotLog();

    /**
     * Test whether exception was already user handled - some UI action was
     * performed
     * 
     * @return true/false
     */
    public boolean isUserHandled();

    /**
     * Set user handled flag
     * 
     * @param b
     */
    public void setUserHandled(boolean b);

}
