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
package org.vpda.clientserver.viewprovider.list;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.vpda.common.criteria.CriteriaTree;
import org.vpda.common.criteria.CriteriaTreeImpl;
import org.vpda.common.criteria.Reference;
import org.vpda.common.criteria.sort.Sort;
import org.vpda.common.criteria.sort.SortItem;
import org.vpda.internal.common.util.JsonUtils;

/**
 * @author kitko
 *
 */
public class ListViewProviderSettingsImplTest {

    /**
     * Test method for
     * {@link org.vpda.clientserver.viewprovider.list.ListViewProviderSettings#getVisibleColumnIds()}.
     */
    @Test
    public void testGetVisibleColumnIds() {
        assertEquals(Arrays.asList("c1", "c3"), createTestee().getVisibleColumnIds());
    }

    /**
     * Test method for
     * {@link org.vpda.clientserver.viewprovider.list.ListViewProviderSettings#getUserColumnSettings()}.
     */
    @Test
    public void testListUserColumnSettings() {
        ListViewProviderSettings settings = createTestee();
        assertNotNull(settings.getUserColumnSettings());
        assertTrue(settings.getUserColumnSettings().contains(new UserColumnSettings("c1")));
    }

    /**
     * Test
     * 
     * @Test public void testAddUserColumnSettings() {
     *       ListViewProviderSettings.Builder settings = createTesteeBuilder();
     *       assertEquals(3,settings.getColumnsSettings().size());
     *       settings.addUserColumnSettings(new UserColumnSettings("c4",true));
     *       assertEquals(4,settings.getColumnsSettings().size()); }
     * 
     *       /** Test
     */
    @Test
    public void testListViewSettingsImplListOfUserColumnSettings() {
        assertTrue(createTestee().getUserColumnSettings().contains(new UserColumnSettings("c1")));
    }

    /**
     * Test method for
     * {@link org.vpda.clientserver.viewprovider.list.ListViewProviderSettings#getColumnSettings(java.lang.String)}.
     */
    @Test
    public void testGetColumnSettings() {
        ListViewProviderSettings settings = createTestee();
        assertNotNull(settings.getColumnSettings("c1"));
        assertEquals(new UserColumnSettings("c1", true), settings.getColumnSettings("c1"));
    }

    private ListViewProviderSettings.Builder createTesteeBuilder() {
        UserColumnSettings c1 = new UserColumnSettings("c1", true);
        UserColumnSettings c2 = new UserColumnSettings("c2", false);
        UserColumnSettings c3 = new UserColumnSettings("c3", true);
        ListViewProviderSettings.Builder builder = new ListViewProviderSettings.Builder();
        builder.addUserColumnSettings(c1, c2, c3);
        builder.setUserSort(new Sort(Arrays.asList(new SortItem("c1"))));
        CriteriaTree tree = new CriteriaTreeImpl("myCriteria", new Reference<String>("myReference"));
        builder.setGenericUserCriteria(tree);
        return builder;
    }

    /**
     * Test
     * 
     * @throws IOException
     */
    @Test
    public void testJSON() throws IOException {
        ListViewProviderSettings testee = createTestee();
        String jsonString = JsonUtils.toJsonString(testee);
        ListViewProviderSettings fromJsonString = JsonUtils.fromJsonString(jsonString, ListViewProviderSettings.class);
        assertEquals(testee, fromJsonString);
    }

    private ListViewProviderSettings createTestee() {
        return createTesteeBuilder().build();
    }

}
