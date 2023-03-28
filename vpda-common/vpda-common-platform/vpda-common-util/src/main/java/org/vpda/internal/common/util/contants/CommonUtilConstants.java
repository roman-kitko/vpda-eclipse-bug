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
package org.vpda.internal.common.util.contants;

/**
 * @author kitko
 *
 */
/**
 * Constants that are not application dependent
 * 
 * @author kitko
 *
 */
public final class CommonUtilConstants {
    private CommonUtilConstants() {
    }

    /** Constant represented empty string */
    public final static String EMPTY_STRING = "";
    /** Constant for line separator - Os specific */
    public final static String LINE_SEPARATOR = System.getProperty("line.separator");
    /** Constant for name for project */
    public final static String VPDA_PROJECT_NAME = "VPDA";
    /** Constant semicolon */
    public final static String SEMICOLON = ";";

    /** Constant for dot */
    public final static String DOT = ".";

    /** Constant for colon */
    public final static String COLON = ",";

    /** Constant for SPACE */
    public final static char SPACE = ' ';

    /** Empty array of strings */
    public final static String[] EMPTY_STRING_ARRAY = new String[0];

}
