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
package org.vpda.common.util.testing;

/**
 * Type of tests We will use this enumeration to later select test to suites
 * This will help us execute very quickly unit test while integration and
 * functional tests we can execute later
 * 
 * @author kitko
 *
 */
public enum TestType {
    /** Unit tests - test only java code */
    UNIT,
    /** Integration - DB,communication .. */
    INTEGRATION,
    /** Functional - use also gui to test */
    FUNCTIONAL,
    /** All tests */
    ALL;
}
