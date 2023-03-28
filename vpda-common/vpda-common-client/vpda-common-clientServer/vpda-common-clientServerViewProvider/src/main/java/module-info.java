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
open module org.vpda.common.clientserver.viewprovider {
    requires transitive org.vpda.common.core;
    requires transitive org.vpda.common.comps;
    requires transitive org.vpda.common.clientserver.core;

    requires java.logging;
    requires com.fasterxml.jackson.databind;

    exports org.vpda.clientserver.viewprovider;
    exports org.vpda.clientserver.viewprovider.autocomplete;
    exports org.vpda.clientserver.viewprovider.autocomplete.impl;
    exports org.vpda.clientserver.viewprovider.autocomplete.stateless;
    exports org.vpda.clientserver.viewprovider.criteria;
    exports org.vpda.clientserver.viewprovider.custom;
    exports org.vpda.clientserver.viewprovider.fetch;
    exports org.vpda.clientserver.viewprovider.generic;
    exports org.vpda.clientserver.viewprovider.generic.impl;
    exports org.vpda.clientserver.viewprovider.generic.stateless;
    exports org.vpda.clientserver.viewprovider.list;
    exports org.vpda.clientserver.viewprovider.list.annotations;
    exports org.vpda.clientserver.viewprovider.list.impl;
    exports org.vpda.clientserver.viewprovider.list.processor;
    exports org.vpda.clientserver.viewprovider.list.stateless;
    exports org.vpda.clientserver.viewprovider.module;
    exports org.vpda.clientserver.viewprovider.moduleentry;
    exports org.vpda.clientserver.viewprovider.preferences;
    exports org.vpda.clientserver.viewprovider.stateless;
    exports org.vpda.clientserver.viewprovider.dto.inputfactory;

}