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

import java.util.ArrayList;
import java.util.List;

import org.vpda.clientserver.viewprovider.list.ListViewResults;
import org.vpda.clientserver.viewprovider.list.ListViewRow;

/**
 * Helper utilities for view privider module
 * 
 * @author kitko
 *
 */
public class ViewProviderTestHelper {
    /**
     * 
     * @return test ViewProviderDef
     */
    public static ViewProviderDef createTestViewProviderDef() {
        ViewProviderDef def = new ViewProviderDef.Builder().setKind(ViewProviderKind.ListViewProvider).setImplClassName(ViewProvider.class.getName()).build();
        return def;
    }

    /**
     * 
     * @return test ViewProviderID
     */
    public static ViewProviderID createTestViewProviderID() {
        return new ViewProviderID("1", createTestViewProviderDef());
    }

    /**
     * @return test list view reseults
     */
    public static ListViewResults createTestListViewResults() {
        List<ListViewRow> rows = new ArrayList<ListViewRow>(2);
        rows.add(new ListViewRow(new Object[] { "a1", "b1" }));
        rows.add(new ListViewRow(new Object[] { "a2", "b2" }));
        List<String> columns = new ArrayList<String>(2);
        columns.add("id1");
        columns.add("id2");
        ListViewResults listViewResults = new ListViewResults(rows, true, true);
        return listViewResults;
    }

}
