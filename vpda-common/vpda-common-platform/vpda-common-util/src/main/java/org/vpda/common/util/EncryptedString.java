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
import java.util.Arrays;

/**
 * Encrypted String
 * 
 * @author kitko
 *
 */
public final class EncryptedString implements Serializable {
    private static final long serialVersionUID = -3502401044062318260L;

    /** Accessor of clear chars stored in this string */
    public interface Accessor {
        /**
         * Access clear chars of EncryptedString
         * 
         * @param clearChars
         */
        public void access(char[] clearChars);
    }

    private byte[] bytes;

    /**
     * Publish clear chars
     * 
     * @param accessor
     */
    public void access(Accessor accessor) {
        if (bytes == null) {
            throw new RuntimeException("Cannot access EncryptedString, bytes are already cleared");
        }
        char[] clearChars = bytesToChars(bytes);
        try {
            accessor.access(clearChars);
        }
        finally {
            Arrays.fill(clearChars, (char) 0);
            clearChars = null;
        }
    }

    /**
     * Clears this string
     */
    public void clear() {
        Arrays.fill(bytes, (byte) 0);
        bytes = null;
    }

    /**
     * Creates EncryptedString
     * 
     * @param chars
     */
    public EncryptedString(char[] chars) {
        bytes = charsToBytes(chars);
    }

    /** Fox JAXB */
    @SuppressWarnings("unused")
    private EncryptedString() {
    }

    /**
     * @param chars
     * @return bytes
     */
    static byte[] charsToBytes(char[] chars) {
        byte[] bytes = new byte[chars.length * 2];
        for (int i = 0; i < chars.length; i++) {
            char v = chars[i];
            bytes[i * 2] = (byte) (0xff & (v >> 8));
            bytes[i * 2 + 1] = (byte) (0xff & (v));
        }
        return bytes;
    }

    /**
     * @param bytes
     * @return chars
     */
    static char[] bytesToChars(byte[] bytes) {
        char[] chars = new char[bytes.length / 2];
        for (int i = 0; i < chars.length; i++) {
            char v = (char) (((0xFF & (bytes[i * 2])) << 8) | (0xFF & bytes[i * 2 + 1]));
            chars[i] = v;
        }
        return chars;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + Arrays.hashCode(bytes);
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
        EncryptedString other = (EncryptedString) obj;
        if (!Arrays.equals(bytes, other.bytes)) {
            return false;
        }
        return true;
    }

}
