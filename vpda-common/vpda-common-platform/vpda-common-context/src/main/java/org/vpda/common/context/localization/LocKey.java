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
package org.vpda.common.context.localization;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.vpda.common.annotations.Immutable;

/**
 * This is identificator for localization key. It contains path and key. Key
 * part is only last element of path.
 * 
 * @author kitko
 *
 */
@Immutable
public final class LocKey implements Serializable {
    private static final long serialVersionUID = -8246099049314610593L;
    private final String path;
    private final String key;

    /**
     * @return key part of key
     */
    public String getKey() {
        return key;
    }

    /**
     * @return path part of key
     */
    public String getPath() {
        return path;
    }

    /**
     * Creates loc key
     * 
     * @param path
     * @param key
     */
    public LocKey(String path, String key) {
        if (path == null) {
            throw new IllegalArgumentException("Path argument is null");
        }
        if (key != null && !key.isEmpty()) {
            key = key.replace('.', '/');
            this.key = key;
            this.path = path + '/' + key;
        }
        else {
            this.path = path;
            this.key = null;
        }
    }

    /**
     * Creates LocKey with path
     * 
     * @param path
     * @return LockKey with path
     */
    public static LocKey path(String path) {
        return new LocKey(path);
    }

    /**
     * Creates LocKey with path and key
     * 
     * @param path
     * @param key
     * @return LocKey
     */
    public static LocKey pathAndKey(String path, String key) {
        return new LocKey(path, key);
    }

    /**
     * Creates loc key with only path part
     * 
     * @param path
     */
    public LocKey(String path) {
        if (path == null) {
            throw new IllegalArgumentException("Path argument is null");
        }
        int index = path.lastIndexOf('/');
        this.path = path;
        this.key = index != -1 ? path.substring(index + 1) : null;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof LocKey)) {
            return false;
        }
        LocKey locKey = (LocKey) obj;
        return locKey.path.equals(path);
    }

    @Override
    public int hashCode() {
        return path.hashCode();
    }

    @Override
    public String toString() {
        return path;
    }

    /**
     * @param childKey
     * @return child key of current path
     */
    public LocKey createChildKey(String childKey) {
        return new LocKey(path + '/' + childKey);
    }

    /**
     * @return parent key
     */
    public LocKey createParrentKey() {
        int index = path.lastIndexOf('/');
        if (index == -1) {
            throw new IllegalStateException("No parrent for localization key");
        }
        if (index == path.length() - 1) {
            index = path.substring(0, path.length() - 1).lastIndexOf('/');
            if (index == -1) {
                throw new IllegalStateException("No parrent for localization key");
            }
        }
        return new LocKey(path.substring(0, index + 1));
    }

    /**
     * @return true if this key has parrent
     */
    public boolean hasParrent() {
        int index = path.indexOf('/');
        return index > 0 && index < path.length() - 1;
    }

    /**
     * @return tokenized parts of key
     */
    public String[] getPathParts() {
        List<String> parts = new ArrayList<String>(3);
        StringTokenizer tokenizer = new StringTokenizer(path, "/", false);
        while (tokenizer.hasMoreTokens()) {
            parts.add(tokenizer.nextToken());
        }
        return parts.toArray(new String[parts.size()]);
    }

}
