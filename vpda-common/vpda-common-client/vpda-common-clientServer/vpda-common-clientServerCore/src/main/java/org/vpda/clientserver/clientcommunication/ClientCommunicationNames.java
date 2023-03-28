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
package org.vpda.clientserver.clientcommunication;

/**
 * List of names in client communication. For each kind we need unique name. Now
 * we have only one communication for any kind, so default name will make our
 * job
 * 
 * @author kitko
 *
 */
public final class ClientCommunicationNames {
    /** Default name for any communication kind */
    public static final String DEFAULT_NAME = "default";

    private ClientCommunicationNames() {
    }

}
