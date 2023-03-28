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
package org.vpda.common.comps.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * @author kitko
 *
 */
public class IntegerFieldACTest extends NumberFieldACTest {

    /**
     * Test method
     */
    @Test
    public void testIntegerFieldViewProviderComponentImpl() {
        IntegerFieldAC b = new IntegerFieldAC("test", new Integer(1));
        assertEquals(new Integer(1), b.getValue());
    }

    @Override
    protected Object createTestValue() {
        return new Integer(1);
    }

    @Override
    protected IntegerFieldAC createTestee() {
        return new IntegerFieldAC("test", new Integer(1));
    }

}
