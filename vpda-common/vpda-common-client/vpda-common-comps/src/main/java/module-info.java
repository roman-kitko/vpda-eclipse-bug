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
open module org.vpda.common.comps {
    requires java.sql;

    requires org.vpda.common.util;
    requires transitive org.vpda.common.core;

    exports org.vpda.common.comps;
    exports org.vpda.common.comps.annotations;
    exports org.vpda.common.comps.autocomplete;
    exports org.vpda.common.comps.loc;
    exports org.vpda.common.comps.module;
    exports org.vpda.common.comps.moduleentry;
    exports org.vpda.common.comps.processor;
    exports org.vpda.common.comps.shortcuts;
    exports org.vpda.common.comps.shortcuts.keyboard;
    exports org.vpda.common.comps.ui;
    exports org.vpda.common.comps.ui.def;
    exports org.vpda.common.comps.ui.def.instance;
    exports org.vpda.common.comps.ui.menu;
    exports org.vpda.common.comps.ui.resolved;
    exports org.vpda.common.comps.ui.table;
}
