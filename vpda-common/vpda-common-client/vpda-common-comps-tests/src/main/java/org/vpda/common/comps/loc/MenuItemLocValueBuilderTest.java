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
package org.vpda.common.comps.loc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.easymock.EasyMock;
import org.junit.jupiter.api.Test;
import org.vpda.common.context.TenementalContext;
import org.vpda.common.context.localization.LocKey;
import org.vpda.common.service.localization.LocalizationService;

/**
 * @author kitko
 *
 */
public class MenuItemLocValueBuilderTest extends AbstractButtonLocValueBuilderTest {

    @Override
    protected AbstractButtonLocValueBuilder<? extends AbstractButtonLocValue> createTestee() {
        return MenuItemLocValueBuilder.getInstance();
    }

    /** Dummy test */
    @Test
    public void testDummy() {

    }

    @Override
    protected void setupLocServiceMock(LocalizationService locService, LocKey locKey) {
        super.setupLocServiceMock(locService, locKey);
        EasyMock.expect(locService.localizeMessage(EasyMock.eq(locKey.createChildKey("accelerator")), EasyMock.isA(TenementalContext.class))).andReturn("Alt F").once();

    }

    @Override
    protected void testLocValue(AbstractButtonLocValue buttonLocValue) {
        super.testLocValue(buttonLocValue);
        assertTrue(buttonLocValue instanceof MenuItemLocValue);
        MenuItemLocValue m = (MenuItemLocValue) buttonLocValue;
        assertEquals("Alt F", m.getAccelerator());
    }
}
