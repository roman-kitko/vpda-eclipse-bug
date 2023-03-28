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
package org.vpda.clientserver;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Locale;

import org.vpda.clientserver.clientcommunication.ClientConnectionInfo;
import org.vpda.clientserver.clientcommunication.ClientLoginInfo;
import org.vpda.clientserver.communication.BasicCommunicationKind;
import org.vpda.clientserver.communication.BasicCommunicationProtocol;
import org.vpda.clientserver.communication.CommunicationId;
import org.vpda.clientserver.communication.TestClientCommunicationSettings;
import org.vpda.clientserver.communication.data.ClientDeploymentPlatform;
import org.vpda.clientserver.communication.data.ClientPlatform;
import org.vpda.clientserver.communication.data.LoginInfo;
import org.vpda.clientserver.communication.data.UserAndPasswordAuthenticationEntry;
import org.vpda.clientserver.communication.data.UserSession;
import org.vpda.common.context.ApplContext;
import org.vpda.common.context.ApplContextBuilder;
import org.vpda.common.context.ApplContextFactory;
import org.vpda.common.context.TenementalContext;
import org.vpda.common.context.User;
import org.vpda.common.util.ComponentInfo;

/**
 * Test Helper
 * 
 * @author kitko
 *
 */
public class ClientCoreTestHelper {
    private ClientCoreTestHelper() {
    }

    /**
     * 
     * @return test {@link LoginInfo}
     */
    public static ClientLoginInfo createTestLoginInfo() {
        ApplContext applContext = new ApplContextBuilder().setId(0).build();
        ClientLoginInfo loginInfo = null;
        try {
            loginInfo = new ClientLoginInfo.LoginInfoBuilder().setContext(TenementalContext.create(applContext)).setConnectionInfo(createTestConnectionInfo())
                    .setInetAddress(InetAddress.getLocalHost()).setAuthenticationEntry(new UserAndPasswordAuthenticationEntry("system", "system".toCharArray()))
                    .setClientInfo(new ComponentInfo.ComponentInfoBuilder().build()).build();
        }
        catch (UnknownHostException e) {
        }
        return loginInfo;
    }

    /**
     * 
     * @return test {@link ClientConnectionInfo}
     */
    public static ClientConnectionInfo createTestConnectionInfo() {
        CommunicationId commId = new CommunicationId(BasicCommunicationProtocol.EMBEDDED, BasicCommunicationKind.CLIENT_SERVER_COMMUNICATION, "default");
        return new ClientConnectionInfo.ConnectionInfoBuilder().setCommunicationId(commId).setClientPlatform(ClientPlatform.CONSOLE).setClientDeploymentPlatform(ClientDeploymentPlatform.OFFLINE)
                .setClientCommunicationSettings(new TestClientCommunicationSettings()).build();
    }

    /**
     * 
     * @return test {@link UserSession}
     */
    public static UserSession createTestSession() {
        UserSession testSession = new UserSession.Builder().setSessionId("test").setUser(createTestUser()).setLoginInfo(createTestLoginInfo().createLoginInfo())
                .setLoginTime(System.currentTimeMillis()).build();
        return testSession;
    }

    /**
     * @return test ApplContext
     */
    public static ApplContext createTestApplContext() {
        return new ApplContextBuilder().setId(2).setCode("Test").build();
    }

    /**
     * 
     * @return test user
     */
    public static User createTestUser() {
        return new User.UserBuilder().setLogin("test").build();
    }

    /**
     * @return CommunicationId
     */
    public static CommunicationId createCommunicationId() {
        return new CommunicationId(BasicCommunicationProtocol.RMI, BasicCommunicationKind.CLIENT_SERVER_COMMUNICATION, "default");
    }

}