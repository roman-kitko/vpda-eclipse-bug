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
public class LabelLocValueBuilderTest {

    /**
     * 
     */
    @Test
    public void testContainsLocValue() {
    }

    /**
     * Test method for
     * 
     */
    @Test
    public void testLocalizeData() {
        IconLocValue iconLocvalue = new IconLocValue("testIcon", new byte[0]);
        LocalizationService locService = EasyMock.createNiceMock(LocalizationService.class);
        EasyMock.expect(locService.localizeMessage(EasyMock.isA(LocKey.class), EasyMock.isA(TenementalContext.class), EasyMock.isA(String.class))).andReturn("testValue").anyTimes();
        EasyMock.expect(locService.localizeMessage(EasyMock.isA(LocKey.class), EasyMock.isA(TenementalContext.class))).andReturn("testValue").anyTimes();
        EasyMock.expect(locService.localizeData(EasyMock.isA(LocKey.class), EasyMock.isA(TenementalContext.class), EasyMock.isA(IconLocValueBuilder.class), (IconLocValue) EasyMock.anyObject(),
                (LocDataArguments) EasyMock.anyObject())).andReturn(iconLocvalue);
        EasyMock.replay(locService);
        LocValueBuilderFactory lvbf = new LocValueBuilderRegistry();
        ResourceService rs = new ResourceServiceImpl();
        Application application = new ConcreteApplication(CommonUtilConstants.VPDA_PROJECT_NAME, new AppConfigurationImpl());
        RelativePathInputStreamResolver iconsStreamResolver = new AllModulesRelativeInputStreamResolver(application, "", ModuleHomeRelativeURLResolver.create(ConfigurationConstants.DATA_ICONS_PATH));
        new BasicCompsLocValueBuilderCollector(lvbf, rs, iconsStreamResolver).collect();
        LabelLocValue labelLocValue = LabelLocValueBuilder.getInstance().localizeData(lvbf, locService, new LocKey("testPath", "testKey"), CommonCoreTestHelper.createTestContext(), null);
        assertNotNull(labelLocValue);
        assertEquals("testValue", labelLocValue.getLabel());
        assertEquals("testValue", labelLocValue.getTooltip());
        assertSame(iconLocvalue, labelLocValue.getIconValue());
    }

    /**
     * Test method for
     */
    @Test
    public void testGetInstance() {
        assertSame(LabelLocValueBuilder.getInstance(), new LabelLocValueBuilder.LabelLocValueBuilderFactory().createBuilder());
    }

}
