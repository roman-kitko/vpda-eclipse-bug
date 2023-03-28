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

import org.vpda.common.service.localization.LocValueBuilderFactory;
import org.vpda.common.service.resources.RelativePathInputStreamResolver;
import org.vpda.common.service.resources.ResourceService;
import org.vpda.internal.common.util.Assert;

/**
 * This is collector for basic client server components factories
 * 
 * @author kitko
 *
 */
public final class BasicCompsLocValueBuilderCollector {
    private final LocValueBuilderFactory locValueBuilderFactory;
    private final ResourceService resourceService;
    private final RelativePathInputStreamResolver iconsStreamResolver;

    /**
     * Creates BasicCompsLocValueBuilderCollector
     * 
     * @param locValueBuilderFactory
     * @param resourceService
     * @param iconsStreamResolver
     */
    public BasicCompsLocValueBuilderCollector(LocValueBuilderFactory locValueBuilderFactory, ResourceService resourceService, RelativePathInputStreamResolver iconsStreamResolver) {
        this.locValueBuilderFactory = Assert.isNotNullArgument(locValueBuilderFactory, "locValueBuilderFactory");
        this.resourceService = Assert.isNotNullArgument(resourceService, "resourceService");
        this.iconsStreamResolver = Assert.isNotNullArgument(iconsStreamResolver, "iconsStreamResolver");
    }

    /**
     * Collect LocValueBuilderFactory.OneLocValueBuilderFactory
     */
    public void collect() {
        locValueBuilderFactory.registerOneLocValueBuilderFactory(new IconLocValueBuilder.IconLocValueBuilderFactory(iconsStreamResolver, resourceService));
        locValueBuilderFactory.registerOneLocValueBuilderFactory(new LabelLocValueBuilder.LabelLocValueBuilderFactory());
        locValueBuilderFactory.registerOneLocValueBuilderFactory(new MultiOptionLocValueBuilder.MultiOptionLocValueBuilderFactory());
        locValueBuilderFactory.registerOneLocValueBuilderFactory(new PushButtonLocValueBuilder.PushButtonLocValueBuilderFactory());
        locValueBuilderFactory.registerOneLocValueBuilderFactory(new ToggleButtonLocValueBuilder.ToggleButtonLocValueBuilderFactory());
        locValueBuilderFactory.registerOneLocValueBuilderFactory(new FieldLocValueBuilder.FieldLocValueBuilderFactory());
        locValueBuilderFactory.registerOneLocValueBuilderFactory(new ComboBoxLocValueBuilder.ComboBoxLocValueBuilderFactory());
        locValueBuilderFactory.registerOneLocValueBuilderFactory(new ListBoxLocValueBuilder.ListBoxLocValueBuilderFactory());
        locValueBuilderFactory.registerOneLocValueBuilderFactory(new SpinnerLocValueBuilder.SpinnerLocValueBuilderFactory());
        locValueBuilderFactory.registerOneLocValueBuilderFactory(new MenuItemLocValueBuilder.MenuItemLocValueBuilderFactory());
    }
}
