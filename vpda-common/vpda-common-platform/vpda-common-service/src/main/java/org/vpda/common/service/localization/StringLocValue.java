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
package org.vpda.common.service.localization;

import org.vpda.common.annotations.Immutable;

/**
 * LocValue as String
 * 
 * @author kitko
 * 
 *
 */
@Immutable
public final class StringLocValue implements LocValue {
    private static final long serialVersionUID = 1346300863343660736L;
    private final String locValue;

    /**
     * Creates SimpleLocValueImpl
     * 
     * @param locValue
     */
    public StringLocValue(String locValue) {
        this.locValue = locValue;
    }

    /**
     * @return locValue
     */
    public String getStringLocValue() {
        return locValue;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((locValue == null) ? 0 : locValue.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final StringLocValue other = (StringLocValue) obj;
        if (locValue == null) {
            if (other.locValue != null)
                return false;
        }
        else if (!locValue.equals(other.locValue))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return locValue;
    }

}
