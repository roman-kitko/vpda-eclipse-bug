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

import java.util.HashMap;
import java.util.Map;

/** */
public class MultiOptionACTest extends AbstractComponentTest {

    @Override
    protected AbstractComponent createTestee() {
        return new MultiOptionAC("m");
    }

    @Override
    protected Object createTestValue() {
        ToggleButtonAC b1 = new ToggleButtonAC("b1", "b1", true);
        ToggleButtonAC b2 = new ToggleButtonAC("b2", "b2", false);
        ToggleButtonAC b3 = new ToggleButtonAC("b2", "b3", false);
        Map<String, ToggleButtonAC> m = new HashMap<String, ToggleButtonAC>();
        m.put(b1.getId(), b1);
        m.put(b2.getId(), b2);
        m.put(b3.getId(), b3);
        return m;
    }

}
