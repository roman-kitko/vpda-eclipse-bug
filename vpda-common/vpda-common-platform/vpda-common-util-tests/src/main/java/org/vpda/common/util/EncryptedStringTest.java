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
/**
 * 
 */
package org.vpda.common.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.vpda.common.util.EncryptedString.Accessor;

/**
 * @author kitko
 *
 */
public class EncryptedStringTest {

    /**
     * Test method for
     * {@link org.vpda.common.util.EncryptedString#access(org.vpda.common.util.EncryptedString.Accessor)}.
     */
    @Test
    public void testAccess() {
        new EncryptedString("myPassword".toCharArray()).access(new Accessor() {

            @Override
            public void access(char[] clearChars) {
                Assertions.assertArrayEquals("myPassword".toCharArray(), clearChars);
            }
        });
    }

    /**
     * Test method for
     * {@link org.vpda.common.util.EncryptedString#EncryptedString(char[])}.
     */
    @Test
    public void testEncryptedString() {
        Assertions.assertEquals(new EncryptedString("myPassword".toCharArray()), new EncryptedString("myPassword".toCharArray()));
        Assertions.assertFalse(new EncryptedString("myPassword1".toCharArray()).equals(new EncryptedString("myPassword".toCharArray())));
    }

}
