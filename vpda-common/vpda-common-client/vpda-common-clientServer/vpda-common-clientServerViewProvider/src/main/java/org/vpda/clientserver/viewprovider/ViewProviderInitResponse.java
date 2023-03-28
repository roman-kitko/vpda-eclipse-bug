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
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.vpda.clientserver.viewprovider.preferences.AbstractViewProviderPreferences;
import org.vpda.common.command.Command;
import org.vpda.internal.common.util.Assert;

/**
 * 
 * Initial response from view provider init method
 * 
 * @author kitko
 *
 */
public abstract class ViewProviderInitResponse implements Serializable {

    private static final long serialVersionUID = 7004584700358810437L;

    /** Initial settings for client */
    protected final ViewProviderSettings viewProviderSettings;
    /** Initial provider info */
    protected final ViewProviderInfo viewProviderInfo;
    /** Initial properties */
    protected final Map<String, Object> properties;
    /** Preferences with name, can be null */
    protected final AbstractViewProviderPreferences initPreferences;
    /** Initial data command */
    protected final Command<?> initCommand;

    /**
     * @return init settings
     */
    public ViewProviderSettings getInitSettings() {
        return viewProviderSettings;
    }

    /**
     * @return provider info
     */
    public ViewProviderInfo getViewProviderInfo() {
        return viewProviderInfo;
    }

    /**
     * @return initial command
     */
    public Command<?> getInitCommand() {
        return initCommand;
    }

    /**
     * Creates ViewProviderInitResponse with settings but null preferences
     * 
     * @param viewProviderInfo
     * @param viewProviderSettings
     * @param properties
     */
    protected ViewProviderInitResponse(ViewProviderInfo viewProviderInfo, ViewProviderSettings viewProviderSettings, Map<String, Object> properties) {
        super();
        this.viewProviderInfo = Assert.isNotNullArgument(viewProviderInfo, "viewProviderInfo");
        this.viewProviderSettings = Assert.isNotNullArgument(viewProviderSettings, "viewProviderSettings");
        this.properties = properties == null ? null : new HashMap<String, Object>(properties);
        this.initPreferences = null;
        this.initCommand = null;
    }

    /**
     * Creates ViewProviderInitResponse with settings but null preferences
     * 
     * @param viewProviderInfo
     * @param viewProviderSettings
     * @param properties
     * @param initCommand
     */
    protected ViewProviderInitResponse(ViewProviderInfo viewProviderInfo, ViewProviderSettings viewProviderSettings, Map<String, Object> properties, Command<?> initCommand) {
        super();
        this.viewProviderInfo = Assert.isNotNullArgument(viewProviderInfo, "viewProviderInfo");
        this.viewProviderSettings = Assert.isNotNullArgument(viewProviderSettings, "viewProviderSettings");
        this.properties = properties == null ? null : new HashMap<String, Object>(properties);
        this.initPreferences = null;
        this.initCommand = initCommand;
    }

    /**
     * Creates ViewProviderInitResponse with settings but null preferences
     * 
     * @param viewProviderInfo
     * @param initPreferences
     * @param properties
     */
    protected ViewProviderInitResponse(ViewProviderInfo viewProviderInfo, AbstractViewProviderPreferences initPreferences, Map<String, Object> properties) {
        super();
        this.viewProviderInfo = Assert.isNotNullArgument(viewProviderInfo, "viewProviderInfo");
        this.initPreferences = Assert.isNotNullArgument(initPreferences, "preferences");
        this.viewProviderSettings = Assert.isNotNullArgument(initPreferences.getViewProviderSettings(), "viewProviderSettings");
        this.properties = properties == null ? null : new HashMap<String, Object>(properties);
        this.initCommand = null;
    }

    /**
     * Creates ViewProviderInitResponse with settings but null preferences
     * 
     * @param viewProviderInfo
     * @param initPreferences
     * @param properties
     * @param initCommand
     */
    protected ViewProviderInitResponse(ViewProviderInfo viewProviderInfo, AbstractViewProviderPreferences initPreferences, Map<String, Object> properties, Command<?> initCommand) {
        super();
        this.viewProviderInfo = Assert.isNotNullArgument(viewProviderInfo, "viewProviderInfo");
        this.initPreferences = Assert.isNotNullArgument(initPreferences, "preferences");
        this.viewProviderSettings = Assert.isNotNullArgument(initPreferences.getViewProviderSettings(), "viewProviderSettings");
        this.properties = properties == null ? null : new HashMap<String, Object>(properties);
        this.initCommand = initCommand;
    }

