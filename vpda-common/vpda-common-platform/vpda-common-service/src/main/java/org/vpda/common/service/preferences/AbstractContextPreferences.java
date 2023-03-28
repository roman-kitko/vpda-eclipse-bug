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
package org.vpda.common.service.preferences;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.UUID;

import org.vpda.common.context.ApplContext;
import org.vpda.common.context.User;

/**
 * Abstract preferences
 * 
 * @author kitko
 *
 */
public abstract class AbstractContextPreferences implements Serializable {
    private static final long serialVersionUID = -8794695027252453127L;
    private final Long id;
    private final UUID uuid;
    private final User creator;
    private final ApplContext context;
    private final String name;
    private final String description;
    private final Timestamp creationTime;
    private final boolean isReadOnly;
    private final boolean isTemporal;

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @return the uuid
     */
    public UUID getUuid() {
        return uuid;
    }

    /**
     * @return the creator
     */
    public User getCreator() {
        return creator;
    }

    /**
     * @return the context
     */
    public ApplContext getContext() {
        return context;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @return creationTime
     */
    public Timestamp getCreationTime() {
        return creationTime;
    }

    /**
     * @return the isReadOnly
     */
    public boolean isReadOnly() {
        return isReadOnly;
    }

    /**
     * @return true is these preferences are temporal only
     */
    public boolean isTemporal() {
        return isTemporal;
    }

    @Override
    public String toString() {
        return "AbstractContextPreferences [id=" + id + ", uuid=" + uuid + ", creator=" + creator + ", context=" + context + ", name=" + name + ", description=" + description + ", creationTime="
                + creationTime + ", isReadOnly=" + isReadOnly + ", isTemporal=" + isTemporal + "]";
    }

    /**
     * Creates AbstractContextPreferences
     * 
     * @param builder
     */
    protected AbstractContextPreferences(Builder builder) {
        this.id = builder.getId();
        this.context = builder.getContext();
        this.creator = builder.getCreator();
        this.description = builder.getDescription();
        this.name = builder.getName();
        this.uuid = builder.getUuid();
        this.creationTime = builder.getCreationTime();
        this.isReadOnly = builder.isReadOnly();
        this.isTemporal = builder.isTemporal();
    }

    /**
     * @author kitko
     *
     * @param <T>
     */
    public static abstract class Builder<T extends AbstractContextPreferences> implements org.vpda.common.util.Builder<T> {
        private Long id;
        private UUID uuid;
        private User creator;
        private ApplContext context;
        private String name;
        private String description;
        private Timestamp creationTime;
        private boolean isReadOnly;
        private boolean isTemporal;

        /**
         * @param pref
         * @return this
         */
        public Builder<T> setValues(T pref) {
            this.setId(pref.getId());
            this.setContext(pref.getContext());
            this.setCreator(pref.getCreator());
            this.setDescription(pref.getDescription());
            this.setName(pref.getName());
            this.setUuid(pref.getUuid());
            this.setCreationTime(pref.getCreationTime());
            this.isReadOnly = pref.isReadOnly();
            this.isTemporal = pref.isTemporal();
            return this;
        }

        /**
         * @return the isReadOnly
         */
        public boolean isReadOnly() {
            return isReadOnly;
        }

        /**
         * @param isReadOnly the isReadOnly to set
         * @return this;
         */
        public Builder<T> setReadOnly(boolean isReadOnly) {
            this.isReadOnly = isReadOnly;
            return this;
        }

        /**
         * @return the isTemporal
         */
        public boolean isTemporal() {
            return isTemporal;
        }

        /**
         * @param isTemporal the isTemporal to set
         * @return this;
         */
        public Builder<T> setTemporal(boolean isTemporal) {
            this.isTemporal = isTemporal;
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
        public Builder<T> setCreationTime(Timestamp creationTime) {
            this.creationTime = creationTime;
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
        public Builder<T> setId(Long id) {
            this.id = id;
            return this;
        }

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
        public Builder<T> setUuid(UUID uuid) {
            this.uuid = uuid;
            return this;
        }

        /**
         * @return the creator
         */
        public User getCreator() {
            return creator;
        }

        /**
         * @param creator the creator to set
         * @return this
         */
        public Builder<T> setCreator(User creator) {
            this.creator = creator;
            return this;
        }

        /**
         * @return the context
         */
        public ApplContext getContext() {
            return context;
        }

        /**
         * @param context the context to set
         * @return this
         */
        public Builder<T> setContext(ApplContext context) {
            this.context = context;
            return this;
        }

        /**
         * @return the name
         */
        public String getName() {
            return name;
        }

        /**
         * @param name the name to set
         * @return this
         */
        public Builder<T> setName(String name) {
            this.name = name;
            return this;
        }

        /**
         * @return the description
         */
        public String getDescription() {
            return description;
        }

        /**
         * @param description the description to set
         * @return this
         */
        public Builder<T> setDescription(String description) {
            this.description = description;
            return this;
        }

    }

}
