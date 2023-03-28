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
package org.vpda.client.clientcommunication.protocol.rmi;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.picocontainer.PicoContainer;
import org.picocontainer.script.xml.XMLContainerBuilder;
import org.vpda.clientserver.communication.protocol.rmi.RMIClientServerConstants;

/**
 * @author kitko
 *
 */
public class RMIClientCommunicationSettingsTest {

    /**
     * Test method for
     * {@link org.vpda.client.clientcommunication.protocol.rmi.RMIClientCommunicationSettings#RMIClientCommunicationSettings()}.
     */
    @Test
    public void testRMIClientCommunicationSettings() {
        RMIClientCommunicationSettings settings = new RMIClientCommunicationSettings();
        assertEquals(RMIClientServerConstants.LOGIN_SERVER_BINDING_NAME, settings.getServerBindingName());
        assertEquals(RMIClientServerConstants.RMI_PORT, settings.getPort().intValue());
        assertEquals(RMIClientServerConstants.LOCALHOST, settings.getHost());
    }

    /**
     * Test method for
     * {@link org.vpda.client.clientcommunication.protocol.rmi.RMIClientCommunicationSettings#RMIClientCommunicationSettings(java.lang.String, int, java.lang.String)}.
     */
    @Test
    public void testRMIClientCommunicationSettingsStringStringInt() {
        RMIClientCommunicationSettings settings = new RMIClientCommunicationSettings("myHost", 666, "myBindingName");
        assertEquals("myBindingName", settings.getServerBindingName());
        assertEquals(666, settings.getPort().intValue());
        assertEquals("myHost", settings.getHost());
    }

    /** Test loading settings from xml using picobuilder */
    @Test
    public void testLoadFromXMl() {
        XMLContainerBuilder builder = new XMLContainerBuilder(getClass().getResource("rMIClientCommunicationSettings.xml"), getClass().getClassLoader());
        PicoContainer pico = builder.buildContainer(null, "test", false);
        RMIClientCommunicationSettings settings = pico.getComponent(RMIClientCommunicationSettings.class);
        assertNotNull(settings);
        assertEquals("myBindingName", settings.getServerBindingName());
        assertEquals(888, settings.getPort().intValue());
        assertEquals("myHost", settings.getHost());

    }

    /** Test loading settings from xml using picobuilder */
    @Test
    public void testLoadFromXMlUsingBuilder() {
        XMLContainerBuilder builder = new XMLContainerBuilder(getClass().getResource("rMIClientCommunicationSettingsWithBuilder.xml"), getClass().getClassLoader());
        PicoContainer pico = builder.buildContainer(null, "test", false);
        RMIClientCommunicationSettings settings = pico.getComponent(RMIClientCommunicationSettings.class);
        assertNotNull(settings);
        assertEquals("MyBinding1", settings.getServerBindingName());
        assertEquals(777, settings.getPort().intValue());
        assertEquals("myHost", settings.getHost());

    }

}
