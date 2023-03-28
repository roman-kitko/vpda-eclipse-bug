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
open module org.vpda.common.util {
    requires transitive java.logging;
    requires java.rmi;
    requires java.xml;

    requires com.fasterxml.jackson.databind;
    requires org.eclipse.jgit;

    exports org.vpda.common.annotations;
    exports org.vpda.common.util;
    exports org.vpda.common.util.logging;
    exports org.vpda.common.util.exceptions;
    exports org.vpda.internal.common;
    exports org.vpda.internal.common.util;
    exports org.vpda.internal.common.util.contants;
    exports org.vpda.internal.common.util.scm;
}