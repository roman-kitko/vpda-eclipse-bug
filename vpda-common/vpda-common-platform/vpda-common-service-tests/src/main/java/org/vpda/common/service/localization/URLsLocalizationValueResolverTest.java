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
/**
 * 
 */
package org.vpda.common.service.localization;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;

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
public class URLsLocalizationValueResolverTest {

    /**
     * Test
     * 
     * @throws MalformedURLException
     */
    @Test
    public void testResolveValue() throws MalformedURLException {
        TenementalContext ctx = ServiceTesthelper.createTestContext();
        URLsLocalizationValueResolver testee = createTestee();
        @SuppressWarnings("unused")
        LocalizationService ls = new LocalizationServiceImpl(testee, new LocValueBuilderRegistry(), new MemLocalizationServiceCacheImpl());
        String resolvedValue = testee.resolveValue(new LocKey("common/service/localization/testloc/ui/common/common/testButton"), ctx);
        assertEquals("Test button", resolvedValue);
        resolvedValue = testee.resolveValue(new LocKey("common/service/localization/testloc/ui/common/common/testButton/icon"), ctx);
        assertEquals("testIcon.gif", resolvedValue);
    }

    private URLsLocalizationValueResolver createTestee() throws MalformedURLException {
        String res = URLsLocalizationValueResolverTest.class.getName().replace('.', '/') + ".class";
        URL url1 = URLsLocalizationValueResolverTest.class.getResource("/" + res);
        URL url = new URL(url1.toExternalForm().substring(0, url1.toExternalForm().length() - res.length()) + "org/vpda/");
        return new URLsLocalizationValueResolver(Collections.singletonList(url), new MemURLsLocalizationValueResolverCacheImpl());
    }

}
