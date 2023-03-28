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
package org.vpda.clientserver.communication.protocol.rmi;

/**
 * Constants for RMI Client and Server
 * 
 * @author kitko
 *
 */
public final class RMIClientServerConstants {
    private RMIClientServerConstants() {
    }

    /** Default binding name for login server */
    public static final String LOGIN_SERVER_BINDING_NAME = "RMI_VPDA_LOGIN_SERVER";
    /** Default binding name for client entry */
    public static final String CLIENT_SERVER_ENTRY_BINDING_NAME = "RMI_VPDA_CLIENT_ENTRY";
    /** Default RMI Port */
    public static final int RMI_PORT = 1099;
    /** Default export port */
    public static final int EXPORT_PORT = 9000;
    /** Constant for id of RMI login Cmd exectotor */
    public static final String RMI_LOGIN_COMMAND_EXECUTOR_ID = "rmiLoginCmdExecutor";
    /** Constant for localhost */
    public static final String LOCALHOST = "localhost";
    /** Default binding name for login server for SSL */
    public static final String LOGIN_SERVER_BINDING_NAME_SSL = LOGIN_SERVER_BINDING_NAME;
    /** Default binding name for client entry for ssl */
    public static final String CLIENT_SERVER_ENTRY_BINDING_NAME_SSL = CLIENT_SERVER_ENTRY_BINDING_NAME;
    /** Default RMI Port for SSL */
    public static final int RMI_PORT_SSL = 1199;
    /** Default export port for SSL */
    public static final int EXPORT_PORT_SSL = 9090;

}
