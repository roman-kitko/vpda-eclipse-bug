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
 * Holder of localization data for input fields
 * 
 * @author kitko
 *
 */
@Immutable
public final class FieldLocValue extends AbstractCompLocValue {
    private static final long serialVersionUID = 8184429113970452638L;

    private FieldLocValue(Builder builder) {
        super(builder);
    }

    /** Builder for FieldLocValue */
    public final static class Builder extends AbstractCompLocValue.Builder<FieldLocValue> {
        @Override
        public FieldLocValue build() {
            return new FieldLocValue(this);
        }

        @Override
        public Class<? extends FieldLocValue> getTargetClass() {
            return FieldLocValue.class;
        }

    }

}
