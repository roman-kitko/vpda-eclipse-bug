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
import static org.junit.jupiter.api.Assertions.assertNull;

import java.io.IOException;

import org.easymock.EasyMock;
import org.junit.jupiter.api.Test;
import org.vpda.common.context.ApplContext;
import org.vpda.common.context.TenementalContext;
import org.vpda.common.context.localization.LocKey;
import org.vpda.common.service.localization.LocPair;
import org.vpda.common.service.localization.LocalizationService;
import org.vpda.common.service.localization.StringLocValue;
import org.vpda.common.service.localization.StringLocValueBuilder;
import org.vpda.internal.common.util.JavaSerializationUtil;

/**
 * @author kitko
 *
 */
public class LocPairTest {

    /**
     * Test method for {@link LocPair#getKeyPart()}.
     */
    @Test
    public void testGetLocKey() {
        assertEquals("testKey", createTestee().getKeyPart());
    }

    /**
     * Test method for {@link LocPair#getPathPart()}.
     */
    @Test
    public void testGetLocPath() {
        assertEquals("testPath/testKey", createTestee().getPathPart());
    }

    /**
     * Test method for {@link LocPair#getLocValue()}
     */
    @Test
    public void testGetLocValue() {
        assertNull(createTestee().getLocValue());
    }

    /**
     * Test method for {@link LocPair#localize(LocalizationService, ApplContext)}
     */
    @Test
    public void testLocalize() {
        TenementalContext settingsApplContext = ServiceTesthelper.createTestContext();
        LocPair<StringLocValue> pair = createTestee();
        LocalizationService locService = EasyMock.createMock(LocalizationService.class);
        LocKey locKey = new LocKey("testPath", "testKey");
        StringLocValue locValue = new StringLocValue("localizedValue");
        EasyMock.expect(locService.localizeMessage(locKey, settingsApplContext)).andReturn("localizedValue");
        EasyMock.expect(locService.localizeData(locKey, settingsApplContext, StringLocValueBuilder.getInstance(), null, null)).andReturn(locValue).anyTimes();
        EasyMock.replay(locService);
        pair = pair.localize(locService, settingsApplContext);
        EasyMock.verify();
        assertEquals(locValue, pair.getLocValue());
    }

    private LocPair<StringLocValue> createTestee() {
        return LocPair.createStringLocPair("testPath", "testKey");
    }

    /**
     * Test object serialization
     * 
     * @throws IllegalArgumentException
     * @throws IOException
     * @throws ClassNotFoundException
     * @throws IllegalAccessException
     */
    @Test
    public void testSerialization() throws IllegalArgumentException, IOException, ClassNotFoundException, IllegalAccessException {
        JavaSerializationUtil.testObjectSerialization(createTestee());
    }

}
