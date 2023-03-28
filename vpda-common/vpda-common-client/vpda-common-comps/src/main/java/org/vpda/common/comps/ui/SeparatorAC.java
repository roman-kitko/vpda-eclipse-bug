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

import org.vpda.common.comps.loc.AbstractCompLocValue;

/**
 * Separator separates some sections horizontally or vertically
 * 
 * @author kitko
 *
 */
public final class SeparatorAC extends AbstractComponent<Void, AbstractCompLocValue> {

    /**
     * Creates the horizontal separator
     */
    public SeparatorAC() {
        this(ACOrientation.HORIZONTAL);
    }

    /**
     * Creates separator with orientation
     * 
     * @param orientation
     */
    public SeparatorAC(ACOrientation orientation) {
        super("separator");
        this.orientation = org.vpda.internal.common.util.Assert.isNotNullArgument(orientation, "orientation");
    }

    private final ACOrientation orientation;

    private static final long serialVersionUID = -7704466241491898228L;

    @Override
    protected void adjustFromLocValue(AbstractCompLocValue locValue) {
    }

    @Override
    protected AbstractCompLocValue createLocValue() {
        return null;
    }

    @Override
    public Class<AbstractCompLocValue> getLocValueClass() {
        return AbstractCompLocValue.class;
    }

    /**
     * @return the orientation
     */
    public ACOrientation getOrientation() {
        return orientation;
    }

}
