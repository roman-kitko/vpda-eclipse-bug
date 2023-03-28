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

import java.text.Format;

/**
 * Formatted field field
 * 
 * @author kitko
 * @param <T> type of value
 *
 */
public abstract class AbstractFormatttedFieldAC<T> extends AbstractFieldAC<T> {

    private static final long serialVersionUID = -6686238224000505572L;
    /** Real format */
    protected Format format;
    /** Pattern of format */
    protected String pattern;

    /** */
    protected AbstractFormatttedFieldAC() {
    }

    /**
     * Constructs empty field component
     * 
     * @param id
     */
    protected AbstractFormatttedFieldAC(String id) {
        this(id, null);
    }

    /**
     * Constructs field component
     * 
     * @param id
     * @param value
     */
    protected AbstractFormatttedFieldAC(String id, T value) {
        this(id, value, (Format) null);
    }

    /**
     * Constructs field component
     * 
     * @param id
     * @param value
     * @param format
     */
    protected AbstractFormatttedFieldAC(String id, T value, Format format) {
        super(id);
        setValue(value);
        setFormat(format);
    }

    /**
     * Constructs Date field component
     * 
     * @param id
     * @param value
     * @param formatPattern
     */
    public AbstractFormatttedFieldAC(String id, T value, String formatPattern) {
        super(id);
        setValue(value);
        setFormatPattern(formatPattern);
    }

    /**
     * @return date format
     */
    public Format getFormat() {
        return format;
    }

    /**
     * @return format patter
     */
    public String getFormatPattern() {
        return pattern;
    }

    /**
     * Sets format
     * 
     * @param format
     */
    public void setFormat(Format format) {
        this.format = format;

    }

    /**
     * Sets format pattern
     * 
     * @param pattern
     */
    public void setFormatPattern(String pattern) {
        this.pattern = pattern;
    }

    /**
     * Creates format from patter
     * 
     * @return new format
     */
    public abstract Format createFormat();
}
