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

import java.io.ObjectStreamException;
import java.io.Serializable;

import org.vpda.common.context.TenementalContext;
import org.vpda.common.context.localization.LocKey;

/**
 * This is simplest loc value builder It will return same String retrieved using
 * locService.localizeString(locKey, context) method call
 * 
 * @author kitko
 *
 */
public final class StringLocValueBuilder implements LocValueBuilder<StringLocValue> {

    private static final long serialVersionUID = 1L;

    @Override
    public StringLocValue localizeData(LocValueBuilderFactory locValueBuilderFactory, LocalizationService locService, LocKey locKey, TenementalContext context, LocDataArguments params) {
        String value = locService.localizeMessage(locKey, context, params != null ? params.getRootArguments() : null);
        return value != null ? new StringLocValue(value) : null;
    }

    private StringLocValueBuilder() {
    }

    private static StringLocValueBuilder instance = new StringLocValueBuilder();

    /**
     * @return new instance
     */
    protected static StringLocValueBuilder getInstance() {
        return instance;
    }

    @Override
    public boolean containsLocValue(LocValueBuilderFactory locValueBuilderFactory, LocalizationService locService, LocKey locKey, TenementalContext context) {
        return locService.localizeString(locKey, context) != null;
    }

    Object readResolve() throws ObjectStreamException {
        return getInstance();
    }

    /**
     * Factory for {@link StringLocValueBuilder}
     * 
     * @author kitko
     *
     */
    public static class StringLocValueBuilderFactory implements LocValueBuilderFactory.OneLocValueBuilderFactory<StringLocValue, StringLocValueBuilder>, Serializable {

        private static final long serialVersionUID = -2423408126258489905L;

        @Override
        public StringLocValueBuilder createBuilder() {
            return getInstance();
        }

        @Override
        public Class<StringLocValueBuilder> getBuilderClass() {
            return StringLocValueBuilder.class;
        }

        @Override
        public Class<? extends LocValue> getLocValueClass() {
            return StringLocValue.class;
        }

    }

    @Override
    public Class<StringLocValue> getLocValueClass() {
        return StringLocValue.class;
    }

}
