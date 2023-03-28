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
package org.vpda.common.command.executor.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;

import java.util.Arrays;
import java.util.HashSet;

import org.junit.jupiter.api.Test;
import org.vpda.common.command.executor.impl.CommandExecutorBase;
import org.vpda.common.command.executor.impl.CommandExecutorRegistryImpl;

/**
 * @author kitko
 *
 */
public class CommandExecutorRegistryImplTest {

    /**
     * Test method for
     * {@link org.vpda.common.command.executor.impl.CommandExecutorRegistryImpl#getRegisteredCommandExecutor(java.lang.String)}.
     */
    @Test
    public final void testRegisterCommandExecutor() {
        CommandExecutorRegistryImpl registryImpl = new CommandExecutorRegistryImpl();
        CommandExecutorBase exc1 = new CommandExecutorBase("exc1");
        CommandExecutorBase exc2 = new CommandExecutorBase("exc2");
        registryImpl.registerCommandExecutor(exc1);
        registryImpl.registerCommandExecutor("exc22", exc2);
        assertSame(exc1, registryImpl.getRegisteredCommandExecutor("exc1"));
        assertSame(exc2, registryImpl.getRegisteredCommandExecutor("exc22"));
        assertNull(registryImpl.getRegisteredCommandExecutor("exc2"));
        assertEquals(new HashSet<String>(Arrays.asList("exc1", "exc22")), new HashSet<String>(registryImpl.getExecutorsIds()));
    }

}
