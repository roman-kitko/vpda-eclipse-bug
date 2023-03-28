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
package org.vpda.abstractclient.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Locale;

import org.junit.jupiter.api.Test;
import org.vpda.abstractclient.core.profile.AbstractLoginProfile;
import org.vpda.abstractclient.core.profile.LoginSettingsDataHelper;
import org.vpda.abstractclient.core.profile.impl.DefaultLoginProfile;
import org.vpda.clientserver.communication.BasicCommunicationProtocol;

/**
 * Test for {@link AbstractLoginProfile}
 * 
 * @author kitko
 *
 */
public class LoginSettingsDataTest {

    /**
     * @throws FileNotFoundException
     * @throws IOException
     */
    @Test
    public void testReadWrite() throws FileNotFoundException, IOException {

        Locale locale = new Locale("en", "US");
        String user = "user";
        String bindingName = "bindName";
        Integer port = 1234;
        String server = "localhost";
        BasicCommunicationProtocol protocol = BasicCommunicationProtocol.RMI;

        DefaultLoginProfile.Builder builder = new DefaultLoginProfile.Builder();
        builder.setProfileName("test");
        builder.setLocale(locale);
        builder.setUser(user);
        builder.setBindingName(bindingName);
        builder.setPort(port);
        builder.setServer("myServer");
        builder.setServer(server);
        builder.setCommunicationProtocol(protocol);
        AbstractLoginProfile profile = builder.build();

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        new LoginSettingsDataHelper().storeToStream(profile, bos);
        profile = new LoginSettingsDataHelper().loadFromStream(new ByteArrayInputStream(bos.toByteArray()));
        assertNotNull(builder);
        assertEquals(locale, builder.getLocale());
        assertEquals(user, builder.getUser());
        assertEquals(bindingName, builder.getBindingName());
        assertEquals(port, builder.getPort());
        assertEquals(server, builder.getServer());
        assertEquals(protocol, builder.getCommunicationProtocol());
    }

}
