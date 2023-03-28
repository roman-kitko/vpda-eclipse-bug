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
package org.vpda.common.entrypoint;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.net.URL;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.picocontainer.PicoContainer;
import org.picocontainer.script.ContainerBuilder;
import org.vpda.common.entrypoint.AppLauncherHelper;
import org.vpda.common.entrypoint.ModulesConfigurationFactory;
import org.vpda.internal.common.util.VersionUtil;

/**
 * @author kitko
 *
 */
public class AppLauncherHelperTest {

    /**
     * Test method
     * 
     * @throws IOException
     */
    @Test
    public void testFindSPIResourceURLs() throws IOException {
        List<URL> urls = AppLauncherHelper.getSPIResourceURLs(ModulesConfigurationFactory.class, getClass().getClassLoader());
        assertNotNull(urls);
        assertTrue(!urls.isEmpty(), "Returned list of resource urls should not be empty");

    }

    /**
     * Test method for
     * {@link org.vpda.common.entrypoint.AppLauncherHelper#loadXMLPicoContainer(java.lang.String, java.lang.ClassLoader, org.picocontainer.PicoContainer)}.
     */
    @Test
    public void testLoadXMLPicoContainer() {
        PicoContainer container = AppLauncherHelper.loadXMLPicoContainer("resource:data/picoApp.xml", getClass().getClassLoader(), null);
        assertNotNull(container);
        ModulesConfigurationFactory mcf = container.getComponent(ModulesConfigurationFactory.class);
        assertTrue(mcf instanceof TestModulesConfigurationFactory, "Returned ModulesConfigurationFactory should be TestModulesConfigurationFactory");
        URL url = getClass().getClassLoader().getResource("data/picoApp.xml");
        assertNotNull(url);
        container = AppLauncherHelper.loadXMLPicoContainer("url:" + url.toExternalForm(), getClass().getClassLoader(), null);
        assertNotNull(container);
        mcf = container.getComponent(ModulesConfigurationFactory.class);
        assertTrue(mcf instanceof TestModulesConfigurationFactory, "Returned ModulesConfigurationFactory should be TestModulesConfigurationFactory");
    }

    /**
     * Test method for
     * {@link org.vpda.common.entrypoint.AppLauncherHelper#getApplicationContainerBuilderLikeSPI(java.lang.String, java.lang.ClassLoader)}.
     * 
     * @throws Exception
     */
    @Test
    public void testFindApplicationContainerBuilderLikeSPI() throws Exception {
        ContainerBuilder containerBuilder = AppLauncherHelper.getApplicationContainerBuilderLikeSPI("testBuilder", getClass().getClassLoader());
        assertNotNull(containerBuilder);
    }

    /**
     * Test method for
     * {@link org.vpda.common.entrypoint.AppLauncherHelper#loadApplicationContainerLikeSPI(java.lang.String, java.lang.String, java.lang.ClassLoader, org.picocontainer.PicoContainer)}.
     * 
     * @throws Exception
     */
    @Test
    public void testFindApplicationContainer() throws Exception {
        PicoContainer container = AppLauncherHelper.loadApplicationContainerLikeSPI("testBuilder", "unknown", getClass().getClassLoader(), null);
        assertNotNull(container);
        assertEquals("testValue", container.getComponent("testKey"));
        container = AppLauncherHelper.loadApplicationContainerLikeSPI("unknown", "testApp", getClass().getClassLoader(), null);
        assertNotNull(container);
        ModulesConfigurationFactory mcf = container.getComponent(ModulesConfigurationFactory.class);
        assertTrue(mcf instanceof TestModulesConfigurationFactory, "Returned ModulesConfigurationFactory should be TestModulesConfigurationFactory");
    }

    /**
     * Test getVpdaVersion
     */
    @Test
    public void testGetVpdaVersion() {
        String vpdaVersion = VersionUtil.getVpdaVersion();
        assertNotNull(vpdaVersion);
    }

}
