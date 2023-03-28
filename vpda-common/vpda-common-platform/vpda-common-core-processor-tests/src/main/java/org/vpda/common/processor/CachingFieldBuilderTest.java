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
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.vpda.common.ioc.objectresolver.ObjectResolver;
import org.vpda.common.processor.ProcessorResolver;
import org.vpda.common.processor.ctx.FieldContext;
import org.vpda.common.processor.impl.AbstractFieldBuilder;
import org.vpda.common.processor.impl.CachingFieldBuilder;
import org.vpda.common.processor.impl.ProcessingCacheImpl;

/**
 * @author kitko
 *
 */
public class CachingFieldBuilderTest {
    private static final class Delegate extends AbstractFieldBuilder<String> {

        @Override
        public String build(FieldContext<String> fieldContext, ProcessorResolver resolver, ObjectResolver context) {
            return new String("string1");
        }

        @Override
        public Class<? extends String> getTargetClass() {
            return String.class;
        }

    }

    private static final class C {
        @SuppressWarnings("unused")
        String f1;
    }

    private CachingFieldBuilder<String> createTestee() {
        return new CachingFieldBuilder<String>(new Delegate(), new ProcessingCacheImpl());
    }

    /**
     * Test method for
     * {@link org.vpda.common.processor.impl.CachingFieldBuilder#canBuild(org.vpda.common.processor.ctx.FieldContext, org.vpda.common.processor.ProcessorResolver, org.vpda.common.ioc.objectresolver.ObjectResolver)}.
     * 
     * @throws SecurityException
     * @throws NoSuchFieldException
     */
    @Test
    public void testCanBuild() throws NoSuchFieldException, SecurityException {
        CachingFieldBuilder<String> testee = createTestee();
        assertTrue(testee.canBuild(FieldContext.createRootFieldContext(C.class.getDeclaredField("f1"), String.class), null, null));
        assertFalse(testee.canBuild(FieldContext.createRootFieldContext(C.class.getDeclaredField("f1"), Long.class), null, null));
    }

    /**
     * Test method for
     * {@link org.vpda.common.processor.impl.CachingFieldBuilder#build(org.vpda.common.processor.ctx.FieldContext, org.vpda.common.processor.ProcessorResolver, org.vpda.common.ioc.objectresolver.ObjectResolver)}.
     * 
     * @throws SecurityException
     * @throws NoSuchFieldException
     */
    @Test
    public void testBuildFieldContextOfTProcessorResolverObjectResolver() throws NoSuchFieldException, SecurityException {
        CachingFieldBuilder<String> testee = createTestee();
        String s1 = testee.build(FieldContext.createRootFieldContext(C.class.getDeclaredField("f1"), String.class), null, null);
        assertEquals("string1", s1);
        String s2 = testee.build(FieldContext.createRootFieldContext(C.class.getDeclaredField("f1"), String.class), null, null);
        assertSame(s1, s2);
    }

    /**
     * Test method for
     * {@link org.vpda.common.processor.impl.CachingFieldBuilder#getTargetClass()}.
     */
    @Test
    public void testGetTargetClass() {
        CachingFieldBuilder<String> testee = createTestee();
        assertEquals(String.class, testee.getTargetClass());
    }

}
