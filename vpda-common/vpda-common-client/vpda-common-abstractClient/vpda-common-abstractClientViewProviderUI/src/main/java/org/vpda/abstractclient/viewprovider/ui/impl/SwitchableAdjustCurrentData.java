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
package org.vpda.abstractclient.viewprovider.ui.impl;

import org.vpda.clientserver.viewprovider.AdjustCurrentData;
import org.vpda.clientserver.viewprovider.ViewProviderCallerItem;

/**
 * {@link AdjustCurrentData} that can be disabled
 * 
 * @author kitko
 *
 */
public final class SwitchableAdjustCurrentData implements AdjustCurrentData {
    private static final long serialVersionUID = -803842006398271780L;
    private boolean isOn;
    private final AdjustCurrentData delegate;

    /**
     * @param delegate
     * @param isOn
     */
    public SwitchableAdjustCurrentData(AdjustCurrentData delegate, boolean isOn) {
        super();
        this.delegate = delegate;
        this.isOn = isOn;
    }

    /**
     * @param delegate
     */
    public SwitchableAdjustCurrentData(AdjustCurrentData delegate) {
        this(delegate, true);
    }

    /**
     * switch this on
     */
    public void switchOn() {
        this.isOn = true;
    }

    /**
     * Make off
     */
    public void switchOff() {
        isOn = false;
    }

    /**
     * @return true if on
     */
    public boolean isSwitchedOn() {
        return isOn;
    }

    @Override
    public void adjustCurrentData(ViewProviderCallerItem<?> item) {
        if (!isOn) {
            return;
        }
        delegate.adjustCurrentData(item);
    }

}
