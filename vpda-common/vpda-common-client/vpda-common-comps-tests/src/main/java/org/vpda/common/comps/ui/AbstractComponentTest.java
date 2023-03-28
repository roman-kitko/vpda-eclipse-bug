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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.net.URL;
import java.util.Collections;

import org.apache.commons.vfs2.FileSystemException;
import org.junit.jupiter.api.Test;
import org.vpda.common.comps.loc.BasicCompsLocValueBuilderCollector;
import org.vpda.common.comps.loc.IconLocValueBuilderTest;
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
import org.vpda.common.service.resources.ResourceService;
import org.vpda.common.service.resources.ResourceServiceImpl;
import org.vpda.internal.common.util.contants.CommonUtilConstants;

/**
 * Abstract Test class for AbstractViewProviderComponent
 * 
 * @author kitko
 *
 */
public abstract class AbstractComponentTest {

    /**
     * @return test AbstractViewProviderComponent
     */
    protected abstract AbstractComponent createTestee();

    /**
     * @return test value to be set to component
     */
    protected abstract Object createTestValue();

    /**
     * Test method for {@link org.vpda.common.comps.ui.AbstractComponent#getId()}.
     */
    @Test
    public void testGetId() {
        AbstractComponent c = createTestee();
        assertNotNull(c.getId());
    }

    /**
     * Test
     */
    @SuppressWarnings("unchecked")
    @Test
    public void testGetValue() {
        Component c = createTestee();
        Object value = createTestValue();
        c.setValue(value);
        assertEquals(value, c.getValue());

    }

    /**
     * Test
     */
    @Test
    public void testIsEnabled() {
        AbstractComponent c = createTestee();
        c.setEnabled(true);
        assertTrue(c.isEnabled());
        c.setEnabled(false);
        assertFalse(c.isEnabled());
    }

    /**
     * Test method for
     * {@link org.vpda.common.comps.ui.AbstractComponent#isVisible()}.
     */
    @Test
    public void testIsVisible() {
        Component c = createTestee();
        c.setVisible(true);
        assertTrue(c.isVisible());
        c.setVisible(false);
        assertFalse(c.isVisible());
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

    /**
     * Test localization of component
     * 
     * @throws FileSystemException
     */
    @Test
    public void testLocalize() throws FileSystemException {
        AbstractComponent c = createTestee();
        c.setLocKey(new LocKey("path", "key"));
        c.localize(createLocService(), CommonCoreTestHelper.createTestContext());
    }

}
