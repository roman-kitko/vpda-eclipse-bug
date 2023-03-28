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
package org.vpda.clientserver.viewprovider.list.annotations;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.vpda.clientserver.viewprovider.list.ColumnsGroup;
import org.vpda.common.context.localization.LocKey;
import org.vpda.common.service.localization.LocKeyInfo;
import org.vpda.common.util.AnnotationConstants;
import org.vpda.internal.common.util.StringUtil;

/**
 * Annotation using which we can build {@link ColumnsGroup}
 * 
 * @author kitko
 *
 */
@Target({ ElementType.TYPE, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ColumnsGroupInfo {
    /** Id of group */
    public String localId() default AnnotationConstants.UNDEFINED_STRING;

    /** Localization key */
    public LocKeyInfo titleKey() default @LocKeyInfo(path = AnnotationConstants.UNDEFINED_STRING);

    /** Columns title prefix */
    public LocKeyInfo columnsTitleKeyPrefix() default @LocKeyInfo(path = AnnotationConstants.UNDEFINED_STRING);

    /** Prefix localization value for columns */
    public ColumnsPrefixLocalizerInfo columnsTitlePrefixInfo() default @ColumnsPrefixLocalizerInfo();

    /**
     * @author kitko
     *
     */
    static final class ColumnsGroupInfoRuntime implements ColumnsGroupInfo {
        private final String localId;
        private final LocKeyInfo titleKey;
        private final LocKeyInfo columnsTitlePrefix;
        private final ColumnsPrefixLocalizerInfo columnsPrefixLocalizerInfo;

        ColumnsGroupInfoRuntime(Builder builder) {
            this.localId = StringUtil.isNotEmpty(builder.localId) ? builder.localId : AnnotationConstants.UNDEFINED_STRING;
            this.titleKey = builder.titleKey != null ? new LocKeyInfo.Builder().setLocKey(builder.titleKey).build()
                    : new LocKeyInfo.Builder().setLocKey(new LocKey(AnnotationConstants.UNDEFINED_STRING)).build();
            this.columnsTitlePrefix = builder.columnsTitlePrefix != null ? new LocKeyInfo.Builder().setLocKey(builder.columnsTitlePrefix).build()
                    : new LocKeyInfo.Builder().setPath(AnnotationConstants.UNDEFINED_STRING).build();
            this.columnsPrefixLocalizerInfo = builder.columnsPrefixLocalizerInfo;

        }

        @Override
        public Class<? extends Annotation> annotationType() {
            return ColumnsGroupInfo.class;
        }

        @Override
        public String localId() {
            return localId;
        }

        @Override
        public LocKeyInfo titleKey() {
            return titleKey;
        }

        @Override
        public LocKeyInfo columnsTitleKeyPrefix() {
            return columnsTitlePrefix;
        }

        @Override
        public ColumnsPrefixLocalizerInfo columnsTitlePrefixInfo() {
            return columnsPrefixLocalizerInfo;
        }

    }

    /**
     * Builder for runtime instance of {@link ColumnsGroupInfo}
     * 
     * @author kitko
     *
     */
    public static final class Builder implements org.vpda.common.util.Builder<ColumnsGroupInfo> {
        private String localId;
        private LocKey titleKey;
        private LocKey columnsTitlePrefix;
        private ColumnsPrefixLocalizerInfo columnsPrefixLocalizerInfo;

        /**
         * @return the columnsPrefixLocalizerInfo
         */
        public ColumnsPrefixLocalizerInfo getColumnsPrefixLocalizerInfo() {
            return columnsPrefixLocalizerInfo;
        }

        /**
         * @param columnsPrefixLocalizerInfo the columnsPrefixLocalizerInfo to set
         * @return this
         */
        public Builder setColumnsPrefixLocalizerInfo(ColumnsPrefixLocalizerInfo columnsPrefixLocalizerInfo) {
            this.columnsPrefixLocalizerInfo = columnsPrefixLocalizerInfo;
            return this;
        }

        /**
         * @return the titleKey
         */
        public LocKey getTitleKey() {
            return titleKey;
        }

        /**
         * @param titleKey the titleKey to set
         * @return this
         */
        public Builder setTitleKey(LocKey titleKey) {
            this.titleKey = titleKey;
            return this;
        }

        /**
         * @return the columnsTitlePrefix
         */
        public LocKey getColumnsTitlePrefix() {
            return columnsTitlePrefix;
        }

        /**
         * @param columnsTitlePrefix the columnsTitlePrefix to set
         * @return this
         */
        public Builder setColumnsTitlePrefix(LocKey columnsTitlePrefix) {
            this.columnsTitlePrefix = columnsTitlePrefix;
            return this;
        }

        /**
         * @return the id
         */
        public String getLocalId() {
            return localId;
        }

        /**
         * @param localId the localId to set
         * @return this
         */
        public Builder setLocalId(String localId) {
            this.localId = localId;
            return this;
        }

        @Override
        public Class<? extends ColumnsGroupInfo> getTargetClass() {
            return ColumnsGroupInfo.class;
        }

        @Override
        public ColumnsGroupInfo build() {
            return new ColumnsGroupInfoRuntime(this);
        }

    }

}
