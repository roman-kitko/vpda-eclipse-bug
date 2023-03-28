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
import java.util.Locale;

import org.vpda.common.context.ApplContext;
import org.vpda.common.context.DateContext;
import org.vpda.common.context.TenementalContext;
import org.vpda.common.context.User;
import org.vpda.common.context.UserAware;
import org.vpda.internal.common.util.Assert;

/**
 * User session representation
 * 
 * @author kitko
 */
public final class UserSession implements Serializable, UserAware {
    private static final long serialVersionUID = -2593330833404772206L;
    private final Long id;
    private final String sessionId;
    private final User user;
    private final LoginInfo loginInfo;
    private final long loginTime;
    private boolean isTransient;

    private UserSession(Builder builder) {
        this.id = builder.getId();
        this.sessionId = Assert.isNotEmptyArgument(builder.getSessionId(), "sessionId");
        this.user = Assert.isNotNull(builder.getUser(), "user");
        this.loginInfo = Assert.isNotNullArgument(builder.getLoginInfo(), "loginInfo");
        this.loginTime = builder.getLoginTime();
        this.isTransient = builder.isTransient();
    }

    /**
     * @return true if this is transient session
     */
    public boolean isTransient() {
        return isTransient;
    }

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @return id of session
     */
    public String getSessionId() {
        return sessionId;
    }

    /**
     * @return login information
     */
    public LoginInfo getLoginInfo() {
        return loginInfo;
    }

    /**
     * @return Login time
     */
    public long getLoginTime() {
        return loginTime;
    }

    /**
     * @return user
     */
    @Override
    public User getUser() {
        return user;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof UserSession)) {
            return false;
        }
        UserSession userSession = (UserSession) o;
        return sessionId.equals(userSession.getSessionId());

    }

    @Override
    public int hashCode() {
        return sessionId.hashCode();
    }

    @Override
    public String toString() {
        return user + "_" + sessionId;
    }

    /**
     * @return setting context
     */
    public ApplContext getApplContext() {
        return loginInfo.getApplContext();
    }

    public TenementalContext getContext() {
        return loginInfo.getContext();
    }

    public Locale getLocale() {
        return loginInfo.getLocale();
    }

    public DateContext getDateContext() {
        return loginInfo.getDateContext();
    }

    /** Session builder */
    public final static class Builder implements org.vpda.common.util.Builder<UserSession> {
        private Long id;
        private String sessionId;
        private User user;
        private LoginInfo loginInfo;
        private long loginTime;
        private boolean isTransient;

        /**
         * Creates Builder
         */
        public Builder() {
            this.isTransient = false;
        }

        /**
         * @return the isTransient
         */
        public boolean isTransient() {
            return isTransient;
        }

        /**
         * @param isTransient the isTransient to set
         * @return this
         */
        public Builder setTransient(boolean isTransient) {
            this.isTransient = isTransient;
            return this;
        }

        /**
         * @return the id
         */
        public Long getId() {
            return id;
        }

        /**
         * @param id the id to set
         * @return this
         */
        public Builder setId(Long id) {
            this.id = id;
            return this;
        }

        /**
         * @return the id
         */
        public String getSessionId() {
            return sessionId;
        }

        /**
         * @param sessionId the id to set
         * @return this
         */
        public Builder setSessionId(String sessionId) {
            this.sessionId = sessionId;
            return this;
        }

        /**
         * @return the user
         */
        public User getUser() {
            return user;
        }

        /**
         * @param user the user to set
         * @return this
         */
        public Builder setUser(User user) {
            this.user = user;
            return this;
        }

        /**
         * @return the loginInfo
         */
        public LoginInfo getLoginInfo() {
            return loginInfo;
        }

        /**
         * @param loginInfo the loginInfo to set
         * @return this
         */
        public Builder setLoginInfo(LoginInfo loginInfo) {
            this.loginInfo = loginInfo;
            return this;
        }

        /**
         * @return the loginTime
         */
        public long getLoginTime() {
            return loginTime;
        }

        /**
         * @param loginTime the loginTime to set
         * @return this
         */
        public Builder setLoginTime(long loginTime) {
            this.loginTime = loginTime;
            return this;
        }

        /**
         * Sets values from session
         * 
         * @param session
         * @return this
         */
        public Builder setValues(UserSession session) {
            this.sessionId = session.getSessionId();
            this.loginInfo = session.getLoginInfo();
            this.user = session.getUser();
            this.id = session.getId();
            this.loginTime = session.getLoginTime();
            this.isTransient = session.isTransient();
            return this;
        }

        /**
         * Builds user session
         * 
         * @return new UserSession
         */
        @Override
        public UserSession build() {
            return new UserSession(this);
        }

        @Override
        public Class<? extends UserSession> getTargetClass() {
            return UserSession.class;
        }

    }

}