    /**
     * Creates response from builder
     * 
     * @param builder
     */
    protected <T extends ViewProviderInitResponse> ViewProviderInitResponse(ViewProviderInitResponseBuilder<T> builder) {
        this.viewProviderInfo = Assert.isNotNullArgument(builder.getViewProviderInfo(), "viewProviderInfo");
        this.properties = builder.properties == null ? null : new HashMap<String, Object>(builder.properties);
        this.initCommand = builder.getInitCommand();
        if (builder.getInitPreferences() != null) {
            this.initPreferences = Assert.isNotNullArgument(builder.getInitPreferences(), "preferences");
            this.viewProviderSettings = Assert.isNotNullArgument(initPreferences.getViewProviderSettings(), "viewProviderSettings");
        }
        else {
            this.initPreferences = null;
            this.viewProviderSettings = Assert.isNotNullArgument(builder.getViewProviderSettings(), "viewProviderSettings");
        }

    }

    /**
     * @return new builder
     */
    public abstract <T extends ViewProviderInitResponse> ViewProviderInitResponseBuilder<T> createBuilder();

    /**
     * @return builder with same values from this set
     */
    @SuppressWarnings("unchecked")
    public <T extends ViewProviderInitResponse> ViewProviderInitResponseBuilder<T> createBuilderWithSameValues() {
        ViewProviderInitResponseBuilder<T> builder = createBuilder();
        builder.setValues((T) this);
        return builder;
    }

    /**
     * @param key
     * @return custom property
     */
    public Object getProperty(String key) {
        return properties != null ? properties.get(key) : null;
    }

    /**
     * @return preferences
     */
    public AbstractViewProviderPreferences getInitPreferences() {
        return initPreferences;
    }

    /**
     * @return properties
     */
    public Map<String, Object> getProperties() {
        return properties == null ? Collections.emptyMap() : Collections.unmodifiableMap(properties);
    }

    @Override
    public String toString() {
        return "ViewProviderInitResponse [viewProviderSettings=" + viewProviderSettings + ", viewProviderInfo=" + viewProviderInfo + ", properties=" + properties + ", initPreferences="
                + initPreferences + "]";
    }

    /**
     * Builder for {@link ViewProviderInitResponse} response
     * 
     * @param <T> type of response
     */
    public static abstract class ViewProviderInitResponseBuilder<T extends ViewProviderInitResponse> implements org.vpda.common.util.Builder<T> {
        /** Initial settings for client */
        protected ViewProviderSettings viewProviderSettings;
        /** Initial provider info */
        protected ViewProviderInfo viewProviderInfo;
        /** Initial properties */
        protected Map<String, Object> properties;
        /** Preferences with name, can be null */
        protected AbstractViewProviderPreferences initPreferences;
        /** Initial data command */
        protected Command<?> initCommand;

        /**
         * @return the viewProviderSettings
         */
        public ViewProviderSettings getViewProviderSettings() {
            return viewProviderSettings;
        }

        /**
         * @param viewProviderSettings the viewProviderSettings to set
         */
        public void setViewProviderSettings(ViewProviderSettings viewProviderSettings) {
            this.viewProviderSettings = viewProviderSettings;
        }

        /**
         * @return the viewProviderInfo
         */
        public ViewProviderInfo getViewProviderInfo() {
            return viewProviderInfo;
        }

        /**
         * @param viewProviderInfo the viewProviderInfo to set
         * @return this
         */
        public ViewProviderInitResponseBuilder<T> setViewProviderInfo(ViewProviderInfo viewProviderInfo) {
            this.viewProviderInfo = viewProviderInfo;
            return this;
        }

        /**
         * @return the properties
         */
        public Map<String, Object> getProperties() {
            return properties;
        }

        /**
         * @param properties the properties to set
         * @return this
         */
        public ViewProviderInitResponseBuilder<T> setProperties(Map<String, Object> properties) {
            this.properties = properties;
            return this;
        }

        /**
         * @return the initPreferences
         */
        public AbstractViewProviderPreferences getInitPreferences() {
            return initPreferences;
        }

        /**
         * @param initPreferences the initPreferences to set
         * @return this
         */
        public ViewProviderInitResponseBuilder<T> setInitPreferences(AbstractViewProviderPreferences initPreferences) {
            this.initPreferences = initPreferences;
            return this;
        }

        /**
         * @return the initCommand
         */
        public Command<?> getInitCommand() {
            return initCommand;
        }

        /**
         * @param initCommand the initCommand to set
         * @return this
         */
        public ViewProviderInitResponseBuilder<T> setInitCommand(Command<?> initCommand) {
            this.initCommand = initCommand;
            return this;
        }

        /**
         * Sets values from reponse
         * 
         * @param response
         * @return this
         */
        public ViewProviderInitResponseBuilder<T> setValues(T response) {
            this.initCommand = response.getInitCommand();
            this.initPreferences = response.getInitPreferences();
            this.properties = response.getProperties();
            this.viewProviderInfo = response.getViewProviderInfo();
            this.viewProviderSettings = response.getInitSettings();
            return this;
        }

    }

}
