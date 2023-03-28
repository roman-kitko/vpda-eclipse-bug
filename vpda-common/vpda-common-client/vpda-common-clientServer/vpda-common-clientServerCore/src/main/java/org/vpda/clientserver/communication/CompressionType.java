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
package org.vpda.clientserver.communication;

import java.util.Arrays;

/** Type of compression */
public enum CompressionType {
    /** None compression */
    NONE("f:n"),
    /** GZIP compression */
    GZIP("f:g");

    CompressionType(String format) {
        this.format = format;
        this.bytes = format.getBytes();
    }

    private final String format;
    private final byte[] bytes;

    /**
     * 
     * @return format string
     */
    public String getFormat() {
        return format;
    }

    /**
     * 
     * @return format bytes
     */
    public byte[] getFormatBytes() {
        return bytes;
    }

    /**
     * Resolve compression type by bytes
     * 
     * @param bytes
     * @return CompressionType
     */
    public static CompressionType resolveByBytes(byte[] bytes) {
        for (CompressionType t : CompressionType.values()) {
            if (Arrays.equals(bytes, t.getFormatBytes())) {
                return t;
            }
        }
        throw new IllegalArgumentException("Cannot resolve CompressionType");
    }

}
