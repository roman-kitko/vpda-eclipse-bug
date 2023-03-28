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
open module org.vpda.common.abstractclient.viewproviderui {
    requires transitive org.vpda.common.core;
    requires transitive org.vpda.common.abstractclient.core;
    requires transitive org.vpda.common.clientserver.viewprovider;
    requires transitive org.vpda.common.comps;
    requires transitive org.vpda.common.abstractclient.data;
    

    requires java.sql;
    requires java.logging;

    exports org.vpda.abstractclient.viewprovider.ui;
    exports org.vpda.abstractclient.viewprovider.ui.commands;
    exports org.vpda.abstractclient.viewprovider.ui.commands.generic;
    exports org.vpda.abstractclient.viewprovider.ui.fetch;
    exports org.vpda.abstractclient.viewprovider.ui.generic;
    exports org.vpda.abstractclient.viewprovider.ui.generic.impl;
    exports org.vpda.abstractclient.viewprovider.ui.impl;
    exports org.vpda.abstractclient.viewprovider.ui.list;
    exports org.vpda.abstractclient.viewprovider.ui.list.impl;
    exports org.vpda.abstractclient.viewprovider.ui.module;
    exports org.vpda.abstractclient.viewprovider.ui.moduleentry;
}