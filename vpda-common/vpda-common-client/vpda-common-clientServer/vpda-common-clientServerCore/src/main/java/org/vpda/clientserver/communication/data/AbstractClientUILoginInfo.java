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
package org.vpda.clientserver.communication.data;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Locale;
import java.util.UUID;

/**
 * Abstract {@link ClientUILoginInfo}
 * 
 * @author kitko
 *
 */
public abstract class AbstractClientUILoginInfo implements ClientUILoginInfo, Serializable {
    private static final long serialVersionUID = -4586252788351259083L;
    /** UUID of info */
    protected final UUID uuid;
    /** Login name of user */
    protected final String login;
    /** Choosen locale */
    protected final Locale locale;
    /** UI theme */
    protected final String theme;
    /** How context can be choosen */
    protected final ContextPolicy contextPolicy;
    /** When info was created */
    protected final Timestamp creationTime;

    /**
     * Creates AbstractClientUILoginInfo
     * 
     * @param builder
     */
    protected AbstractClientUILoginInfo(AbstractClientUILoginInfoBuilder builder) {
        this.uuid = builder.getUuid();
        this.login = builder.getLogin();
        this.locale = builder.getLocale();
        this.theme = builder.getTheme();
        this.contextPolicy = builder.getContextPolicy();
        this.creationTime = builder.getCreationTime();
    }

    @Override
    public UUID getUuid() {
        return uuid;
    }

    @Override
    public String getLogin() {
        return login;
    }

    @Override
    public Locale getLocale() {
        return locale;
    }

    @Override
    public ContextPolicy getContextPolicy() {
        return contextPolicy;
    }

    @Override
    public Timestamp getCreationTime() {
        return creationTime;
    }

    @Override
    public String getTheme() {
        return theme;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((contextPolicy == null) ? 0 : contextPolicy.hashCode());
        result = prime * result + ((creationTime == null) ? 0 : creationTime.hashCode());
        result = prime * result + ((locale == null) ? 0 : locale.hashCode());
        result = prime * result + ((login == null) ? 0 : login.hashCode());
        result = prime * result + ((theme == null) ? 0 : theme.hashCode());
        result = prime * result + ((uuid == null) ? 0 : uuid.hashCode());
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
        AbstractClientUILoginInfo other = (AbstractClientUILoginInfo) obj;
        if (contextPolicy != other.contextPolicy)
            return false;
        if (creationTime == null) {
            if (other.creationTime != null)
                return false;
        }
        else if (!creationTime.equals(other.creationTime))
            return false;
        if (locale == null) {
            if (other.locale != null)
                return false;
        }
        else if (!locale.equals(other.locale))
            return false;
        if (login == null) {
            if (other.login != null)
                return false;
        }
        else if (!login.equals(other.login))
            return false;
        if (theme == null) {
            if (other.theme != null)
                return false;
        }
        else if (!theme.equals(other.theme))
            return false;
        if (uuid == null) {
            if (other.uuid != null)
                return false;
        }
        else if (!uuid.equals(other.uuid))
            return false;
        return true;
    }

    /**
     * @return builder
     */
    @SuppressWarnings("unchecked")
    public AbstractClientUILoginInfoBuilder createBuilderWithSameValues() {
        AbstractClientUILoginInfoBuilder builder = createBuilder();
        builder.setValues(this);
        return builder;
    }

    /**
     * @return builder
     */
    public abstract AbstractClientUILoginInfoBuilder createBuilder();

    /**
     * Builder for {@link AbstractClientUILoginInfo}
     * 
     * @author kitko
     *
     * @param <T>
     */
    @SuppressWarnings("javadoc")
    public static abstract class AbstractClientUILoginInfoBuilder<T extends AbstractClientUILoginInfo> implements org.vpda.common.util.Builder<T> {
        protected UUID uuid;
        protected String login;
        protected Locale locale;
        protected String theme;
        protected ContextPolicy contextPolicy;
        protected Timestamp creationTime;

        /**
         * @return the uuid
         */
        public UUID getUuid() {
            return uuid;
        }

        /**
         * @param uuid the uuid to set
         * @return this
         */
        public AbstractClientUILoginInfoBuilder<T> setUuid(UUID uuid) {
            this.uuid = uuid;
            return this;
        }

        /**
         * @return the login
         */
        public String getLogin() {
            return login;
        }

        /**
         * @param login the login to set
         * @return this
         */
        public AbstractClientUILoginInfoBuilder<T> setLogin(String login) {
            this.login = login;
            return this;
        }

        /**
         * @return the locale
         */
        public Locale getLocale() {
            return locale;
        }

        /**
         * @param locale the locale to set
         * @return this
         */
        public AbstractClientUILoginInfoBuilder<T> setLocale(Locale locale) {
            this.locale = locale;
            return this;
        }

        /**
         * @return the theme
         */
        public String getTheme() {
            return theme;
        }

        /**
         * @param theme the theme to set
         * @return this
         */
        public AbstractClientUILoginInfoBuilder<T> setTheme(String theme) {
            this.theme = theme;
            return this;
        }

        /**
         * @return the contextPolicy
         */
        public ContextPolicy getContextPolicy() {
            return contextPolicy;
        }

        /**
         * @param contextPolicy the contextPolicy to set
         * @return this
         */
        public AbstractClientUILoginInfoBuilder<T> setContextPolicy(ContextPolicy contextPolicy) {
            this.contextPolicy = contextPolicy;
            return this;
        }

        /**
         * @return the creationTime
         */
        public Timestamp getCreationTime() {
            return creationTime;
        }

        /**
         * @param creationTime the creationTime to set
         * @return this
         */
        public AbstractClientUILoginInfoBuilder<T> setCreationTime(Timestamp creationTime) {
            this.creationTime = creationTime;
            return this;
        }

        /**
         * @param uiInfo
         * @return this
         */
        public AbstractClientUILoginInfoBuilder<T> setValues(T uiInfo) {
            this.uuid = uiInfo.getUuid();
            this.theme = uiInfo.getTheme();
            this.login = uiInfo.getLogin();
            this.locale = uiInfo.getLocale();
            this.creationTime = uiInfo.getCreationTime();
            this.contextPolicy = uiInfo.getContextPolicy();
            return this;
        }

    }

}
