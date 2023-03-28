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
package org.vpda.common.dto.runtime.spi;

import java.util.ServiceLoader;

import org.vpda.internal.common.util.ClassUtil;

final class DefaultDTORuntimeProviderResolver {
    private DTORuntimeProvider resolvedProvider;

    private static final String DEFAULT_PROVIDER_CLASS_NAME = "org.vpda.common.dto.runtime.spi.impl.DTORuntimeProviderImpl";

    public DTORuntimeProvider resolveDTORuntimeProvider() {
        if (resolvedProvider != null) {
            return resolvedProvider;
        }
        ServiceLoader<DTORuntimeProvider> sl = ServiceLoader.load(DTORuntimeProvider.class);
        if (sl.iterator().hasNext()) {
            resolvedProvider = sl.iterator().next();
        }
        if (resolvedProvider != null) {
            return resolvedProvider;
        }
        resolvedProvider = ClassUtil.createInstance(DEFAULT_PROVIDER_CLASS_NAME, DTORuntimeProvider.class);
        return resolvedProvider;
    }
}
