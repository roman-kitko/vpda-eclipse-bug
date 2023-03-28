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
/**
 * 
 */
package org.vpda.abstractclient.viewprovider.ui.list;

import org.vpda.abstractclient.viewprovider.ui.ViewProviderUIListenersSupport;

/**
 * Extension provider ui listener support for {@link ListViewProviderUI}
 * providers.
 * 
 * @author kitko
 *
 */
public interface ListViewProviderUIListenersSupport extends ViewProviderUIListenersSupport {

    /**
     * Adds ListSelectionListener.
     * 
     * @param listener
     */
    public void addListViewSelectionListener(ListSelectionListener listener);

    @Override
    public ListViewProviderUI getProviderUI();
}
