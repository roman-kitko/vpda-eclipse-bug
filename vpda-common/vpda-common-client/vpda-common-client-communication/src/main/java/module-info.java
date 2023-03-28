/**
 * View provider driven applications - java application framework for developing
 * RIA Copyright (C) 2009-2022 Roman Kitko, Slovakia
 *
 * Licensed under the GNU GENERAL PUBLIC LICENSE, Version 3.0 (the "License").
 * You may not use this file except in compliance with the License. You may
 * obtain a copy of the License at http://www.gnu.org/licenses/gpl.html
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
open module org.vpda.common.client.communication {
    requires java.logging;
    requires java.rmi;

    requires org.apache.commons.io;
    requires org.apache.httpcomponents.client5.httpclient5;
    requires org.apache.httpcomponents.core5.httpcore5;

    requires org.vpda.common.util;
    requires org.vpda.common.core;
    requires transitive org.vpda.common.clientserver.core;

    exports org.vpda.client.clientcommunication;
    exports org.vpda.client.clientcommunication.protocol.embedded;
    exports org.vpda.client.clientcommunication.protocol.http;
    exports org.vpda.client.clientcommunication.protocol.jmx;
    exports org.vpda.client.clientcommunication.protocol.rmi;

    provides java.rmi.server.RMIClassLoaderSpi with org.vpda.client.clientcommunication.protocol.rmi.DynamicRMIClassLoaderSpi;

}