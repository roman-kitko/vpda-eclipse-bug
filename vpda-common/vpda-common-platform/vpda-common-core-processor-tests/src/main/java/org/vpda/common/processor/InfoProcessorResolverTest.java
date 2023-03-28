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
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.vpda.common.ioc.objectresolver.EmptyObjectResolver;
import org.vpda.common.ioc.objectresolver.ObjectResolver;
import org.vpda.common.processor.ClassProcessor;
import org.vpda.common.processor.FieldBuilder;
import org.vpda.common.processor.ItemsClassProcessor;
import org.vpda.common.processor.ProcessorResolver;
import org.vpda.common.processor.annotation.FieldBuildingInfo;
import org.vpda.common.processor.annotation.ProcessingInfo;
import org.vpda.common.processor.annotation.TargetItemProcessingInfo;
import org.vpda.common.processor.annotation.FieldBuildingInfo.FieldBuildingInfos;
import org.vpda.common.processor.annotation.ProcessingInfo.ProcessingInfos;
import org.vpda.common.processor.annotation.TargetItemProcessingInfo.TargetItemProcessingInfos;
import org.vpda.common.processor.ctx.ClassContext;
import org.vpda.common.processor.ctx.ClassItemContext;
import org.vpda.common.processor.ctx.FieldContext;
import org.vpda.common.processor.impl.AbstractClassProcessor;
import org.vpda.common.processor.impl.AbstractFieldBuilder;
import org.vpda.common.processor.impl.AbstractItemsClassProcessor;
import org.vpda.common.processor.impl.InfoProcessorResolver;

/**
 * @author kitko
 *
 */
public class InfoProcessorResolverTest {

    /** */
    public static class MyProcessor extends AbstractClassProcessor<String> implements ClassProcessor<String> {
        @Override
        public String process(ClassContext<String> classContext, ProcessorResolver processorResolver, ObjectResolver context) {
            return "hello";
        }

    }

    /**
     * Test method
     */
    @Test
    public void testResolveTargetProcessor() {
        InfoProcessorResolver resolver = new InfoProcessorResolver();
        class A {
        }
        assertNull(resolver.resolveTargetProcessor(ClassContext.createRootClassContext(A.class, String.class), EmptyObjectResolver.getInstance()));
        @ProcessingInfo(processorClass = MyProcessor.class)
        class B {
        }
        assertNotNull(resolver.resolveTargetProcessor(ClassContext.createRootClassContext(B.class, String.class), EmptyObjectResolver.getInstance()));
        assertNull(resolver.resolveTargetProcessor(ClassContext.createRootClassContext(B.class, Integer.class), EmptyObjectResolver.getInstance()));
        assertNotNull(resolver.resolveTargetProcessor(ClassContext.createRootClassContext(B.class, Object.class), EmptyObjectResolver.getInstance()));
        @ProcessingInfo(processorClass = MyProcessor.class, targetClass = Integer.class)
        class C {
        }
        assertNull(resolver.resolveTargetProcessor(ClassContext.createRootClassContext(C.class, String.class), EmptyObjectResolver.getInstance()));
        assertNull(resolver.resolveTargetProcessor(ClassContext.createRootClassContext(C.class, Integer.class), EmptyObjectResolver.getInstance()));
        @ProcessingInfos({ @ProcessingInfo(processorClass = MyProcessor.class, targetClass = String.class), @ProcessingInfo(processorClass = ClassProcessor.class, targetClass = Integer.class) })
        class D {
        }
        assertNotNull(resolver.resolveTargetProcessor(ClassContext.createRootClassContext(D.class, String.class), EmptyObjectResolver.getInstance()));
        assertNull(resolver.resolveTargetProcessor(ClassContext.createRootClassContext(D.class, Integer.class), EmptyObjectResolver.getInstance()));
    }

    /** */
    public static class MyItemProcessor extends AbstractItemsClassProcessor<String> {
        @Override
        public List<String> process(ClassItemContext<String> classContext, ProcessorResolver processorResolver, ObjectResolver context) {
            return Arrays.asList("item");
        }
    }

