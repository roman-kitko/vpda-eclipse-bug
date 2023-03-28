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
package org.vpda.clientserver.communication.pref;

import java.io.Serializable;
import java.util.Locale;

import org.vpda.clientserver.communication.data.AbstractClientUILoginInfo;
import org.vpda.clientserver.communication.data.ClientUILoginInfo;
import org.vpda.clientserver.communication.data.ContextPolicy;
import org.vpda.common.context.ApplContext;
import org.vpda.common.service.preferences.AbstractContextPreferences;

public abstract class AbstractLoginPreferences extends AbstractContextPreferences implements Serializable {
    private static final long serialVersionUID = -827955786747894536L;

    private final String login;

    private final String theme;

    private final Locale locale;

    private final ContextPolicy contextPolicy;

    private final Long loginTime;

    private final ApplContext loginContext;

    private final Locale contextLocale;

    protected <T extends AbstractLoginPreferences> AbstractLoginPreferences(Builder<T> builder) {
        super(builder);
        this.login = builder.getLogin();
        this.theme = builder.getTheme();
        this.locale = builder.getContextLocale();
        this.contextPolicy = builder.getContextPolicy();
        this.loginTime = builder.getLoginTime();
        this.loginContext = builder.getLoginContext();
        this.contextLocale = builder.getContextLocale();
    }

    public String getLogin() {
        return login;
    }

    public String getTheme() {
        return theme;
    }

    public Locale getLocale() {
        return locale;
    }

    public ContextPolicy getContextPolicy() {
        return contextPolicy;
    }

    public Long getLoginTime() {
        return loginTime;
    }

    public ApplContext getLoginContext() {
        return loginContext;
    }

    public Locale getContextLocale() {
        return contextLocale;
    }

    public abstract ClientUILoginInfo createClientUILoginInfo();

    protected <C extends AbstractClientUILoginInfo> void fillClientUILoginInfoBuilder(AbstractClientUILoginInfo.AbstractClientUILoginInfoBuilder<C> builder) {
        builder.setContextPolicy(contextPolicy).setLocale(locale).setLogin(login).setTheme(theme).setUuid(getUuid()).setCreationTime(getCreationTime());
    }

    public static abstract class Builder<T extends AbstractLoginPreferences> extends AbstractContextPreferences.Builder<T> implements org.vpda.common.util.Builder<T> {

        private String login;

        private String theme;

        private Locale locale;

        private ContextPolicy contextPolicy;

        private Long loginTime;

        private ApplContext loginContext;

        private Locale contextLocale;

        @Override
        public Builder<T> setValues(T pref) {
            super.setValues(pref);
            this.login = pref.getLogin();
            this.theme = pref.getTheme();
            this.locale = pref.getLocale();
            this.contextPolicy = pref.getContextPolicy();
            this.loginTime = pref.getLoginTime();
            this.loginContext = pref.getLoginContext();
            this.contextLocale = pref.getContextLocale();
            return this;
        }

        public String getLogin() {
            return login;
        }

        public Builder<T> setLogin(String login) {
            this.login = login;
            return this;
        }

        public String getTheme() {
            return theme;
        }

        public Builder<T> setTheme(String theme) {
            this.theme = theme;
            return this;
        }

        public Locale getLocale() {
            return locale;
        }

        public Builder<T> setLocale(Locale locale) {
            this.locale = locale;
            return this;
        }

        public ContextPolicy getContextPolicy() {
            return contextPolicy;
        }

        public Builder<T> setContextPolicy(ContextPolicy contextPolicy) {
            this.contextPolicy = contextPolicy;
            return this;
        }

        public Long getLoginTime() {
            return loginTime;
        }

        public Builder<T> setLoginTime(Long loginTime) {
            this.loginTime = loginTime;
            return this;
        }

        public ApplContext getLoginContext() {
            return loginContext;
        }

        public void setLoginContext(ApplContext loginContext) {
            this.loginContext = loginContext;
        }

        public Locale getContextLocale() {
            return contextLocale;
        }

        public Builder<T> setContextLocale(Locale contextLocale) {
            this.contextLocale = contextLocale;
            return this;
        }

    }

}
