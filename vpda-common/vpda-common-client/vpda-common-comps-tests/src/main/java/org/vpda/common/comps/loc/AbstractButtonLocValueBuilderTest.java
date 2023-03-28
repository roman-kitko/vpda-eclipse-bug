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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;

import org.easymock.EasyMock;
import org.junit.jupiter.api.Test;
import org.vpda.common.context.TenementalContext;
import org.vpda.common.context.localization.LocKey;
import org.vpda.common.core.CommonCoreTestHelper;
import org.vpda.common.entrypoint.AppConfigurationImpl;
import org.vpda.common.entrypoint.Application;
import org.vpda.common.entrypoint.ConcreteApplication;
import org.vpda.common.entrypoint.ConfigurationConstants;
import org.vpda.common.service.localization.LocDataArguments;
import org.vpda.common.service.localization.LocValueBuilderFactory;
import org.vpda.common.service.localization.LocValueBuilderRegistry;
import org.vpda.common.service.localization.LocalizationService;
import org.vpda.common.service.resources.AllModulesRelativeInputStreamResolver;
import org.vpda.common.service.resources.ModuleHomeRelativeURLResolver;
import org.vpda.common.service.resources.RelativePathInputStreamResolver;
import org.vpda.common.service.resources.ResourceService;
import org.vpda.common.service.resources.ResourceServiceImpl;
import org.vpda.internal.common.util.contants.CommonUtilConstants;

/**
 * @author kitko
 * 
 */
public abstract class AbstractButtonLocValueBuilderTest {

    /**
     */
    @Test
    public void testContainsLocValue() {
    }

    /**
     */
    @Test
    public void testLocalizeData() {
        LocKey locKey = new LocKey("testPath", "testKey");
        IconLocValue iconLocvalue = new IconLocValue("testIcon", new byte[0]);
        LocalizationService locService = EasyMock.createNiceMock(LocalizationService.class);
        setupLocServiceMock(locService, locKey);
        EasyMock.expect(locService.localizeMessage(EasyMock.eq(locKey.createChildKey("mnemonic")), EasyMock.isA(TenementalContext.class))).andReturn("B").once();
        EasyMock.expect(locService.localizeMessage(EasyMock.isA(LocKey.class), EasyMock.isA(TenementalContext.class), EasyMock.isA(String.class))).andReturn("testValue").anyTimes();
        EasyMock.expect(locService.localizeMessage(EasyMock.isA(LocKey.class), EasyMock.isA(TenementalContext.class))).andReturn("testValue").anyTimes();
        EasyMock.expect(locService.localizeData(EasyMock.isA(LocKey.class), EasyMock.isA(TenementalContext.class), EasyMock.isA(IconLocValueBuilder.class), (IconLocValue) EasyMock.anyObject(),
                (LocDataArguments) EasyMock.anyObject())).andReturn(iconLocvalue);
        EasyMock.replay(locService);
        LocValueBuilderFactory lvbf = new LocValueBuilderRegistry();
        Application application = new ConcreteApplication(CommonUtilConstants.VPDA_PROJECT_NAME, new AppConfigurationImpl());
        ResourceService rs = new ResourceServiceImpl();
        RelativePathInputStreamResolver iconsStreamResolver = new AllModulesRelativeInputStreamResolver(application, "", ModuleHomeRelativeURLResolver.create(ConfigurationConstants.DATA_ICONS_PATH));

        new BasicCompsLocValueBuilderCollector(lvbf, rs, iconsStreamResolver).collect();
        AbstractButtonLocValue buttonLocValue = createTestee().localizeData(lvbf, locService, locKey, CommonCoreTestHelper.createTestContext(), null);
        assertNotNull(buttonLocValue);
        assertEquals("testValue", buttonLocValue.getLabel());
        assertEquals("testValue", buttonLocValue.getTooltip());
        assertEquals(Character.valueOf('B'), buttonLocValue.getMnemonic());
        assertSame(iconLocvalue, buttonLocValue.getIconValue());
        testLocValue(buttonLocValue);
    }

    /**
     * Setup locservice mock in subclasses
     * 
     * @param locService
     * @param locKey
     */
    protected void setupLocServiceMock(LocalizationService locService, LocKey locKey) {
    }

    /**
     * Here test locvalue in subclasses
     * 
     * @param buttonLocValue
     */
    protected void testLocValue(AbstractButtonLocValue buttonLocValue) {
    }

    /**
     */
    @Test
    public void testGetInstance() {
        assertSame(createTestee(), createTestee());
    }

    /**
     * Creates test instance of loc builder
     * 
     * @return instanceof AbstractButtonLocValueBuilder
     */
    protected abstract AbstractButtonLocValueBuilder<? extends AbstractButtonLocValue> createTestee();

}
