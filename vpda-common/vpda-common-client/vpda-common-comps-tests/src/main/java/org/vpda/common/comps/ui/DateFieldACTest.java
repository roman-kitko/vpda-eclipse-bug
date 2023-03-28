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
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.jupiter.api.Test;

/**
 * @author kitko
 *
 */
public class DateFieldACTest extends AbstractComponentTest {

    /**
     * Test method
     */
    @Test
    public void testDateFieldViewProviderComponentImpl() {
        DateFieldAC b = new DateFieldAC("test", new Date(1));
        assertEquals(new Date(1), b.getValue());
    }

    @Override
    protected Object createTestValue() {
        return new Date(1);
    }

    /**
     * Test patters methods
     */
    @Test
    public void testPatterns() {
        DateFieldAC b = new DateFieldAC("id", null, "yyyy_DD_MM");
        assertNotNull(b.getFormatPattern());
        assertNull(b.getFormat());
        b = new DateFieldAC("id", null, new SimpleDateFormat("yyyy_DD_MM"));
        assertNull(b.getFormatPattern());
        assertNotNull(b.getFormat());
        b.setFormatPattern("xxxx");
        assertNotNull(b.getFormatPattern());
        b.setDateFormat(null);
        assertNull(b.getFormat());

    }

    @Override
    protected DateFieldAC createTestee() {
        return new DateFieldAC("test", new Date(1));
    }

}
