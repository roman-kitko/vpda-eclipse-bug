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

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

/**
 * @author kitko
 *
 */
public class DecimalFieldACTest extends NumberFieldACTest {

    /**
     * Test method
     */
    @Test
    public void testDecimalFieldViewProviderComponentImpl() {
        DecimalFieldAC b = new DecimalFieldAC("test", new BigDecimal(1));
        assertEquals(new BigDecimal(1), b.getValue());
    }

    @Override
    protected Object createTestValue() {
        return new BigDecimal(1);
    }

    @Override
    protected DecimalFieldAC createTestee() {
        return new DecimalFieldAC("test", new BigDecimal(1));
    }

}
