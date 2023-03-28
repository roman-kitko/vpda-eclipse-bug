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

import org.vpda.common.context.localization.LocKey;

/**
 * How children of provider ui are aligned
 * 
 * @author kitko
 *
 */
public enum ViewProviderUIChildrenOrientation {
    /** Align vertically */
    VERTICAL {
        @Override
        public ViewProviderUIChildrenOrientation flipOrientation() {
            return HORIZONTAL;
        }

        @Override
        public LocKey getButtonIconLocKey() {
            return LocKey.path("common/splitVertical.png");
        }

        @Override
        public LocKey getTitleLocKey() {
            return LocKey.path("viewprovider/showChildrenVertical");
        }

    },
    /** Align horizontally */
    HORIZONTAL {
        @Override
        public ViewProviderUIChildrenOrientation flipOrientation() {
            return VERTICAL;
        }

        @Override
        public LocKey getButtonIconLocKey() {
            return LocKey.path("common/splitHorizontal.png");
        }

        @Override
        public LocKey getTitleLocKey() {
            return LocKey.path("viewprovider/showChildrenHorizontal");
        }
    };

    /**
     * @return new orientation
     */
    public abstract ViewProviderUIChildrenOrientation flipOrientation();

    /**
     * 
     * @return icon localization key
     */
    public abstract LocKey getButtonIconLocKey();

    /**
     * @return title localization key
     */
    public abstract LocKey getTitleLocKey();
}
