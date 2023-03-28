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
package org.vpda.common.processor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.vpda.common.ioc.objectresolver.EmptyObjectResolver;
import org.vpda.common.ioc.objectresolver.ObjectResolver;
import org.vpda.common.processor.ProcessorResolver;
import org.vpda.common.processor.ctx.ClassContext;
import org.vpda.common.processor.impl.AbstractClassProcessor;

/**
 * @author kitko
 *
 */
public class AbstractClassProcessorTest {

    /**
     * Test
     */
    @Test
    public void testCanProcess() {
        assertTrue(new MyClassProcessor().canProcess(ClassContext.createRootClassContext(A.class, String.class), null, EmptyObjectResolver.getInstance()));
        assertFalse(new MyClassProcessor().canProcess(ClassContext.createRootClassContext(A.class, Integer.class), null, EmptyObjectResolver.getInstance()));
    }

    /**
     * Test method for
     * {@link org.vpda.common.processor.impl.AbstractClassProcessor#getTargetClass()}.
     */
    @Test
    public void testGetTargetClass() {
        assertEquals(String.class, new MyClassProcessor().getTargetClass());
    }

    private static final class MyClassProcessor extends AbstractClassProcessor<String> {
        @Override
        public String process(ClassContext<String> classContext, ProcessorResolver processorResolver, ObjectResolver context) {
            return "hello";
        }
    }

    private static final class A {
    }
}
