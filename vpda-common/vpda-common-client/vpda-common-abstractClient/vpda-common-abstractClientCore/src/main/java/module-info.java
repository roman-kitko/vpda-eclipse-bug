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
open module org.vpda.common.abstractclient.core {
    requires java.logging;
    requires java.sql;
    requires xstream;

    requires transitive org.vpda.common.util;
    requires transitive org.vpda.common.core;
    requires transitive org.vpda.common.comps;
    requires transitive org.vpda.common.clientserver.core;
    requires transitive org.vpda.common.client.communication;

    exports org.vpda.abstractclient.core;
    exports org.vpda.abstractclient.core.comps;
    exports org.vpda.abstractclient.core.comps.impl;
    exports org.vpda.abstractclient.core.impl;
    exports org.vpda.abstractclient.core.module;
    exports org.vpda.abstractclient.core.moduleentry;
    exports org.vpda.abstractclient.core.profile;
    exports org.vpda.abstractclient.core.profile.impl;
    exports org.vpda.abstractclient.core.ui;

}