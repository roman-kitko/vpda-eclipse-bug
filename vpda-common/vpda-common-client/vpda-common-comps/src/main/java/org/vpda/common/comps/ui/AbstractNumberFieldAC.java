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

import java.text.NumberFormat;

/**
 * 
 * @author kitko
 * @param <V> - type of number value
 *
 */
public abstract class AbstractNumberFieldAC<V extends Number> extends AbstractFormatttedFieldAC<V> {
    private static final long serialVersionUID = -2011521477128165428L;

    /** */
    protected AbstractNumberFieldAC() {
    }

    /**
     * @param id
     */
    protected AbstractNumberFieldAC(String id) {
        super(id);
    }

    /**
     * Creates number component with id and value
     * 
     * @param id
     * @param value
     */
    protected AbstractNumberFieldAC(String id, V value) {
        this(id, value, (String) null);
    }

    /**
     * Creates number component with id, value, string pattern
     * 
     * @param id
     * @param value
     * @param formatPattern
     */
    protected AbstractNumberFieldAC(String id, V value, String formatPattern) {
        super(id);
        setValue(value);
        setFormatPattern(formatPattern);
    }

    /**
     * Creates number component with id, value, format
     * 
     * @param id
     * @param value
     * @param numberFormat
     */
    protected AbstractNumberFieldAC(String id, V value, NumberFormat numberFormat) {
        super(id);
        setValue(value);
        setNumberFormat(numberFormat);
    }

    /**
     * @return format
     */
    @Override
    public NumberFormat getFormat() {
        return (NumberFormat) format;
    }

    /**
     * Sets format
     * 
     * @param format
     */
    public void setNumberFormat(NumberFormat format) {
        this.format = format;
    }

}