    /**
     * Test method
     */
    @Test
    public void testResolveTargetItemsProcessor() {
        InfoProcessorResolver resolver = new InfoProcessorResolver();
        class A {
        }
        assertNull(resolver.resolveTargetItemsProcessor(ClassItemContext.createRootClassItemContext(A.class, String.class), EmptyObjectResolver.getInstance()));
        @TargetItemProcessingInfo(processorClass = MyItemProcessor.class)
        class B {
        }
        assertNotNull(resolver.resolveTargetItemsProcessor(ClassItemContext.createRootClassItemContext(B.class, String.class), EmptyObjectResolver.getInstance()));
        assertNull(resolver.resolveTargetItemsProcessor(ClassItemContext.createRootClassItemContext(B.class, Integer.class), EmptyObjectResolver.getInstance()));
        assertNotNull(resolver.resolveTargetItemsProcessor(ClassItemContext.createRootClassItemContext(B.class, Object.class), EmptyObjectResolver.getInstance()));
        @TargetItemProcessingInfo(processorClass = MyItemProcessor.class, targetItemClass = Integer.class)
        class C {
        }
        assertNull(resolver.resolveTargetItemsProcessor(ClassItemContext.createRootClassItemContext(C.class, String.class), EmptyObjectResolver.getInstance()));
        assertNull(resolver.resolveTargetItemsProcessor(ClassItemContext.createRootClassItemContext(C.class, Integer.class), EmptyObjectResolver.getInstance()));
        @TargetItemProcessingInfos({ @TargetItemProcessingInfo(processorClass = MyItemProcessor.class, targetItemClass = String.class),
                @TargetItemProcessingInfo(processorClass = ItemsClassProcessor.class, targetItemClass = Integer.class) })
        class D {
        }
        assertNull(resolver.resolveTargetItemsProcessor(ClassItemContext.createRootClassItemContext(D.class, String.class), EmptyObjectResolver.getInstance()));
    }

    /** */
    public static class MyFieldBuilder extends AbstractFieldBuilder<Object> implements FieldBuilder<Object> {

        @Override
        public boolean canBuild(FieldContext<?> ctx, ProcessorResolver resolver, ObjectResolver context) {
            Field field = ctx.getField();
            return field.getType().equals(String.class) || field.getType().equals(Integer.class);
        }

        @Override
        public Object build(FieldContext<Object> ctx, ProcessorResolver resolver, ObjectResolver context) {
            Field field = ctx.getField();
            if (field.getType().equals(String.class)) {
                return "hello";
            }
            if (field.getType().equals(Integer.class)) {
                return 1;
            }
            return null;
        }

        @Override
        public Class<? extends Object> getTargetClass() {
            return Object.class;
        }

    }

    /** */
    public static class MyFieldBuilder1 extends MyFieldBuilder {
    }

    /**
     * Test method
     * 
     * @throws NoSuchFieldException
     * @throws SecurityException
     */
    @Test
    public void testResolveTargetFieldBuilder() throws SecurityException, NoSuchFieldException {
        InfoProcessorResolver resolver = new InfoProcessorResolver();
        @SuppressWarnings("unused")
        class A {
            Integer i1;
            @FieldBuildingInfo(builderClass = MyFieldBuilder.class)
            Integer i2;
            @FieldBuildingInfo(builderClass = MyFieldBuilder.class, targetClass = Integer.class)
            Integer i3;
            @FieldBuildingInfos({ @FieldBuildingInfo(builderClass = MyFieldBuilder.class, targetClass = Integer.class),
                    @FieldBuildingInfo(builderClass = MyFieldBuilder1.class, targetClass = String.class) })
            Integer i4;

        }
        assertNull(resolver.resolveTargetFieldBuilder(FieldContext.createRootFieldContext(A.class.getDeclaredField("i1"), Integer.class), EmptyObjectResolver.getInstance()));
        assertNotNull(resolver.resolveTargetFieldBuilder(FieldContext.createRootFieldContext(A.class.getDeclaredField("i2"), Integer.class), EmptyObjectResolver.getInstance()));
        assertNotNull(resolver.resolveTargetFieldBuilder(FieldContext.createRootFieldContext(A.class.getDeclaredField("i2"), String.class), EmptyObjectResolver.getInstance()));
        assertNotNull(resolver.resolveTargetFieldBuilder(FieldContext.createRootFieldContext(A.class.getDeclaredField("i3"), Integer.class), EmptyObjectResolver.getInstance()));
        assertNull(resolver.resolveTargetFieldBuilder(FieldContext.createRootFieldContext(A.class.getDeclaredField("i3"), String.class), EmptyObjectResolver.getInstance()));
        assertEquals(MyFieldBuilder.class,
                resolver.resolveTargetFieldBuilder(FieldContext.createRootFieldContext(A.class.getDeclaredField("i4"), Integer.class), EmptyObjectResolver.getInstance()).getClass());
        assertEquals(MyFieldBuilder1.class,
                resolver.resolveTargetFieldBuilder(FieldContext.createRootFieldContext(A.class.getDeclaredField("i4"), String.class), EmptyObjectResolver.getInstance()).getClass());
    }

}
