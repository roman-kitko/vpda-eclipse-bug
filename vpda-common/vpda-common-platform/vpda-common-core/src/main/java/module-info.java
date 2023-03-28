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
open module org.vpda.common.core {
    requires java.logging;
    requires java.management;
    requires java.sql;
    requires java.xml;
    requires java.compiler;

    requires transitive cache.api;
    requires transitive ehcache;
    requires org.apache.commons.io;
    requires com.fasterxml.jackson.databind;
    requires info.picocli;
    requires jdk.unsupported; // for ehcache

    requires transitive org.vpda.common.util;
    requires transitive org.vpda.common.context;
    requires transitive org.vpda.common.types;
    requires transitive org.vpda.common.processor;
    requires transitive org.vpda.common.dto;
    requires transitive org.vpda.common.ioc;
    requires transitive org.vpda.common.command;
    requires transitive org.vpda.common.service;
    requires transitive org.vpda.common.entrypoint;

    exports org.vpda.common.core.module;
    exports org.vpda.common.core.moduleEntry;
}
