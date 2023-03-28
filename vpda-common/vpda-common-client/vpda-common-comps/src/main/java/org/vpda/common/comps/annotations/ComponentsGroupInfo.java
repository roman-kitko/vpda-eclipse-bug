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
package org.vpda.common.comps.annotations;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.vpda.common.context.localization.LocKey;
import org.vpda.common.service.localization.LocKeyInfo;
import org.vpda.common.util.AnnotationConstants;
import org.vpda.internal.common.util.StringUtil;

/** Info for components group */
@Target({ ElementType.TYPE, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface ComponentsGroupInfo {
    /** Id of group */
    public String localId() default AnnotationConstants.UNDEFINED_STRING;

    /** Localization key */
    public LocKeyInfo titleKey() default @LocKeyInfo(path = AnnotationConstants.UNDEFINED_STRING);

    /** Components title key prefix */
    public LocKeyInfo componentsTitleKeyPrefix() default @LocKeyInfo(path = AnnotationConstants.UNDEFINED_STRING);

    /** Prefix localization value for columns */
    public ComponentsPrefixLocalizerInfo componentsTitlePrefixInfo() default @ComponentsPrefixLocalizerInfo();

    /** Runtime representation of ComponentsGroupInfo */
    static final class ComponentsGroupInfoRuntime implements ComponentsGroupInfo {
        private final String localId;
        private final LocKeyInfo titleKey;
        private final LocKeyInfo componentsTitleKeyPrefix;
        private final ComponentsPrefixLocalizerInfo componentsPrefixLocalizerInfo;

        private ComponentsGroupInfoRuntime(Builder builder) {
            this.localId = StringUtil.isNotEmpty(builder.localId) ? builder.localId : AnnotationConstants.UNDEFINED_STRING;
            this.titleKey = builder.titleKey != null ? new LocKeyInfo.Builder().setLocKey(builder.titleKey).build()
                    : new LocKeyInfo.Builder().setLocKey(new LocKey(AnnotationConstants.UNDEFINED_STRING)).build();
            this.componentsTitleKeyPrefix = builder.componentsTitleKeyPrefix != null ? new LocKeyInfo.Builder().setLocKey(builder.componentsTitleKeyPrefix).build()
                    : new LocKeyInfo.Builder().setPath(AnnotationConstants.UNDEFINED_STRING).build();
            this.componentsPrefixLocalizerInfo = builder.getComponentsPrefixLocalizerInfo();
        }

        @Override
        public Class<? extends Annotation> annotationType() {
            return ComponentsGroupInfo.class;
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
        public LocKeyInfo componentsTitleKeyPrefix() {
            return componentsTitleKeyPrefix;
        }

        @Override
        public ComponentsPrefixLocalizerInfo componentsTitlePrefixInfo() {
            return componentsPrefixLocalizerInfo;
        }
    }

    /** Builder for ComponentsGroupInfo */
    public static final class Builder implements org.vpda.common.util.Builder<ComponentsGroupInfo> {
        private String localId;
        private LocKey titleKey;
        private LocKey componentsTitleKeyPrefix;
        private ComponentsPrefixLocalizerInfo ComponentsPrefixLocalizerInfo;

        /**
         * @return the localId
         */
        public String getLocalId() {
            return localId;
        }

        /**
         * sets local id
         * 
         * @param localId the localId to set
         * @return this
         */
        public Builder setLocalId(String localId) {
            this.localId = localId;
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
         * @return the componentsTitlePrefix
         */
        public LocKey getComponentsTitleKeyPrefix() {
            return componentsTitleKeyPrefix;
        }

        /**
         * @param componentsTitleKeyPrefix the componentsTitlePrefix to set
         * @return this
         */
        public Builder setComponentsTitleKeyPrefix(LocKey componentsTitleKeyPrefix) {
            this.componentsTitleKeyPrefix = componentsTitleKeyPrefix;
            return this;
        }

        /**
         * @return the componentsPrefixLocalizerInfo
         */
        public ComponentsPrefixLocalizerInfo getComponentsPrefixLocalizerInfo() {
            return ComponentsPrefixLocalizerInfo;
        }

        /**
         * @param componentsPrefixLocalizerInfo the componentsPrefixLocalizerInfo to set
         */
        public void setComponentsPrefixLocalizerInfo(ComponentsPrefixLocalizerInfo componentsPrefixLocalizerInfo) {
            ComponentsPrefixLocalizerInfo = componentsPrefixLocalizerInfo;
        }

        @Override
        public Class<? extends ComponentsGroupInfo> getTargetClass() {
            return ComponentsGroupInfo.class;
        }

        @Override
        public ComponentsGroupInfo build() {
            return new ComponentsGroupInfoRuntime(this);
        }

    }

}
