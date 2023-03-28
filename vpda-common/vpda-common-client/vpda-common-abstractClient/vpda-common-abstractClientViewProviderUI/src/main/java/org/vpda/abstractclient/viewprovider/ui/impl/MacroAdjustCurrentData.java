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
package org.vpda.abstractclient.viewprovider.ui.impl;

import java.util.ArrayList;
import java.util.Collection;

import org.vpda.clientserver.viewprovider.AdjustCurrentData;
import org.vpda.clientserver.viewprovider.ViewProviderCallerItem;

/**
 * Will call adjust on its agregated items
 * 
 * @author kitko
 *
 */
public final class MacroAdjustCurrentData implements AdjustCurrentData {
    private final Collection<AdjustCurrentData> items;
    /**
     * 
     */
    private static final long serialVersionUID = -7547819911175792607L;

    /**
     * Creates MacroAdjustInitData
     * 
     * @param items
     */
    public MacroAdjustCurrentData(AdjustCurrentData... items) {
        this.items = new ArrayList<AdjustCurrentData>(items.length);
        for (AdjustCurrentData item : items) {
            this.items.add(item);
        }
    }

    @Override
    public void adjustCurrentData(ViewProviderCallerItem<?> item) {
        for (AdjustCurrentData myItem : items) {
            myItem.adjustCurrentData(item);
        }
    }

}
