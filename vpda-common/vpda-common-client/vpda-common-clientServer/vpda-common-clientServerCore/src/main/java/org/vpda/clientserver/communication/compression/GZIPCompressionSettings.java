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
package org.vpda.clientserver.communication.compression;

import org.vpda.clientserver.communication.CompressionSettings;
import org.vpda.clientserver.communication.CompressionType;

/**
 * Compression settings when using GZIP
 * 
 * @author kitko
 *
 */
public final class GZIPCompressionSettings implements CompressionSettings {

    /**
     * Creates the settings
     */
    public GZIPCompressionSettings() {
    }

    @Override
    public CompressionType getCompressionType() {
        return CompressionType.GZIP;
    }

    private static final GZIPCompressionSettings instance = new GZIPCompressionSettings();

    /**
     * 
     * @return instance
     */
    public static GZIPCompressionSettings getInstance() {
        return instance;
    }

}
