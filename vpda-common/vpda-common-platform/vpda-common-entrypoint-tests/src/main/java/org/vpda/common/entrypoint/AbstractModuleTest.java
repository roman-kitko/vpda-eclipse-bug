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

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * @author kitko
 *
 */
public abstract class AbstractModuleTest {

    private static Module module;

    /**
     * creates module
     */
    @BeforeEach
    public void setup() {
        if (module == null) {
            module = createModule();
        }
    }

    /**
     * @return module
     */
    protected abstract Module createModule();

    /**
     * Test method for
     * {@link org.vpda.common.entrypoint.AbstractModule#getName()}.
     */
    @Test
    public void testGetName() {
        assertNotNull(module.getName());
    }

    /**
     * Test method for
     * {@link org.vpda.common.entrypoint.AbstractModule#getModuleHomeURL()}.
     * 
     * @throws IOException
     */
    @Test
    public void testGetModuleHome() throws IOException {
        assertNotNull(module.getModuleHomeURL());
    }
}
