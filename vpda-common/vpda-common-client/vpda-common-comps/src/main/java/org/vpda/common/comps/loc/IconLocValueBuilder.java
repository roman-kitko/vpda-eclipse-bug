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
package org.vpda.common.comps.loc;

import java.io.IOException;
import java.io.Serializable;

import org.vpda.common.context.TenementalContext;
import org.vpda.common.context.localization.LocKey;
import org.vpda.common.service.localization.LocDataArguments;
import org.vpda.common.service.localization.LocValueBuilder;
import org.vpda.common.service.localization.LocValueBuilderFactory;
import org.vpda.common.service.localization.LocalizationService;
import org.vpda.common.service.resources.LoadResourceRequest;
import org.vpda.common.service.resources.RelativePathInputStreamResolver;
import org.vpda.common.service.resources.ResourceService;

/**
 * IconLocValueBuilder will create IconLocValue from localization service
 * 
 * @author kitko
 *
 */
public final class IconLocValueBuilder implements LocValueBuilder<IconLocValue> {
    private static final long serialVersionUID = -6367644664926410824L;

    private final RelativePathInputStreamResolver iconDataResolver;
    private final ResourceService resourceService;

    /**
     * Creates builder
     * 
     * @param iconDataResolver
     * @param resourceService
     */
    public IconLocValueBuilder(RelativePathInputStreamResolver iconDataResolver, ResourceService resourceService) {
        if (iconDataResolver == null) {
            throw new IllegalArgumentException("IconDataResolver argument is null");
        }
        if (resourceService == null) {
            throw new IllegalArgumentException("ResourceService argument is null");
        }
        this.iconDataResolver = iconDataResolver;
        this.resourceService = resourceService;
    }

    @Override
    public boolean containsLocValue(LocValueBuilderFactory locValueBuilderFactory, LocalizationService locService, LocKey locKey, TenementalContext context) {
        return locService.localizeString(locKey, context) != null;
    }

    @Override
    public IconLocValue localizeData(LocValueBuilderFactory locValueBuilderFactory, LocalizationService locService, LocKey locKey, TenementalContext context, LocDataArguments params) {
        String iconPath = locService.localizeString(locKey, context);
        if (locKey.getKey().endsWith("png") || locKey.getKey().endsWith("gif")) {
            iconPath = locKey.getPath();
        }
        if (iconPath == null) {
            return null;
        }
        return localizeData(locValueBuilderFactory, locService, iconPath);
    }

    /**
     * Localize data
     * 
     * @param locValueBuilderFactory
     * @param locService
     * @param iconPath
     * @return IconLocValue or null if cannot localize
     */
    public IconLocValue localizeData(LocValueBuilderFactory locValueBuilderFactory, LocalizationService locService, String iconPath) {
        if (iconPath == null) {
            return null;
        }
        byte[] iconData = null;
        if (iconPath != null) {
            try {
                iconData = resourceService.loadResource(new LoadResourceRequest(iconDataResolver, true, iconPath));
            }
            catch (IOException e) {
            }
        }
        IconLocValue iconLocValue = new IconLocValue.Builder().setIconPath(iconPath).setIconData(iconData).build();
        return iconLocValue;
    }

    @Override
    public Class<IconLocValue> getLocValueClass() {
        return IconLocValue.class;
    }

    /**
     * Factory
     * 
     * @author kitko
     *
     */
    public static final class IconLocValueBuilderFactory implements LocValueBuilderFactory.OneLocValueBuilderFactory<IconLocValue, IconLocValueBuilder>, Serializable {
        private static final long serialVersionUID = 2061523844698283432L;
        private final RelativePathInputStreamResolver is;
        private final ResourceService rs;

        /**
         * @param is
         * @param rs
         */
        public IconLocValueBuilderFactory(RelativePathInputStreamResolver is, ResourceService rs) {
            super();
            if (is == null) {
                throw new IllegalArgumentException("RelativePathInputStreamResolver argument is null");
            }
            this.is = is;
            if (rs == null) {
                throw new IllegalArgumentException("ResourceService argument is null");
            }
            this.rs = rs;
        }

        @Override
        public IconLocValueBuilder createBuilder() {
            return new IconLocValueBuilder(is, rs);
        }

        @Override
        public Class<IconLocValueBuilder> getBuilderClass() {
            return IconLocValueBuilder.class;
        }

        @Override
        public Class<IconLocValue> getLocValueClass() {
            return IconLocValue.class;
        }

    }

}
