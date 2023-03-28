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

import java.io.Serializable;

/**
 * Encapsulates info we need to open view provider regardless concrete ui
 * information
 * 
 * @author kitko
 *
 */
public abstract class AbstractViewProviderOpenInfo implements Serializable, ViewProviderOpenInfo {
    private static final long serialVersionUID = 8278162263565319633L;

    /** Context that ui will use as start context */
    protected ViewProviderContext viewProviderContext;
    /** Definition of provider */
    protected final ViewProviderDef viewProviderDef;
    /** Adjuster of current data */
    protected final AdjustCurrentData adjustCurrentData;
    /** Basic operation */
    protected final ViewProviderOperation operation;
    /** User interface information */
    protected final ViewProviderUIInfo uiInfo;
    /** Initial provider properties */
    protected final ViewProviderInitProperties initProperties;

    @Override
    public ViewProviderContext getViewProviderContext() {
        return viewProviderContext;
    }

    @Override
    public ViewProviderDef getViewProviderDef() {
        return viewProviderDef;
    }

    /**
     * Creates AbstractViewProviderOpenInfo using builder
     * 
     * @param builder
     */
    protected AbstractViewProviderOpenInfo(Builder builder) {
        this.adjustCurrentData = builder.getAdjustCurrentData();
        this.operation = builder.getOperation();
        this.uiInfo = builder.getUiInfo();
        this.viewProviderContext = builder.getViewProviderContext();
        this.viewProviderDef = builder.getViewProviderDef();
        this.initProperties = builder.getInitProperties();
    }

    /**
     * @param viewProviderContext the viewProviderContext to set
     */
    @Override
    public void setViewProviderContext(ViewProviderContext viewProviderContext) {
        this.viewProviderContext = viewProviderContext;
    }

    @Override
    public AdjustCurrentData getAdjustData() {
        return adjustCurrentData;
    }

    @Override
    public ViewProviderOperation getOperation() {
        return operation;
    }

    @Override
    public ViewProviderUIInfo getViewProviderUIInfo() {
        return uiInfo;
    }

    @Override
    public ViewProviderInitProperties getInitProperties() {
        return initProperties;
    }

    /**
     * Builder for {@link AbstractViewProviderOpenInfo}
     * 
     * @author kitko
     * @param <T> type of AbstractViewProviderOpenInfo
     *
     */
    public abstract static class Builder<T extends AbstractViewProviderOpenInfo> implements org.vpda.common.util.Builder<T> {
        /** Context that ui will use as start context */
        protected ViewProviderContext viewProviderContext;
        /** Definition of provider */
        protected ViewProviderDef viewProviderDef;
        /** Adjuster of current data */
        protected AdjustCurrentData adjustCurrentData;
        /** Basic operation */
        protected ViewProviderOperation operation;
        /** User interface information */
        protected ViewProviderUIInfo uiInfo;
        /** Initial properties */
        protected ViewProviderInitProperties initProperties;

        /**
         * @return the viewProviderContext
         */
        public ViewProviderContext getViewProviderContext() {
            return viewProviderContext;
        }

        /**
         * @param viewProviderContext the viewProviderContext to set
         * @return this
         */
        public Builder<T> setViewProviderContext(ViewProviderContext viewProviderContext) {
            this.viewProviderContext = viewProviderContext;
            return this;
        }

        /**
         * @return the viewProviderDef
         */
        public ViewProviderDef getViewProviderDef() {
            return viewProviderDef;
        }

        /**
         * @param viewProviderDef the viewProviderDef to set
         * @return this
         */
        public Builder<T> setViewProviderDef(ViewProviderDef viewProviderDef) {
            this.viewProviderDef = viewProviderDef;
            return this;
        }

        /**
         * @return the adjustCurrentData
         */
        public AdjustCurrentData getAdjustCurrentData() {
            return adjustCurrentData;
        }

        /**
         * @param adjustCurrentData the adjustCurrentData to set
         * @return this
         */
        public Builder<T> setAdjustCurrentData(AdjustCurrentData adjustCurrentData) {
            this.adjustCurrentData = adjustCurrentData;
            return this;
        }

        /**
         * @return the operation
         */
        public ViewProviderOperation getOperation() {
            return operation;
        }

        /**
         * @param operation the operation to set
         * @return this
         */
        public Builder<T> setOperation(ViewProviderOperation operation) {
            this.operation = operation;
            return this;
        }

        /**
         * @return the uiInfo
         */
        public ViewProviderUIInfo getUiInfo() {
            return uiInfo;
        }

        /**
         * @param uiInfo the uiInfo to set
         * @return this
         */
        public Builder<T> setUiInfo(ViewProviderUIInfo uiInfo) {
            this.uiInfo = uiInfo;
            return this;
        }

        /**
         * @return the initProperties
         */
        public ViewProviderInitProperties getInitProperties() {
            return initProperties;
        }

        /**
         * @param initProperties the initProperties to set
         * @return this
         */
        public Builder<T> setInitProperties(ViewProviderInitProperties initProperties) {
            this.initProperties = initProperties;
            return this;
        }

        /**
         * Sets values from AbstractViewProviderOpenInfo
         * 
         * @param info
         * @return this
         */
        public Builder<T> setValues(ViewProviderOpenInfo info) {
            this.adjustCurrentData = info.getAdjustData();
            this.initProperties = info.getInitProperties();
            this.operation = info.getOperation();
            this.viewProviderContext = info.getViewProviderContext();
            this.viewProviderDef = info.getViewProviderDef();
            return this;
        }
    }

}
