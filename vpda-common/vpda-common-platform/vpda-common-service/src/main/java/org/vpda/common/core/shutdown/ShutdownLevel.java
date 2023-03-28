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
package org.vpda.common.core.shutdown;

/**
 * Enumeration that defines shutdown level. Going down, the commands registered
 * with that level are executed later
 * 
 * @author kitko
 *
 */
public enum ShutdownLevel {
    /** All conectors - RMI,JMX..... */
    CLIENT_CONNECTORS(0),
    /** Some application specific services */
    BUSINES_SERVICES(1),
    /** Threads,Connection pools.... */
    SYSTEM_RESORCES(2),
    /** GUI */
    UI(3),
    /** Enviroment - if we are running in some container.... */
    ENVIRONMENT(4),
    /** Admin connectors */
    ADMIN_CONNECTORS(5),
    /** Halt */
    HALT(6);

    private int value;

    ShutdownLevel(int value) {
        this.value = value;
    }

    /**
     * 
     * @return level
     */
    public int getValue() {
        return value;
    }

}
