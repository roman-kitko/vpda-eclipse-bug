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

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.vpda.common.comps.SingleData;

/**
 * @author kitko
 *
 */
public class ViewProviderCurrentDataImplTest {

    private static ViewProviderCallerItem<?> testee;

    /**
     * Test method for
     * {@link org.vpda.clientserver.viewprovider.ViewProviderCallerItem#getCurrentData()}.
     */
    @Test
    public void testGetCurrentData() {
        assertNotNull(testee.getCurrentData());
    }

    /**
     * Test method for
     * {@link org.vpda.clientserver.viewprovider.ViewProviderCallerItem#getProviderId()}.
     */
    @Test
    public void testGetProviderId() {
        assertNotNull(testee.getProviderId());
    }

    /**
     * Test method for
     * {@link org.vpda.clientserver.viewprovider.ViewProviderCallerItem#castCurrrentData(java.lang.Class)}.
     */
    @Test
    public void testCastData() {
        assertNotNull(testee.castCurrrentData(SingleData.class));
    }

    private static ViewProviderCallerItem<?> createTestee() {
        return new ViewProviderCallerItem<SingleData<Long>>(ViewProviderTestHelper.createTestViewProviderID(), SingleData.create(1L));
    }

    /**
     * 
     */
    @BeforeAll
    public static void setupBeforeClass() {
        testee = createTestee();
    }

}
