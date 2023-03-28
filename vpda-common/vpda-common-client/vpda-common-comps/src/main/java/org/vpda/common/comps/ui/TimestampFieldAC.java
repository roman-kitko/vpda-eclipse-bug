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
package org.vpda.common.comps.ui;

import java.sql.Timestamp;
import java.text.DateFormat;

/**
 * Timestamp field
 * 
 * @author kitko
 *
 */
public final class TimestampFieldAC extends AbstractDateFieldAC<Timestamp> {

    private static final long serialVersionUID = -6686238224000505572L;

    /** */
    public TimestampFieldAC() {
    }

    /**
     * Constructs empty Date field component
     * 
     * @param id
     */
    public TimestampFieldAC(String id) {
        super(id);
    }

    /**
     * Constructs Date field component
     * 
     * @param id
     * @param value
     */
    public TimestampFieldAC(String id, Timestamp value) {
        super(id, value);
    }

    /**
     * Constructs Date field component
     * 
     * @param id
     * @param value
     * @param format
     */
    public TimestampFieldAC(String id, Timestamp value, DateFormat format) {
        super(id, value, format);
    }

    /**
     * Constructs Date field component
     * 
     * @param id
     * @param value
     * @param formatPattern
     */
    public TimestampFieldAC(String id, Timestamp value, String formatPattern) {
        super(id, value, formatPattern);
    }
}
