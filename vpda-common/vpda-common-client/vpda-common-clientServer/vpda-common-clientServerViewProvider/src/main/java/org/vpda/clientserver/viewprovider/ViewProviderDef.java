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

import java.io.Serializable;

import org.vpda.common.dto.annotations.DTOConcrete;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Definition ID of ViewProvider. It contains form definition
 * class,viewProviderKind and code. Using this information server can choose how
 * it will construct real {@link ViewProvider}
 * 
 * @author kitko
 * 
 */
@JsonInclude(Include.NON_NULL)
@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
@DTOConcrete
public final class ViewProviderDef implements Serializable {
    private static final long serialVersionUID = 2546224722944877125L;
    private final ViewProviderKind kind;
    private final String code;

    private final String implClassName;
    private final String factoryClassName;

    private final String spiClassName;
    private final String spiFactoryClassName;
    @JsonIgnore
    private String definitionID;

    /**
     * JSON creator metod
     * 
     * @param kind
     * @param code
     * @param implClassName
     * @param factoryClassName
     * @param spiClassName
     * @param spiFactoryClassName
     * @return ViewProviderDef
     */
    @JsonCreator
    public static ViewProviderDef fromJson(@JsonProperty("kind") ViewProviderKind kind, @JsonProperty("code") String code, @JsonProperty("implClassName") String implClassName,
            @JsonProperty("factoryClassName") String factoryClassName, @JsonProperty("spiClassName") String spiClassName, @JsonProperty("spiFactoryClassName") String spiFactoryClassName) {
        Builder builder = new Builder();
        builder.setCode(code).setFactoryClassName(spiFactoryClassName).setImplClassName(implClassName).setKind(kind).setSpiClassName(spiClassName).setSpiFactoryClassName(spiFactoryClassName);
        return builder.build();
    }

    private ViewProviderDef(Builder builder) {
        this.code = builder.code;
        this.implClassName = builder.implClassName;
        this.factoryClassName = builder.factoryClassName;
        this.spiClassName = builder.spiClassName;
        this.spiFactoryClassName = builder.spiFactoryClassName;
        this.kind = builder.kind;
    }

    /**
     * 
     * @return token that can identify provider def
     */
    @JsonIgnore
    public String getDefinitionID() {
        if (definitionID != null) {
            return definitionID;
        }
        return (definitionID = computeDefinitionID());
    }

    private String computeDefinitionID() {
        StringBuilder builder = new StringBuilder();
        if (code != null) {
            builder.append("_").append(code);
        }
        if (implClassName != null) {
            builder.append("_").append(implClassName);
        }
        if (factoryClassName != null) {
            builder.append("_").append(factoryClassName);
        }
        if (spiClassName != null) {
            builder.append("_").append(spiClassName);
        }
        if (spiFactoryClassName != null) {
            builder.append("_").append(spiFactoryClassName);
        }
        builder.append(kind);
        return builder.toString();
    }

    /**
     * @return Returns the implementation class name
     */
    public String getImplClassName() {
        return implClassName;
    }

    /**
     * @return the factoryClassName
     */
    public String getFactoryClassName() {
        return factoryClassName;
    }

    /**
     * @return Returns the id.
     */
    public String getCode() {
        return code;
    }

    /**
     * @return the viewProviderKind
     */
    public ViewProviderKind getKind() {
        return kind;
    }

    /**
     * @return localized title
     */
    @JsonIgnore
    public String getCodeTitle() {
        String title = code;
        if (title == null) {
            title = implClassName;
        }
        return title;
    }

    /**
     * @return the spiClassName
     */
    public String getSpiClassName() {
        return spiClassName;
    }

    /**
     * @return the spiFactoryClassName
     */
    public String getSpiFactoryClassName() {
        return spiFactoryClassName;
    }

    @Override
    public int hashCode() {
        return getDefinitionID().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        ViewProviderDef other = (ViewProviderDef) obj;
        return getDefinitionID().equals(other.getDefinitionID());
    }

    @Override
    public String toString() {
        StringBuilder builder2 = new StringBuilder();
        builder2.append("ViewProviderDef [kind=");
        builder2.append(kind);
        builder2.append(", code=");
        builder2.append(code);
        builder2.append(", implClassName=");
        builder2.append(implClassName);
        builder2.append(", spiClassName=");
        builder2.append(spiClassName);
        builder2.append("]");
        return builder2.toString();
    }

    /**
     * Builder for ViewProviderDef
     * 
     * @author kitko
     * 
     */
    public static final class Builder implements org.vpda.common.util.Builder<ViewProviderDef> {
        private String implClassName;
        private String factoryClassName;
        private String code;
        private ViewProviderKind kind;
        private String spiClassName;
        private String spiFactoryClassName;

        /**
         * @return the implementation class
         */
        public String getImplClassName() {
            return implClassName;
        }

        /**
         * Sets values from {@link ViewProviderDef}
         * 
         * @param def
         * @return this
         */
        public Builder setValues(ViewProviderDef def) {
            this.code = def.code;
            this.implClassName = def.implClassName;
            this.factoryClassName = def.factoryClassName;
            this.spiClassName = def.spiClassName;
            this.spiFactoryClassName = def.spiFactoryClassName;
            this.kind = def.kind;
            return this;
        }

        /**
         * Sets provider implementation class
         * 
         * @param implClazz implementation class to set
         * @return this
         */
        public Builder setImplClassName(String implClazz) {
            this.implClassName = implClazz;
            return this;
        }

        /**
         * @return the code
         */
        public String getCode() {
            return code;
        }

        /**
         * @param code the code to set
         * @return this
         */
        public Builder setCode(String code) {
            this.code = code;
            return this;
        }

        /**
         * @return the viewProviderKind
         */
        public ViewProviderKind getKind() {
            return kind;
        }

        /**
         * @param viewProviderKind the viewProviderKind to set
         * @return this
         */
        public Builder setKind(ViewProviderKind viewProviderKind) {
            this.kind = viewProviderKind;
            return this;
        }

        /**
         * @return the spiClassName
         */
        public String getSpiClassName() {
            return spiClassName;
        }

        /**
         * @param spiClassName the spiClassName to set
         * @return this
         */
        public Builder setSpiClassName(String spiClassName) {
            this.spiClassName = spiClassName;
            return this;
        }

        /**
         * @return the factoryClassName
         */
        public String getFactoryClassName() {
            return factoryClassName;
        }

        /**
         * @param factoryClassName the factoryClassName to set
         * @return this
         */
        public Builder setFactoryClassName(String factoryClassName) {
            this.factoryClassName = factoryClassName;
            return this;
        }

        /**
         * @return the spiFactoryClassName
         */
        public String getSpiFactoryClassName() {
            return spiFactoryClassName;
        }

        /**
         * @param spiFactoryClassName the spiFactoryClassName to set
         * @return this
         */
        public Builder setSpiFactoryClassName(String spiFactoryClassName) {
            this.spiFactoryClassName = spiFactoryClassName;
            return this;
        }

        @Override
        public ViewProviderDef build() {
            return new ViewProviderDef(this);
        }

        @Override
        public Class<? extends ViewProviderDef> getTargetClass() {
            return ViewProviderDef.class;
        }

    }
}
