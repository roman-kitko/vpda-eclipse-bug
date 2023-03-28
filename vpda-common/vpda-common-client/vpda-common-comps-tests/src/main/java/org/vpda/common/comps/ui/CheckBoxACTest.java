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

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.vpda.common.command.DummyCommand;
import org.vpda.common.comps.loc.ToggleButtonLocValue;

/**
 * @author kitko
 *
 */
public class CheckBoxACTest extends AbstractButtonACTest {

    /**
     * Test method
     */
    @Test
    public void testButtonViewProviderComponentImplStringButtonLocValueCommand() {
        CheckBoxAC c = new CheckBoxAC("test", new ToggleButtonLocValue("testButton"), DummyCommand.getInstance());
        assertNotNull(c.getId());
        assertNotNull(c.getCommand());
        assertNotNull(c.createLocValue());
    }

    @Override
    protected Object createTestValue() {
        return "testLabel";
    }

    @Override
    protected AbstractButtonAC createTestee() {
        return new CheckBoxAC("check1", "label", true);
    }

}
