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
package org.vpda.common.comps.ui;

import org.vpda.common.comps.loc.LabelLocValue;
import org.vpda.common.context.localization.LocKey;

/**
 * Component that has title border around
 * 
 * @author kitko
 *
 */
public final class TitledAC extends SingleComponentContainerAC<String, LabelLocValue> {

    private static final long serialVersionUID = -3086495332540154294L;

    /**
     * 
     */
    public TitledAC() {
    }

    /**
     * @param localId
     * @param content
     */
    public TitledAC(String localId, AbstractComponent content) {
        super(localId, content);
    }

    /**
     * @param localId
     * @param content
     * @param locKey
     */
    public TitledAC(String localId, AbstractComponent content, LocKey locKey) {
        super(localId, content);
        setLocKey(locKey);
    }

    /**
     * @param localId
     * @param content
     * @param locValue
     */
    public TitledAC(String localId, AbstractComponent content, LabelLocValue locValue) {
        super(localId, content);
        if (locValue == null) {
            locValue = new LabelLocValue(getId());
        }
        adjustFromLocValue(locValue);
        setValue(locValue.getLabel());
    }

    @Override
    public Class<LabelLocValue> getLocValueClass() {
        return LabelLocValue.class;
    }

    @Override
    public void adjustFromLocValue(LabelLocValue locValue) {
        if (locValue == null) {
            return;
        }
        setValue(locValue.getLabel());
    }

    @Override
    public String getCaption() {
        return getValue();
    }

}
