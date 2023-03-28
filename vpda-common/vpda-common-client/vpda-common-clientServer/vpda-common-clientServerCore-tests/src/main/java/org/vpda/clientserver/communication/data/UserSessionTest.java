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
package org.vpda.clientserver.communication.data;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.vpda.clientserver.ClientCoreTestHelper.createTestSession;

import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.vpda.internal.common.util.JavaSerializationUtil;

/**
 * Test for user session
 * 
 * @author kitko
 *
 */
public class UserSessionTest {

    /**
     * Test method
     */
    @Test
    public void testGetId() {
        assertEquals("test", createTestSession().getSessionId());
    }

    /**
     * Test method
     */
    @Test
    public void testGetLoginInfo() {
        assertNotNull(createTestSession().getLoginInfo());
    }

    /**
     * Test method
     */
    @Test
    public void testGetLoginTime() {
        assertNotNull(createTestSession().getLoginTime());
    }

    /**
     * Test method
     */
    @Test
    public void testGetUser() {
        assertNotNull(createTestSession().getUser());
    }

    /**
     * Test method
     */
    @Test
    public void testGetSettingsContext() {
        assertNotNull(createTestSession().getApplContext());
    }

    /**
     * Test size
     * 
     * @throws IOException
     */
    @Test
    public void testSize() throws IOException {
        UserSession session = createTestSession();
        int binarySize = JavaSerializationUtil.computeSize(session);
        assertTrue(binarySize < 10000);
    }

}
