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
package org.vpda.common.util;

import java.io.Serializable;
import java.time.ZonedDateTime;

/**
 * One commit info
 * 
 * @author kitko
 *
 */
public final class ScmCommitInfo implements Serializable {
    private static final long serialVersionUID = -1112019252048640500L;
    private final String id;
    private final String author;
    private final ZonedDateTime timestamp;
    private final String msg;

    private ScmCommitInfo(ScmCommitInfoBuilder builder) {
        this.id = builder.getId();
        this.author = builder.getAuthor();
        this.timestamp = builder.getTimestamp();
        this.msg = builder.getMsg();
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @return the author
     */
    public String getAuthor() {
        return author;
    }

    /**
     * @return the timestamp
     */
    public ZonedDateTime getTimestamp() {
        return timestamp;
    }

    /**
     * @return the msg
     */
    public String getMsg() {
        return msg;
    }

    @Override
    public String toString() {
        return "ScmCommitInfo [id=" + id + ", author=" + author + ", timestamp=" + timestamp + ", msg=" + msg + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
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
        ScmCommitInfo other = (ScmCommitInfo) obj;
        if (id == null) {
            if (other.id != null) {
                return false;
            }
        }
        else if (!id.equals(other.id)) {
            return false;
        }
        return true;
    }

    /**
     * Commit info
     * 
     * @author kitko
     *
     */
    public static final class ScmCommitInfoBuilder implements org.vpda.common.util.Builder<ScmCommitInfo> {
        private String id;
        private String author;
        private ZonedDateTime timestamp;
        private String msg;

        /**
         * @return the id
         */
        public String getId() {
            return id;
        }

        /**
         * @param id the id to set
         * @return this
         */
        public ScmCommitInfoBuilder setId(String id) {
            this.id = id;
            return this;
        }

        /**
         * @return the author
         */
        public String getAuthor() {
            return author;
        }

        /**
         * @param author the author to set
         * @return this
         */
        public ScmCommitInfoBuilder setAuthor(String author) {
            this.author = author;
            return this;
        }

        /**
         * @return the timestamp
         */
        public ZonedDateTime getTimestamp() {
            return timestamp;
        }

        /**
         * @param timestamp the timestamp to set
         * @return this
         */
        public ScmCommitInfoBuilder setTimestamp(ZonedDateTime timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        /**
         * @return the msg
         */
        public String getMsg() {
            return msg;
        }

        /**
         * @param msg the msg to set
         * @return this
         */
        public ScmCommitInfoBuilder setMsg(String msg) {
            this.msg = msg;
            return this;
        }

        @Override
        public Class<? extends ScmCommitInfo> getTargetClass() {
            return ScmCommitInfo.class;
        }

        @Override
        public ScmCommitInfo build() {
            return new ScmCommitInfo(this);
        }

    }

}
