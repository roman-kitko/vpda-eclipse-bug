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
package org.vpda.common.entrypoint;

/**
 * Constants for configuration
 * 
 * @author kitko
 *
 */
public final class ConfigurationConstants {
    private ConfigurationConstants() {
    }

    /** data dir */
    public static final String DATA_DIR = "vpda/data";
    /** data test dir */
    public static final String DATA_TEST_DIR = "datatest";
    /** Name of icons dir */
    public static final String ICONS_DIR = "icons";
    /** data icons dir */
    public static final String DATA_ICONS_PATH = DATA_DIR + '/' + ICONS_DIR + '/';
    /** Name of loc dir */
    public static final String LOC_DIR = "loc";
    /** Name of loc path dir */
    public static final String DATA_LOC_PATH = DATA_DIR + '/' + LOC_DIR + '/';
}
