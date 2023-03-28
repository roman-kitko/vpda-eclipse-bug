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

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Collections;

import org.apache.commons.vfs2.FileSystemException;
import org.junit.jupiter.api.Test;
import org.vpda.common.context.localization.LocKey;
import org.vpda.common.core.CommonCoreTestHelper;
import org.vpda.common.entrypoint.AppConfigurationImpl;
import org.vpda.common.entrypoint.Application;
import org.vpda.common.entrypoint.ConcreteApplication;
import org.vpda.common.entrypoint.ConfigurationConstants;
import org.vpda.common.service.localization.LocValueBuilderFactory;
import org.vpda.common.service.localization.LocValueBuilderRegistry;
import org.vpda.common.service.localization.LocalizationService;
import org.vpda.common.service.localization.LocalizationServiceImpl;
import org.vpda.common.service.localization.URLsLocalizationValueResolver;
import org.vpda.common.service.resources.AllModulesRelativeInputStreamResolver;
import org.vpda.common.service.resources.ModuleHomeRelativeURLResolver;
import org.vpda.common.service.resources.RelativePathInputStreamResolver;
import org.vpda.common.service.resources.ResourceException;
import org.vpda.common.service.resources.ResourceService;
import org.vpda.common.service.resources.ResourceServiceImpl;
import org.vpda.internal.common.util.contants.CommonUtilConstants;

/**
 * @author kitko
 *
 */
public class IconLocValueBuilderTest {

    /**
     * @throws FileSystemException
     * 
     */
    @Test
    public void testContainsLocValue() throws FileSystemException {
        assertTrue(createLocService().containsLocData(getTestLocKey(), CommonCoreTestHelper.createTestContext(), createTestee()));
    }

    /**
     * @throws FileSystemException
     * 
     */
    @Test
    public void testLocalizeData() throws FileSystemException {
        IconLocValue iconLocValue = createLocService().localizeData(getTestLocKey(), CommonCoreTestHelper.createTestContext(), createTestee(), null, null);
        assertNotNull(iconLocValue);
        assertNotNull(iconLocValue.getIconData());
        assertNotNull(iconLocValue.getIconPath());
    }

    private IconLocValueBuilder createTestee() {
        ResourceService rs = new ResourceServiceImpl();
        RelativePathInputStreamResolver iconsStreamResolver = new RelativePathInputStreamResolver() {
            private static final long serialVersionUID = -7403725121681946914L;

            @Override
            public Object resolveKey() throws ResourceException {
                throw new UnsupportedOperationException("resolveKey");
            }

            @Override
            public InputStream resolveStream() throws IOException, ResourceException {
                throw new UnsupportedOperationException("resolveStream");
            }

            @Override
            public Object resolveKey(String relativePath) throws IOException, ResourceException {
                return relativePath;
            }

            @Override
            public InputStream resolveStream(String relativePath) throws IOException, ResourceException {
                URL url = IconLocValueBuilderTest.class.getResource(IconLocValueBuilderTest.class.getSimpleName() + ".class");
                URL resultUrl = new URL(url, relativePath);
                return resultUrl.openStream();
            }

        };
        return new IconLocValueBuilder.IconLocValueBuilderFactory(iconsStreamResolver, rs).createBuilder();
    }

    private LocKey getTestLocKey() {
        return new LocKey("testComps", "testButton/icon");
    }

    private LocalizationService createLocService() throws FileSystemException {
        URL url = IconLocValueBuilderTest.class.getResource(IconLocValueBuilderTest.class.getSimpleName() + ".class");
        URLsLocalizationValueResolver uRLsLocalizationValueResolver = new URLsLocalizationValueResolver(Collections.singletonList(url));
        Application application = new ConcreteApplication(CommonUtilConstants.VPDA_PROJECT_NAME, new AppConfigurationImpl());
        ResourceService rs = new ResourceServiceImpl();
        RelativePathInputStreamResolver iconsStreamResolver = new AllModulesRelativeInputStreamResolver(application, "", ModuleHomeRelativeURLResolver.create(ConfigurationConstants.DATA_ICONS_PATH));
        LocValueBuilderFactory lvbf = new LocValueBuilderRegistry();
        new BasicCompsLocValueBuilderCollector(lvbf, rs, iconsStreamResolver).collect();
        return new LocalizationServiceImpl(uRLsLocalizationValueResolver, lvbf);
    }

}
