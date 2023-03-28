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
package org.vpda.common.entrypoint;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.net.URL;
import java.util.Collections;

import org.apache.commons.vfs2.FileSystemException;
import org.apache.commons.vfs2.VFS;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.vpda.common.entrypoint.MuttableApplication;

/**
 * @author kitko
 *
 */
public abstract class AbstractProjectTest {
    private static MuttableApplication project;

    /**
     * Creates project
     */
    @BeforeEach
    public void setup() {
        if (project == null) {
            project = createProject();
        }
    }

    /**
     * Creates project
     * 
     * @return created project
     */
    protected abstract MuttableApplication createProject();

    /**
     * Test
     */
    @Test
    public void testGetName() {
        assertNotNull(project.getName());
    }

    /**
     * Test
     */
    @Test
    public void testRegisterModule() {
        Module module = new TestModule();
        project.registerModule(module);
        assertSame(module, project.getModule(module.getName()));
        assertEquals(Collections.singletonList(module), project.getModules());
    }

    /**
     * Test project home
     * 
     * @throws FileSystemException
     */
    @Test
    public void testGetProjectHome() throws FileSystemException {
        URL url = project.getApplicationHomeURL();
        assertNotNull(url, "Must have project url");
        assertTrue(VFS.getManager().resolveFile(url).exists(), "Url : " + url + " must exist");
    }

}
