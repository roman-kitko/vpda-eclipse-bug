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
package org.vpda.common.comps.loc;

import org.vpda.common.annotations.Immutable;

/**
 * Simple localization value
 * 
 * @author kitko
 *
 */
@Immutable
public final class SimpleLocValue extends AbstractCompLocValue {
    private static final long serialVersionUID = 8184429113970452638L;

    private SimpleLocValue(Builder builder) {
        super(builder);
    }

    /**
     * Creates SimpleLocValue
     * 
     * @param tooltip
     */
    public SimpleLocValue(String tooltip) {
        super(tooltip);
    }

    /**
     * Creates SimpleLocValue
     */
    public SimpleLocValue() {
        super("");
    }

    /** Builder for FieldLocValue */
    public final static class Builder extends AbstractCompLocValue.Builder<SimpleLocValue> {
        @Override
        public SimpleLocValue build() {
            return new SimpleLocValue(this);
        }

        @Override
        public Class<? extends SimpleLocValue> getTargetClass() {
            return SimpleLocValue.class;
        }

    }

}
