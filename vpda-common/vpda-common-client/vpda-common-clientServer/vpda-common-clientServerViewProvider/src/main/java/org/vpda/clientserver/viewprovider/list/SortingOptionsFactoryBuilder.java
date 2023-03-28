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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.vpda.common.criteria.sort.Sort;
import org.vpda.common.criteria.sort.SortingOptions;

/**
 * Helper builder for SortingOptions
 * 
 * @author kitko
 *
 */
public final class SortingOptionsFactoryBuilder {

    private static final class FullOptionsImpl extends SortingOptions {
        private static final long serialVersionUID = 5314189334500328465L;

        private final List<String> allColumns;

        private FullOptionsImpl(ListViewProviderInfo info) {
            this.allColumns = new ArrayList<String>();
            for (Column col : info.getColumns()) {
                if (col.isInvisible()) {
                    continue;
                }
                this.allColumns.add(col.getId());
            }

        }

        @Override
        public boolean canDefineCustomSort() {
            return true;
        }

        @Override
        public List<String> getSortingColumns() {
            return Collections.unmodifiableList(allColumns);
        }

        @Override
        public List<Sort> getPredefinedSorts() {
            return Collections.emptyList();
        }

        @Override
        public boolean canSortByColumn(String columnId) {
            return allColumns.contains(columnId);
        }
    }

    private static final class CustomSortingImpl extends SortingOptions {
        private static final long serialVersionUID = 7342283689511507170L;

        private List<String> columns;

        @Override
        public List<String> getSortingColumns() {
            return Collections.unmodifiableList(columns);
        }

        private CustomSortingImpl(List<String> columns) {
            this.columns = new ArrayList<String>(columns);
        }

        @Override
        public boolean canDefineCustomSort() {
            return true;
        }

        @Override
        public List<Sort> getPredefinedSorts() {
            return Collections.emptyList();
        }

        @Override
        public boolean canSortByColumn(String columnId) {
            return columns.contains(columnId);
        }

    }

    private static final class PredefinedSortingImpl extends SortingOptions {
        private static final long serialVersionUID = -1223423775757540345L;

        private List<Sort> sorts;

        @Override
        public List<Sort> getPredefinedSorts() {
            return Collections.unmodifiableList(sorts);
        }

        private PredefinedSortingImpl(List<Sort> sorts) {
            this.sorts = new ArrayList<Sort>(sorts);
        }

        @Override
        public boolean canDefineCustomSort() {
            return false;
        }

        @Override
        public List<String> getSortingColumns() {
            return Collections.emptyList();
        }

        @Override
        public boolean canSortByColumn(String columnId) {
            return false;
        }
    }

    /**
     * @return {@link SortingOptions} where user can sort by all columns
     */
    public static SortingOptionsFactory createFullSortingOptionsFactory() {
        return new SortingOptionsFactory() {
            private static final long serialVersionUID = 1L;

            @Override
            public SortingOptions createSortingOptions(ListViewProviderInfo info) {
                return new FullOptionsImpl(info);
            }
        };
    }

    /**
     * @param columns
     * @return CustomSortingOptions for list of columns from which user can build
     *         sort
     */
    public static SortingOptionsFactory createCustomSortingOptionsFactory(final List<String> columns) {
        return new SortingOptionsFactory() {
            private static final long serialVersionUID = 1L;

            @Override
            public SortingOptions createSortingOptions(ListViewProviderInfo info) {
                return new CustomSortingImpl(columns);
            }
        };
    }

    /**
     * @param sorts
     * @return PredefinedSortingOptions with predefined sorts
     */
    public static SortingOptionsFactory createPredefinedSortingOptionsFactory(final List<Sort> sorts) {
        return new SortingOptionsFactory() {
            private static final long serialVersionUID = 1L;

            @Override
            public SortingOptions createSortingOptions(ListViewProviderInfo info) {
                return new PredefinedSortingImpl(sorts);
            }
        };
    }

}