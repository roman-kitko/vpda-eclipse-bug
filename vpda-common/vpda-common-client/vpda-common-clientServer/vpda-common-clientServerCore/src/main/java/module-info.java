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
open module org.vpda.common.clientserver.core {

    requires java.rmi;
    requires java.logging;
    requires java.sql;
    requires org.apache.commons.io;
    requires org.apache.httpcomponents.client5.httpclient5;
    requires org.apache.httpcomponents.core5.httpcore5;

    requires org.vpda.common.util;
    requires transitive org.vpda.common.core;
    requires org.vpda.common.comps;

    exports org.vpda.clientserver.clientcommunication;
    exports org.vpda.clientserver.communication;
    exports org.vpda.clientserver.communication.command;
    exports org.vpda.clientserver.communication.compression;
    exports org.vpda.clientserver.communication.data;
    exports org.vpda.clientserver.communication.data.impl;
    exports org.vpda.clientserver.communication.module;
    exports org.vpda.clientserver.communication.moduleentry;
    exports org.vpda.clientserver.communication.pref;
    exports org.vpda.clientserver.communication.protocol.embedded;
    exports org.vpda.clientserver.communication.protocol.http;
    exports org.vpda.clientserver.communication.protocol.jmx;
    exports org.vpda.clientserver.communication.protocol.rmi;
    exports org.vpda.clientserver.communication.services;
    exports org.vpda.clientserver.communication.services.impl;
    exports org.vpda.internal.clientserver.communication;
}