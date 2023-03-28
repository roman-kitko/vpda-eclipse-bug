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

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.vpda.clientserver.viewprovider.ViewProviderConstants;
import org.vpda.common.context.TenementalContext;
import org.vpda.common.context.localization.LocKey;
import org.vpda.common.service.localization.LocalizationService;

/**
 * Page size settings
 * 
 * @author kitko
 *
 */
public interface ListViewPagingSize extends Serializable {
    /**
     * Will localize value
     * 
     * @param locService
     * @param context
     * @return localized value
     */
    public String localizeValue(LocalizationService locService, TenementalContext context);

    /**
     * Relative page size
     * 
     * @author kitko
     *
     */
    public static enum RelativeSize implements ListViewPagingSize {
        /** Max rows to fit full page */
        MAX_ROWS_PER_PAGE {
            @Override
            public String localizeValue(LocalizationService locService, TenementalContext context) {
                return locService.localizeString(LocKey.pathAndKey(ViewProviderConstants.VIEW_PROVDIER_LOC_PATH, "maxRowsPerPage"), context, "Max rows per page");
            }
        },
        /** Half of the rows to fit page */
        HALF_PAGE_SIZE {
            @Override
            public String localizeValue(LocalizationService locService, TenementalContext context) {
                return locService.localizeString(LocKey.pathAndKey(ViewProviderConstants.VIEW_PROVDIER_LOC_PATH, "halfPageSize"), context, "Half page size");
            }

        }

    }

    /** Concrete size */
    public abstract class ConcreteSize implements ListViewPagingSize, Serializable {
        private static final long serialVersionUID = 3045110777168556789L;
        /** Rows per page */
        protected final int pageSize;

        /**
         * @param pageSize
         */
        protected ConcreteSize(int pageSize) {
            this.pageSize = pageSize;
        }

        /**
         * @return pageSize
         */
        public int getPageSize() {
            return pageSize;
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + pageSize;
            return result;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            ConcreteSize other = (ConcreteSize) obj;
            if (pageSize != other.pageSize)
                return false;
            return true;
        }

        @Override
        public String toString() {
            return "ConcreteSize : " + Integer.toString(pageSize);
        }
    }

    /** Exact predefined number of rows per page */
    public final class ConcretePredefinedSize extends ConcreteSize implements ListViewPagingSize, Serializable {
        private static final long serialVersionUID = 6437887185427601367L;
        private static final Map<Integer, ConcretePredefinedSize> cache = new HashMap<>();
        static {
            for (int i = 0; i <= 1000; i++) {
                cache.put(i, new ConcretePredefinedSize(i));
            }
        }

        /**
         * Creates ConcreteSize
         * 
         * @param pageSize
         */
        private ConcretePredefinedSize(int pageSize) {
            super(pageSize);
        }

        /**
         * @param size
         * @return ConcreteSize
         */
        public static ConcretePredefinedSize create(int size) {
            ConcretePredefinedSize v = cache.get(size);
            if (v != null) {
                return v;
            }
            return new ConcretePredefinedSize(size);
        }

        @Override
        public String toString() {
            return "Predefined : " + Integer.toString(pageSize);
        }

        @Override
        public String localizeValue(LocalizationService locService, TenementalContext context) {
            return locService.localizeString(LocKey.pathAndKey(ViewProviderConstants.VIEW_PROVDIER_LOC_PATH, "concretePredefinedPageSize"), context, "Concrete predefined page size") + " : "
                    + pageSize;
        }

    }

    /**
     * Custom size
     * 
     * @author kitko
     *
     */
    public final class ConcreteCustomSize extends ConcreteSize implements ListViewPagingSize, Serializable {
        private static final long serialVersionUID = 6437887185427601367L;
        private static final Map<Integer, ConcreteCustomSize> cache = new HashMap<>();
        static {
            for (int i = 0; i <= 1000; i++) {
                cache.put(i, new ConcreteCustomSize(i));
            }
        }

        /**
         * Creates ConcreteSize
         * 
         * @param pageSize
         */
        private ConcreteCustomSize(int pageSize) {
            super(pageSize);
        }

        /**
         * @param size
         * @return ConcreteSize
         */
        public static ConcreteCustomSize create(int size) {
            ConcreteCustomSize v = cache.get(size);
            if (v != null) {
                return v;
            }
            return new ConcreteCustomSize(size);
        }

        @Override
        public String toString() {
            return "CustomSize : " + Integer.toString(pageSize);
        }

        @Override
        public String localizeValue(LocalizationService locService, TenementalContext context) {
            return locService.localizeString(LocKey.pathAndKey(ViewProviderConstants.VIEW_PROVDIER_LOC_PATH, "customPageSize"), context, "Custom page size") + " : " + pageSize;
        }

    }

}
