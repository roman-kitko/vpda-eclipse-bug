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

import java.text.DateFormat;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.vpda.internal.common.util.StringUtil;

/**
 * Date field
 * 
 * @author kitko
 * @param <D>
 *
 */
public abstract class AbstractDateFieldAC<D extends Date> extends AbstractFormatttedFieldAC<D> {

    private static final long serialVersionUID = -6686238224000505572L;

    /** */
    public AbstractDateFieldAC() {
    }

    /**
     * Constructs empty Date field component
     * 
     * @param id
     */
    public AbstractDateFieldAC(String id) {
        super(id);
    }

    /**
     * Constructs Date field component
     * 
     * @param id
     * @param value
     */
    public AbstractDateFieldAC(String id, D value) {
        super(id, value);
    }

    /**
     * Constructs Date field component
     * 
     * @param id
     * @param value
     * @param format
     */
    public AbstractDateFieldAC(String id, D value, DateFormat format) {
        super(id, value, format);
    }

    /**
     * Constructs Date field component
     * 
     * @param id
     * @param value
     * @param formatPattern
     */
    public AbstractDateFieldAC(String id, D value, String formatPattern) {
        super(id, value, formatPattern);
    }

    /**
     * @return date format
     */
    @Override
    public DateFormat getFormat() {
        return (DateFormat) format;
    }

    /**
     * Sets format
     * 
     * @param format
     */
    public void setDateFormat(DateFormat format) {
        this.format = format;
    }

    @Override
    public Format createFormat() {
        if (StringUtil.isEmpty(pattern)) {
            throw new IllegalArgumentException("Pattern is null");
        }
        return new SimpleDateFormat(pattern);
    }

}
