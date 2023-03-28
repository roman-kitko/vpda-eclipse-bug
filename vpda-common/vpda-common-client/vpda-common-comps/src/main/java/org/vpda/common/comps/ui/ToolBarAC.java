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

import org.vpda.internal.common.util.Assert;

/**
 * ToolBar component
 * 
 * @author kitko
 *
 */
public final class ToolBarAC extends AbstractContainerWithLayoutAC {
    private static final long serialVersionUID = 3910385369835681753L;
    private ACOrientation orientation;

    /** */
    public ToolBarAC() {
    }

    /**
     * Creates toolbar container with {@link ACOrientation#HORIZONTAL} orientation
     * 
     * @param id
     * @param orientation
     */
    public ToolBarAC(String id, ACOrientation orientation) {
        super(id);
        this.orientation = Assert.isNotNullArgument(orientation, "orientation");
    }

    /**
     * Creates toolbar with orientation
     * 
     * @param id
     */
    public ToolBarAC(String id) {
        this(id, ACOrientation.HORIZONTAL);
    }

    /**
     * @return orientation of component
     */
    public ACOrientation getOrientation() {
        return orientation;
    }

}
