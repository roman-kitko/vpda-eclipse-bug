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
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;

import org.easymock.EasyMock;
import org.junit.jupiter.api.Test;
import org.vpda.common.ioc.objectresolver.EmptyObjectResolver;
import org.vpda.common.ioc.objectresolver.ObjectResolver;
import org.vpda.common.processor.ClassProcessor;
import org.vpda.common.processor.ProcessingCache;
import org.vpda.common.processor.ProcessorResolver;
import org.vpda.common.processor.annotation.ProcessingInfo;
import org.vpda.common.processor.ctx.ClassContext;
import org.vpda.common.processor.impl.CachingClassProcessor;
import org.vpda.common.processor.impl.ChainedProcessorResolver;
import org.vpda.common.processor.impl.ProcessingCacheImpl;

/**
 * @author kitko
 *
 */
public class CachingProcessorTest {

    /**
     * Test
     */
    @Test
    public void testProcess() {
        ClassProcessor<String> delegate = new Delegate();
        ProcessingCache cache = new ProcessingCacheImpl();
        CachingClassProcessor<String> processor = new CachingClassProcessor<String>(delegate, cache);
        String o1 = processor.process(ClassContext.createRootClassContext(MyClassA.class, String.class), new ChainedProcessorResolver(), EmptyObjectResolver.getInstance());
        assertEquals(MyClassA.class.getSimpleName(), o1);
        String o2 = processor.process(ClassContext.createRootClassContext(MyClassA.class, String.class), new ChainedProcessorResolver(), EmptyObjectResolver.getInstance());
        assertSame(o1, o2);
        String o3 = processor.process(ClassContext.createRootClassContext(MyClassB.class, String.class), new ChainedProcessorResolver(), EmptyObjectResolver.getInstance());
        assertEquals(MyClassB.class.getSimpleName(), o3);
        String o4 = processor.process(ClassContext.createRootClassContext(MyClassB.class, String.class), new ChainedProcessorResolver(), EmptyObjectResolver.getInstance());
        assertNotSame(o3, o4);

    }

    @ProcessingInfo(cacheProcessedObject = true)
    private static final class MyClassA {

    }

    @ProcessingInfo(cacheProcessedObject = false)
    private static final class MyClassB {

    }

    private static final class Delegate implements ClassProcessor<String> {
        @Override
        public String process(ClassContext<String> classContext, ProcessorResolver processorResolver, ObjectResolver context) {
            return new String(classContext.getProcessedClass().getSimpleName());
        }

        @Override
        public Class<? extends String> getTargetClass() {
            return String.class;
        }

        @Override
        public boolean canProcess(ClassContext<?> classContext, ProcessorResolver processorResolver, ObjectResolver context) {
            return true;
        }
    }

    /**
     * Test method for
     * {@link org.vpda.common.processor.impl.CachingClassProcessor#getTargetClass()}.
     */
    @Test
    public void testGetTargetClass() {
        ClassProcessor delegate = EasyMock.createNiceMock(ClassProcessor.class);
        EasyMock.expect(delegate.getTargetClass()).andReturn(String.class).once();
        EasyMock.replay(delegate);
        ProcessingCache cache = EasyMock.createNiceMock(ProcessingCache.class);
        @SuppressWarnings("unchecked")
        CachingClassProcessor cp = new CachingClassProcessor(delegate, cache);
        assertEquals(String.class, cp.getTargetClass());
    }

}
