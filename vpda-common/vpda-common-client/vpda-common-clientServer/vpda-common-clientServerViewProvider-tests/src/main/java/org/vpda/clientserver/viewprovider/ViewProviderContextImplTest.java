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
package org.vpda.clientserver.viewprovider;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.vpda.common.comps.SingleData;

/**
 * @author kitko
 *
 */
public class ViewProviderContextImplTest {

    /**
     * Test
     */
    @Test
    public void testViewProviderContextImplViewProviderID() {
        ViewProviderID id = new ViewProviderID(ViewProviderID.generateId(), ViewProviderTestHelper.createTestViewProviderDef());
        ViewProviderCallerItem<SingleData<Long>> callerItem = new ViewProviderCallerItem<SingleData<Long>>(id, SingleData.create(1L));
        ViewProviderContext viewProviderContextImpl = new ViewProviderContext(callerItem);
        assertTrue(viewProviderContextImpl.getCallers().size() == 1);
        assertEquals(callerItem, (viewProviderContextImpl.getCallers().get(0)));
    }

    /**
     * Test
     */
    @Test
    public void testViewProviderContextImplViewProviderContextViewProviderID() {
        ViewProviderID id1 = new ViewProviderID(ViewProviderID.generateId(), ViewProviderTestHelper.createTestViewProviderDef());
        ViewProviderCallerItem<SingleData<Long>> callerItem = new ViewProviderCallerItem<SingleData<Long>>(id1, SingleData.create(1L));
        ViewProviderContext viewProviderContextImpl1 = new ViewProviderContext(callerItem);
        ViewProviderID id2 = new ViewProviderID(ViewProviderID.generateId(), ViewProviderTestHelper.createTestViewProviderDef());
        ViewProviderCallerItem<SingleData<Long>> callerItem2 = new ViewProviderCallerItem<SingleData<Long>>(id2, SingleData.create(1L));
        ViewProviderContext viewProviderContextImpl2 = new ViewProviderContext(viewProviderContextImpl1, callerItem2);
        assertTrue(viewProviderContextImpl2.getCallers().size() == 2);
        assertSame(id1, viewProviderContextImpl2.getCallers().get(0).getProviderId());
        assertSame(id2, viewProviderContextImpl2.getCallers().get(1).getProviderId());

    }

    /**
     * Test
     */
    @Test
    public void testViewProviderContextImplListOfViewProviderID() {
        ViewProviderID id1 = new ViewProviderID(ViewProviderID.generateId(), ViewProviderTestHelper.createTestViewProviderDef());
        ViewProviderID id2 = new ViewProviderID(ViewProviderID.generateId(), ViewProviderTestHelper.createTestViewProviderDef());
        ViewProviderCallerItem<SingleData<Long>> callerItem1 = new ViewProviderCallerItem<SingleData<Long>>(id1, SingleData.create(1L));
        ViewProviderCallerItem<SingleData<Long>> callerItem2 = new ViewProviderCallerItem<SingleData<Long>>(id2, SingleData.create(1L));
        ViewProviderContext viewProviderContextImpl = new ViewProviderContext(Arrays.asList(callerItem1, callerItem2));
        assertTrue(viewProviderContextImpl.getCallers().size() == 2);
        assertSame(id1, viewProviderContextImpl.getCallers().get(0).getProviderId());
        assertSame(id2, viewProviderContextImpl.getCallers().get(1).getProviderId());

    }

}
