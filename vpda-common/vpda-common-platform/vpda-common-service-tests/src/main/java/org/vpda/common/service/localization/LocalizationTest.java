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
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;

import java.net.URL;
import java.util.Collections;
import java.util.Locale;

import org.apache.commons.vfs2.FileSystemException;
import org.junit.jupiter.api.Test;
import org.vpda.common.context.TenementalContext;
import org.vpda.common.context.localization.LocKey;
import org.vpda.common.service.localization.LocValueBuilderRegistry;
import org.vpda.common.service.localization.LocalizationService;
import org.vpda.common.service.localization.LocalizationServiceImpl;
import org.vpda.common.service.localization.MemLocalizationServiceCacheImpl;
import org.vpda.common.service.localization.MemURLsLocalizationValueResolverCacheImpl;
import org.vpda.common.service.localization.URLsLocalizationValueResolver;

/**
 * @author kitko
 *
 */
public class LocalizationTest {

    /**
     * Base localizatin test
     * 
     * @throws FileSystemException
     */
    @Test
    public void testLocalization() throws FileSystemException {
        LocalizationService locService = createLocService();
        assertNotNull(locService);
        // Context EN
        String path = "ui/details/common/locTest";
        TenementalContext contextEN = ServiceTesthelper.createTestContext();

        assertEquals("User", locService.localizeMessage(new LocKey(path, "user"), contextEN), "Invalid value for user");
        assertEquals("Password", locService.localizeMessage(new LocKey(path, "password"), contextEN), "Invalid value for password");
        assertEquals("View provider driven applications", locService.localizeMessage(new LocKey(path, "vpda"), contextEN), "Invalid value for vpda");
        assertSame(locService.localizeMessage(new LocKey(path, "user"), contextEN), locService.localizeMessage(new LocKey(path, "user"), contextEN),
                "Another attempt must return same instance because of performance");

        // Context SK
        TenementalContext contextSK = contextEN.createWithLocale(new Locale("sk"));
        assertEquals("Užívateľ", locService.localizeMessage(new LocKey(path, "user"), contextSK), "Invalid value for user");
        assertEquals("Heslo", locService.localizeMessage(new LocKey(path, "password"), contextSK), "Invalid value for password");
        assertEquals("Klient riadeny server pohladmi", locService.localizeMessage(new LocKey(path, "vpda"), contextSK), "Invalid value for vpda");
        assertSame(locService.localizeMessage(new LocKey(path, "user"), contextSK), locService.localizeMessage(new LocKey(path, "user"), contextSK),
                "Another attempt must return same instance because of performance");

    }

    private LocalizationService createLocService() throws FileSystemException {
        URL url = LocalizationTest.class.getResource("testloc/");
        URLsLocalizationValueResolver uRLsLocalizationValueResolver = new URLsLocalizationValueResolver(Collections.singletonList(url), new MemURLsLocalizationValueResolverCacheImpl());
        return new LocalizationServiceImpl(uRLsLocalizationValueResolver, new LocValueBuilderRegistry(), new MemLocalizationServiceCacheImpl());
    }

}
