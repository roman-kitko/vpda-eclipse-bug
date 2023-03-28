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
 * Compression settings when not compressing
 * 
 * @author kitko
 * 
 */
public final class NoneCompressionSettings implements CompressionSettings {

    /**
     * Creates the settings
     */
    public NoneCompressionSettings() {
    }

    @Override
    public CompressionType getCompressionType() {
        return CompressionType.NONE;
    }

    private static final NoneCompressionSettings instance = new NoneCompressionSettings();

    /**
     * 
     * @return instance
     */
    public static NoneCompressionSettings getInstance() {
        return instance;
    }

}
