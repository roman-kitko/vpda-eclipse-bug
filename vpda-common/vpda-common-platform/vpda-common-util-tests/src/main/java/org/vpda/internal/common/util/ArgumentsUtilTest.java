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
package org.vpda.internal.common.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Collections;

import org.junit.jupiter.api.Test;

/** Tests for ArgumentsUtil */
public class ArgumentsUtilTest {

    /** test */
    @Test
    public void testParseSystemProperties() {
        assertEquals(Collections.emptyList(), ArgumentsUtil.retrieveSystemProperties(new String[0]));
        assertEquals(Collections.singletonList(new ArgumentsUtil.SystemProperty("myKey", "myValue")), ArgumentsUtil.retrieveSystemProperties(new String[] { "-DmyKey=myValue" }));
        assertEquals(Collections.singletonList(new ArgumentsUtil.SystemProperty("myKey", "")), ArgumentsUtil.retrieveSystemProperties(new String[] { "-DmyKey=" }));
        assertEquals(Collections.emptyList(), ArgumentsUtil.retrieveSystemProperties(new String[] { "-D" }));
        assertEquals(Collections.emptyList(), ArgumentsUtil.retrieveSystemProperties(new String[] { "-D=" }));
    }
}
