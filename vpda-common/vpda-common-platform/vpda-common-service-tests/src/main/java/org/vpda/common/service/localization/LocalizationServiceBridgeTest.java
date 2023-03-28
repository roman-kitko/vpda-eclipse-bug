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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.apache.commons.vfs2.FileSystemException;
import org.junit.jupiter.api.Test;
import org.vpda.common.context.ApplContext;
import org.vpda.common.context.localization.LocKey;
import org.vpda.common.service.localization.LocValueBuilder;
import org.vpda.common.service.localization.LocValueBuilderRegistry;
import org.vpda.common.service.localization.LocalizationService;
import org.vpda.common.service.localization.LocalizationServiceBridge;
import org.vpda.common.service.localization.LocalizationServiceImpl;
import org.vpda.common.service.localization.MapLocalizationValueResolver;
import org.vpda.common.service.localization.MemLocalizationServiceCacheImpl;
import org.vpda.common.service.localization.StringLocValueBuilder;

/**
 * @author kitko
 *
 */
public class LocalizationServiceBridgeTest {

    private LocalizationServiceBridge createTestee() throws FileSystemException {
        LocalizationServiceBridge localizationServiceBridge = new LocalizationServiceBridge(createPrimLocService(), createSecLocService(), new LocValueBuilderRegistry(),
                new MemLocalizationServiceCacheImpl());
        localizationServiceBridge.clearData();
        return localizationServiceBridge;
    }

    private LocalizationService createPrimLocService() throws FileSystemException {
        MapLocalizationValueResolver mapLocalizationValueResolver = new MapLocalizationValueResolver();
        mapLocalizationValueResolver.put(new LocKey("P1", "K1"), ServiceTesthelper.createTestContext(), "V1");
        return new LocalizationServiceImpl(mapLocalizationValueResolver, null, new MemLocalizationServiceCacheImpl());
    }

    private LocalizationService createSecLocService() throws FileSystemException {
        MapLocalizationValueResolver mapLocalizationValueResolver = new MapLocalizationValueResolver();
        mapLocalizationValueResolver.put(new LocKey("P2", "K2"), ServiceTesthelper.createTestContext(), "V2");
        return new LocalizationServiceImpl(mapLocalizationValueResolver, null, new MemLocalizationServiceCacheImpl());
    }

    /**
     * Test method for
     * {@link LocalizationServiceBridge#containsLocData(LocKey, ApplContext, LocValueBuilder)}
     * 
     * @throws FileSystemException
     */
    @Test
    public void testContainsLocMessage() throws FileSystemException {
        assertTrue(createTestee().containsLocData(new LocKey("P1", "K1"), ServiceTesthelper.createTestContext(), StringLocValueBuilder.getInstance()));
        assertTrue(createTestee().containsLocData(new LocKey("P2", "K2"), ServiceTesthelper.createTestContext(), StringLocValueBuilder.getInstance()));
    }

    /**
     * Test method for
     * 
     * @throws FileSystemException
     */
    @Test
    public void testLocalizeMessageStringStringApplContextString() throws FileSystemException {
        String value = createTestee().localizeMessage(new LocKey("P1", "K1"), ServiceTesthelper.createTestContext());
        assertEquals("V1", value, "Invalid loc value");
        value = createTestee().localizeMessage(new LocKey("P2", "K2"), ServiceTesthelper.createTestContext());
        assertEquals("V2", value, "Invalid loc value");
    }

}
