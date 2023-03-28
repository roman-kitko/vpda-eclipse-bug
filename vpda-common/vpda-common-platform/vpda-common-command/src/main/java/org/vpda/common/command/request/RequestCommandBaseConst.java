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
package org.vpda.common.command.request;

/**
 * Constants for {@link AbstractRequestCommand}
 * 
 * @author kitko
 *
 */
public abstract class RequestCommandBaseConst {
    /** Default Request Command executor */
    public static final String RCB_EXECUTOR = "RCBExecutor";

    private RequestCommandBaseConst() {
    }

    /** Before request command executor id */
    public static final String BEFORE_REQUEST_COMMAND_EXECUTOR = "brce";
    /** Request command executor id */
    public static final String REQUEST_COMMAND_EXECUTOR = "rce";
    /** After request command executor id */
    public static final String AFTER_REQUEST_COMMAND_EXECUTOR = "arce";
    /** Exception handling */
    public static final String EXCEPTION_HANDLING_COMMAND_EXECUTOR = "exce";

}
