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

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.io.IOException;

import org.junit.jupiter.api.Test;

/** Test */
public class ClassBytesResolverTest {
    /**
     * Test
     * 
     * @throws IOException
     */
    @Test
    public void testLoadClass() throws IOException {
        ClassBytesResolver testee = new ClassBytesResolver();
        byte[] b = testee.loadClassBytes(String.class.getName());
        assertNotNull(b);
        byte[] a = testee.loadClassBytes(String.class.getName());
        assertSame(a, b);

        byte[] c = testee.loadClassBytes("XX");
        assertNull(c);

        class OtherClass {
        }
        assertNotNull(testee.loadClassBytes(OtherClass.class.getName()));
        assertNotNull(testee.loadClassBytes(MyClass.class.getName()));

    }

    private static class MyClass {
    }

}
