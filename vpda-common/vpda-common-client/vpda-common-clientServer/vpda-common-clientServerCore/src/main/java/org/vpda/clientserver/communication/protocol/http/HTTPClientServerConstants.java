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
package org.vpda.clientserver.communication.protocol.http;

/**
 * Constants for HTTP Client and Server
 * 
 * @author kitko
 *
 */
public final class HTTPClientServerConstants {
    /** Default binding name */
    public static final String STATEFULL_SERVER_URI = "/vpda-connector/sf";
    /** Default binding name */
    public static final String STATELESS_SERVER_URI = "/vpda-connector/sl";

    /** Default HTTP Port */
    public static final int HTTP_PORT = 8080;

    /** Default HTTP Port for SSL */
    public static final int HTTP_PORT_SSL = 8443;

    /** Constant for id of HTTP login command executor */
    public static final String HTTP_STATEFULL_LOGIN_COMMAND_EXECUTOR_ID = "httpStatefullLoginCmdExecutor";

    /** Constant for id of HTTP stateless entry command executor */
    public static final String HTTP_STATELESS_ENTRY_COMMAND_EXECUTOR_ID = "httpStatelessEntryCmdExecutor";

    /** Constant for localhost */
    public static final String LOCALHOST = "localhost";

    /** Ping period for client in ms */
    public static final int CLIENT_PING_PERIOD = 2 * 60 * 1000;

    private HTTPClientServerConstants() {
    }

}
