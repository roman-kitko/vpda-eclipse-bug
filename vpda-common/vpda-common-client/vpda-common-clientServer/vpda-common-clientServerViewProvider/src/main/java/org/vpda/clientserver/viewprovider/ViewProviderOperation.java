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
package org.vpda.clientserver.viewprovider;

import org.vpda.common.types.CrudOperation;

/**
 * Operation upon viewProvider.
 * 
 * @author rkitko
 *
 */
public enum ViewProviderOperation implements CrudOperation {
    /** Insert new data operation */
    INSERT_NEW {
        @Override
        public boolean needCurrentData() {
            return false;
        }

        @Override
        public boolean isEditingData() {
            return true;
        }

        @Override
        public boolean isInsertingData() {
            return true;
        }
    },
    /** Insert data copy operation */
    INSERT_COPY {
        @Override
        public boolean needCurrentData() {
            return true;
        }

        @Override
        public boolean isEditingData() {
            return true;
        }

        @Override
        public boolean isInsertingData() {
            return true;
        }

    },
    /** Read operation */
    READ {
        @Override
        public boolean needCurrentData() {
            return true;
        }

        @Override
        public boolean isEditingData() {
            return false;
        }

        @Override
        public boolean isInsertingData() {
            return false;
        }

    },
    /** Update operation */
    UPDATE {
        @Override
        public boolean needCurrentData() {
            return true;
        }

        @Override
        public boolean isEditingData() {
            return true;
        }

        @Override
        public boolean isInsertingData() {
            return false;
        }

    },
    /** Delete operation */
    DELETE {
        @Override
        public boolean needCurrentData() {
            return true;
        }

        @Override
        public boolean isEditingData() {
            return true;
        }

        @Override
        public boolean isInsertingData() {
            return false;
        }

    };

    /**
     * @return true if operation needs current data
     */
    @Override
    public abstract boolean needCurrentData();

    /**
     * @return true if operation is editing current or new data
     */
    @Override
    public abstract boolean isEditingData();

    /**
     * @return true if operation is inserting new data
     */
    @Override
    public abstract boolean isInsertingData();
}
