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
/**
 * 
 */
package org.vpda.internal.common.util;

import java.math.RoundingMode;
import java.text.AttributedCharacterIterator;
import java.text.FieldPosition;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.util.Currency;

/**
 * Format that will return integers from parse using passed delegate
 * 
 * @author kitko
 *
 */
public final class IntegerFormat extends NumberFormat {
    private static final long serialVersionUID = 7716143308537618146L;

    private final NumberFormat delegate;

    /**
     * Creates IntegerFormat
     * 
     * @param delegate
     */
    public IntegerFormat(NumberFormat delegate) {
        this.delegate = Assert.isNotNull(delegate, "Delegate argument is null");
    }

    @Override
    public Object clone() {
        return delegate.clone();
    }

    @Override
    public boolean equals(Object obj) {
        return delegate.equals(obj);
    }

    @Override
    public StringBuffer format(double number, StringBuffer toAppendTo, FieldPosition pos) {
        return delegate.format(number, toAppendTo, pos);
    }

    @Override
    public StringBuffer format(long number, StringBuffer toAppendTo, FieldPosition pos) {
        return delegate.format(number, toAppendTo, pos);
    }

    @Override
    public StringBuffer format(Object number, StringBuffer toAppendTo, FieldPosition pos) {
        return delegate.format(number, toAppendTo, pos);
    }

    @Override
    public AttributedCharacterIterator formatToCharacterIterator(Object obj) {
        return delegate.formatToCharacterIterator(obj);
    }

    @Override
    public Currency getCurrency() {
        return delegate.getCurrency();
    }

    @Override
    public int getMaximumFractionDigits() {
        return delegate.getMaximumFractionDigits();
    }

    @Override
    public int getMaximumIntegerDigits() {
        return delegate.getMaximumIntegerDigits();
    }

    @Override
    public int getMinimumFractionDigits() {
        return delegate.getMinimumFractionDigits();
    }

    @Override
    public int getMinimumIntegerDigits() {
        return delegate.getMinimumIntegerDigits();
    }

    @Override
    public RoundingMode getRoundingMode() {
        return delegate.getRoundingMode();
    }

    @Override
    public int hashCode() {
        return delegate.hashCode();
    }

    @Override
    public boolean isGroupingUsed() {
        return delegate.isGroupingUsed();
    }

    @Override
    public boolean isParseIntegerOnly() {
        return delegate.isParseIntegerOnly();
    }

    @Override
    public Number parse(String source, ParsePosition parsePosition) {
        Number number = delegate.parse(source, parsePosition);
        return number instanceof Integer ? number : Integer.valueOf(number.intValue());
    }

    @Override
    public Number parse(String source) throws ParseException {
        Number number = delegate.parse(source);
        return number instanceof Integer ? number : Integer.valueOf(number.intValue());
    }

    @Override
    public Object parseObject(String source) throws ParseException {
        Number number = (Number) delegate.parseObject(source);
        return number instanceof Integer ? number : Integer.valueOf(number.intValue());
    }

    @Override
    public void setCurrency(Currency currency) {
        delegate.setCurrency(currency);
    }

    @Override
    public void setGroupingUsed(boolean newValue) {
        delegate.setGroupingUsed(newValue);
    }

    @Override
    public void setMaximumFractionDigits(int newValue) {
        delegate.setMaximumFractionDigits(newValue);
    }

    @Override
    public void setMaximumIntegerDigits(int newValue) {
        delegate.setMaximumIntegerDigits(newValue);
    }

    @Override
    public void setMinimumFractionDigits(int newValue) {
        delegate.setMinimumFractionDigits(newValue);
    }

    @Override
    public void setMinimumIntegerDigits(int newValue) {
        delegate.setMinimumIntegerDigits(newValue);
    }

    @Override
    public void setParseIntegerOnly(boolean value) {
        delegate.setParseIntegerOnly(value);
    }

    @Override
    public void setRoundingMode(RoundingMode roundingMode) {
        delegate.setRoundingMode(roundingMode);
    }

    @Override
    public String toString() {
        return delegate.toString();
    }

}
