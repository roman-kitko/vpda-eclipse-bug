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

import java.io.Serializable;

/**
 * Enum of all currently provided communication protocols between client and
 * server.
 * 
 * @author kitko
 *
 */
public enum BasicCommunicationProtocol implements Serializable, CommunicationProtocol {
    /** RMI - Remote Method Invocation protocol */
    RMI("RMI", "Remote Method Invocation protocol"),
    /** HTTP protocol */
    HTTP("HTTP", "Http protocol"),
    /** Local - local for testing */
    EMBEDDED("EMBEDDED", "Embedded communication for testing"),
    /** JMX communication */
    JMX("JMX", "JMX Server management communication");

    /** Webservice */
    private String name;
    private String desc;

    BasicCommunicationProtocol(String name, String desc) {
        this.name = name;
        this.desc = desc;
    }

    @Override
    public String getDesc() {
        return desc;
    }

    @Override
    public String getName() {
        return name;
    }

}
