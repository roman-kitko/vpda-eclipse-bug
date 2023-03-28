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

import java.text.DecimalFormat;
import java.text.Format;
import java.text.NumberFormat;

import org.vpda.internal.common.util.IntegerFormat;
import org.vpda.internal.common.util.StringUtil;

/**
 * Integer field
 * 
 * @author kitko
 *
 */
public final class IntegerFieldAC extends AbstractNumberFieldAC<Integer> {

    private static final long serialVersionUID = -8746169859794422073L;

    /** */
    public IntegerFieldAC() {
    }

    /**
     * Creates empty integer field
     * 
     * @param id
     */
    public IntegerFieldAC(String id) {
        super(id);
    }

    /**
     * Creates integer field
     * 
     * @param id
     * @param value
     */
    public IntegerFieldAC(String id, Integer value) {
        super(id, value);
    }

    /**
     * @param id
     * @param value
     * @param numberFormat
     */
    public IntegerFieldAC(String id, Integer value, NumberFormat numberFormat) {
        super(id, value, numberFormat);
    }

    /**
     * @param id
     * @param value
     * @param formatPattern
     */
    public IntegerFieldAC(String id, Integer value, String formatPattern) {
        super(id, value, formatPattern);
    }

    @Override
    public Format createFormat() {
        if (StringUtil.isEmpty(pattern)) {
            throw new IllegalArgumentException("Pattern is null");
        }
        return new IntegerFormat(new DecimalFormat(pattern));
    }

}
