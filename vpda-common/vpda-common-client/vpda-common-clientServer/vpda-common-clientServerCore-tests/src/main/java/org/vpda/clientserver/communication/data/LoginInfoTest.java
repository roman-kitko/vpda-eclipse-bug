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
import static org.vpda.clientserver.ClientCoreTestHelper.createTestLoginInfo;

import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.vpda.common.context.ApplContext;
import org.vpda.common.context.ApplContextBuilder;
import org.vpda.common.context.TenementalContext;
import org.vpda.internal.common.util.JavaSerializationUtil;

/**
 * 
 * Test for LoginInfo
 * 
 * @author kitko
 *
 */
public class LoginInfoTest {

    /**
     * Test method for {@link LoginInfo#getApplContext()}
     * 
     * @throws IOException
     */
    @Test
    public void testApplContext() throws IOException {
        LoginInfo loginInfo = createTestLoginInfo().createLoginInfo();
        ApplContext applContext = new ApplContextBuilder().build();
        TenementalContext context = TenementalContext.create(applContext);
        loginInfo = new LoginInfo.LoginInfoBuilder().setValues(loginInfo).setContext(context).build();
        assertEquals(applContext, loginInfo.getApplContext());
        assertEquals(context, loginInfo.getContext());
    }

    /**
     * Test size
     * 
     * @throws IOException
     */
    @Test
    public void testSize() throws IOException {
        LoginInfo loginInfo = createTestLoginInfo().createLoginInfo();
        int binarySize = JavaSerializationUtil.computeSize(loginInfo);
        assertTrue(binarySize < 10000);
    }

    /**
     * Test method
     */
    @Test
    public void testGetConnectionInfo() {
        assertNotNull(createTestLoginInfo().getConnectionInfo());
    }

    /**
     * Test method
     */
    @Test
    public void testGetInetAddress() {
        assertNotNull(createTestLoginInfo().getInetAddress());
    }

    /**
     * Test method
     */
    @Test
    public void testGetLocale() {
        LoginInfo loginInfo = createTestLoginInfo().createLoginInfo();
        assertNotNull(loginInfo.getLocale());
    }

    /**
     * Test method
     */
    @Test
    public void testGetLogin() {
        assertNotNull(createTestLoginInfo().getAuthenticationEntry());
    }

}
