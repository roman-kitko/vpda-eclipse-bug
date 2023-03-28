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
package org.vpda.common.context;

import java.io.Serializable;
import java.util.UUID;

import org.vpda.common.util.Builder;

/**
 * Tenant basic data.
 * 
 * @author kitko
 *
 */
public final class Tenant implements Serializable, Actor {
    private static final long serialVersionUID = 396801676003028213L;
    private final Long id;
    private final UUID externalId;
    private final String code;
    private final String name;

    /**
     * @return Login of this user
     */
    public String getCode() {
        return code;
    }

    /**
     * @return Returns the id.
     */
    @Override
    public Long getId() {
        return id;
    }

    /**
     * @return Returns the name.
     */
    public String getName() {
        return name;
    }

    @Override
    public String getUniqueName() {
        return code;
    }

    @Override
    public UUID getExternalId() {
        return externalId;
    }

    private Tenant(TenantBuilder builder) {
        this.code = builder.getCode();
        this.id = builder.getId();
        this.name = builder.getName();
        this.externalId = builder.getExternalId();
    }

    @Override
    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (object instanceof Tenant) {
            Tenant group = (Tenant) object;
            if (group.id != null && id != null) {
                return group.id.equals(id);
            }
            if (group.code != null && code != null) {
                return group.code.equals(code);
            }
            throw new IllegalStateException("Not sufficient information for equals of Group");
        }
        return false;

    }

    @Override
    public int hashCode() {
        if (id != null) {
            return id.hashCode();
        }
        if (code != null) {
            return code.hashCode();
        }
        throw new IllegalStateException("Not sufficient information for hashCode");
    }

    @Override
    public String toString() {
        return code;
    }

    /** Builder for tenant */
    public final static class TenantBuilder implements Builder<Tenant> {
        private Long id;
        private UUID externalId;
        private String name;
        private String code;

        public UUID getExternalId() {
            return externalId;
        }

        public TenantBuilder setExternalId(UUID externalId) {
            this.externalId = externalId;
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
        public TenantBuilder setId(Long id) {
            this.id = id;
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
        public TenantBuilder setName(String name) {
            this.name = name;
            return this;
        }

        /**
         * Sets values from user
         * 
         * @param group
         * @return this
         */
        public TenantBuilder setValues(Tenant group) {
            this.id = group.getId();
            this.code = group.getCode();
            this.name = group.getName();
            this.externalId = group.getExternalId();
            return this;
        }

        /**
         * Builds new user
         * 
         * @return new user
         */
        @Override
        public Tenant build() {
            return new Tenant(this);
        }

        @Override
        public Class<? extends Tenant> getTargetClass() {
            return Tenant.class;
        }

        public String getCode() {
            return code;
        }

        public TenantBuilder setCode(String code) {
            this.code = code;
            return this;
        }
    }
}
