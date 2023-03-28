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
package org.vpda.clientserver.communication;

/**
 * Communication kind enum. Server may establish these comunication kinds to
 * listen for request
 * 
 * @author kitko
 *
 */
public enum BasicCommunicationKind implements CommunicationKind {
    /** Base communication between client and server */
    CLIENT_SERVER_COMMUNICATION,
    /** Management communication to manage server */
    SERVER_MANAGEMENT_COMMUNICATION,
    /** Communication to server for cluster purposes */
    CLUSTER_NODE_COMMUNICATION;

    @Override
    public String getName() {
        return name();
    }
}
