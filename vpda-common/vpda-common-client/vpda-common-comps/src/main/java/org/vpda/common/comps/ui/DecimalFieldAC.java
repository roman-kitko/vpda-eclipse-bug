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

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.Format;
import java.text.NumberFormat;

import org.vpda.internal.common.util.StringUtil;

/**
 * Decimal field
 * 
 * @author kitko
 *
 */
public final class DecimalFieldAC extends AbstractNumberFieldAC<BigDecimal> {
    private static final long serialVersionUID = 5429218848139099328L;

    /** */
    public DecimalFieldAC() {
    }

    /**
     * Creates empty Decimal field
     * 
     * @param id
     */
    public DecimalFieldAC(String id) {
        super(id);
    }

    /**
     * Creates Decimal field
     * 
     * @param id
     * @param value
     */
    public DecimalFieldAC(String id, BigDecimal value) {
        super(id, value);
    }

    /**
     * @param id
     * @param value
     * @param numberFormat
     */
    public DecimalFieldAC(String id, BigDecimal value, NumberFormat numberFormat) {
        super(id, value, numberFormat);
    }

    /**
     * @param id
     * @param value
     * @param formatPattern
     */
    public DecimalFieldAC(String id, BigDecimal value, String formatPattern) {
        super(id, value, formatPattern);
    }

    @Override
    public Format createFormat() {
        if (StringUtil.isEmpty(pattern)) {
            throw new IllegalArgumentException("Pattern is null");
        }
        return new DecimalFormat(pattern);
    }

}
