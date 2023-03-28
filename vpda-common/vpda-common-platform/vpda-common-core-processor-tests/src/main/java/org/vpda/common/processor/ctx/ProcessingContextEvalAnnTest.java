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
package org.vpda.common.processor.ctx;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;

import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.junit.jupiter.api.Test;
import org.vpda.common.ioc.objectresolver.EmptyObjectResolver;
import org.vpda.common.processor.annotation.GroupFiltering;
import org.vpda.common.processor.annotation.GroupFilteringMethod;
import org.vpda.common.processor.annotation.ProcessingInfo;
import org.vpda.common.processor.annotation.eval.ProcessingContextEvalAnn;
import org.vpda.common.processor.ctx.ClassContext;
import org.vpda.common.processor.ctx.DefaultGroup;
import org.vpda.common.processor.ctx.ProcessingContextEvalAnnTest.MyAnnotation4.MyAnnotation4s;

@SuppressWarnings("javadoc")
public class ProcessingContextEvalAnnTest {

    public interface Group1 {
    }

    public interface Group2 {
    }

    public interface Group3 {
    }

    public interface Group4 extends Group1 {
    }

    @ProcessingInfo(processGroups = { Group1.class, Group3.class }, targetClass = Number.class)
    public static class MyClass1 {
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target({ ElementType.TYPE, ElementType.FIELD })
    public static @interface MyAnnotation2 {
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target({ ElementType.TYPE, ElementType.FIELD })
    @GroupFiltering
    public static @interface MyAnnotation3 {
        @GroupFilteringMethod
        Class[] buildGroups() default {};
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target({ ElementType.TYPE, ElementType.FIELD })
    @GroupFiltering
    @Repeatable(MyAnnotation4s.class)
    public static @interface MyAnnotation4 {
        int myInt() default 1;

        @GroupFilteringMethod
        Class[] buildGroups() default {};

        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.TYPE)
        public @interface MyAnnotation4s {
            /** List of infos */
            MyAnnotation4[] value() default {};
        }
    }

    @MyAnnotation2
    public static class MyClass2 {
    }

    @MyAnnotation3
    public static class MyClass3 {
    }

    @MyAnnotation3(buildGroups = { Group2.class })
    public static class MyClass4 {
    }

    @MyAnnotation4(myInt = 1)
    @MyAnnotation4(buildGroups = { Group1.class }, myInt = 2)
    @MyAnnotation4(buildGroups = { Group2.class }, myInt = 3)
    public static class MyClass5 {
    }

    @Test
    public void testEvalAnnotation1() {
        assertNotNull(ProcessingContextEvalAnn.evalAnnotation(ProcessingInfo.class, EmptyObjectResolver.getInstance(), ClassContext.createRootClassContext(MyClass1.class, Number.class, Group1.class),
                a -> a.processGroups(), a -> a.targetClass()));
        assertNull(ProcessingContextEvalAnn.evalAnnotation(ProcessingInfo.class, EmptyObjectResolver.getInstance(), ClassContext.createRootClassContext(MyClass1.class, Integer.class, Group1.class),
                a -> a.processGroups(), a -> a.targetClass()));
        assertNull(ProcessingContextEvalAnn.evalAnnotation(ProcessingInfo.class, EmptyObjectResolver.getInstance(), ClassContext.createRootClassContext(MyClass1.class, Number.class, Group2.class),
                a -> a.processGroups(), a -> a.targetClass()));
        assertNotNull(ProcessingContextEvalAnn.evalAnnotation(ProcessingInfo.class, EmptyObjectResolver.getInstance(), ClassContext.createRootClassContext(MyClass1.class, Number.class, Group4.class),
                a -> a.processGroups(), a -> a.targetClass()));
    }

    @Test
    public void testEvalAnnotation2() {
        assertNotNull(ProcessingContextEvalAnn.evalAnnotation(MyClass1.class, ProcessingInfo.class, EmptyObjectResolver.getInstance(),
                ClassContext.createRootClassContext(MyClass1.class, Number.class, Group1.class), a -> a.processGroups(), a -> a.targetClass()));
        assertNull(ProcessingContextEvalAnn.evalAnnotation(MyClass1.class, ProcessingInfo.class, EmptyObjectResolver.getInstance(),
                ClassContext.createRootClassContext(MyClass1.class, Integer.class, Group1.class), a -> a.processGroups(), a -> a.targetClass()));
        assertNull(ProcessingContextEvalAnn.evalAnnotation(MyClass1.class, ProcessingInfo.class, EmptyObjectResolver.getInstance(),
                ClassContext.createRootClassContext(MyClass1.class, Number.class, Group2.class), a -> a.processGroups(), a -> a.targetClass()));
        assertNotNull(ProcessingContextEvalAnn.evalAnnotation(MyClass1.class, ProcessingInfo.class, EmptyObjectResolver.getInstance(),
                ClassContext.createRootClassContext(MyClass1.class, Number.class, Group4.class), a -> a.processGroups(), a -> a.targetClass()));

    }

    @Test
    public void testAnnotationReflective() {
        assertSame(MyClass2.class.getAnnotation(MyAnnotation2.class),
                ProcessingContextEvalAnn.evalAnnotation(MyAnnotation2.class, EmptyObjectResolver.getInstance(), ClassContext.createRootClassContext(MyClass2.class, Object.class)));
        assertNull(ProcessingContextEvalAnn.evalAnnotation(MyAnnotation3.class, EmptyObjectResolver.getInstance(), ClassContext.createRootClassContext(MyClass2.class, Object.class)));
        assertSame(MyClass3.class.getAnnotation(MyAnnotation3.class),
                ProcessingContextEvalAnn.evalAnnotation(MyAnnotation3.class, EmptyObjectResolver.getInstance(), ClassContext.createRootClassContext(MyClass3.class, Object.class)));
        assertNull(ProcessingContextEvalAnn.evalAnnotation(MyAnnotation3.class, EmptyObjectResolver.getInstance(), ClassContext.createRootClassContext(MyClass3.class, Object.class, Group1.class)));
        assertSame(MyClass3.class.getAnnotation(MyAnnotation3.class),
                ProcessingContextEvalAnn.evalAnnotation(MyAnnotation3.class, EmptyObjectResolver.getInstance(), ClassContext.createRootClassContext(MyClass3.class, Object.class, DefaultGroup.class)));
        assertSame(MyClass4.class.getAnnotation(MyAnnotation3.class),
                ProcessingContextEvalAnn.evalAnnotation(MyAnnotation3.class, EmptyObjectResolver.getInstance(), ClassContext.createRootClassContext(MyClass4.class, Object.class, Group2.class)));
        assertNull(ProcessingContextEvalAnn.evalAnnotation(MyAnnotation3.class, EmptyObjectResolver.getInstance(), ClassContext.createRootClassContext(MyClass4.class, Object.class, Group1.class)));
    }

    @Test
    public void testAnnotationReflectiveWithElement() {
        assertSame(MyClass2.class.getAnnotation(MyAnnotation2.class),
                ProcessingContextEvalAnn.evalAnnotation(MyClass2.class, MyAnnotation2.class, EmptyObjectResolver.getInstance(), ClassContext.createRootClassContext(MyClass2.class, Object.class)));
        assertNull(ProcessingContextEvalAnn.evalAnnotation(MyClass2.class, MyAnnotation3.class, EmptyObjectResolver.getInstance(), ClassContext.createRootClassContext(MyClass2.class, Object.class)));
        assertSame(MyClass3.class.getAnnotation(MyAnnotation3.class),
                ProcessingContextEvalAnn.evalAnnotation(MyClass3.class, MyAnnotation3.class, EmptyObjectResolver.getInstance(), ClassContext.createRootClassContext(MyClass3.class, Object.class)));
        assertNull(ProcessingContextEvalAnn.evalAnnotation(MyClass3.class, MyAnnotation3.class, EmptyObjectResolver.getInstance(),
                ClassContext.createRootClassContext(MyClass3.class, Object.class, Group1.class)));
        assertSame(MyClass3.class.getAnnotation(MyAnnotation3.class), ProcessingContextEvalAnn.evalAnnotation(MyClass3.class, MyAnnotation3.class, EmptyObjectResolver.getInstance(),
                ClassContext.createRootClassContext(MyClass3.class, Object.class, DefaultGroup.class)));
        assertSame(MyClass4.class.getAnnotation(MyAnnotation3.class), ProcessingContextEvalAnn.evalAnnotation(MyClass4.class, MyAnnotation3.class, EmptyObjectResolver.getInstance(),
                ClassContext.createRootClassContext(MyClass4.class, Object.class, Group2.class)));
        assertNull(ProcessingContextEvalAnn.evalAnnotation(MyClass4.class, MyAnnotation3.class, EmptyObjectResolver.getInstance(),
                ClassContext.createRootClassContext(MyClass4.class, Object.class, Group1.class)));
    }

    @Test
    public void testAnnotationReflectiveWithRepeatable() {
        MyAnnotation4 ann = ProcessingContextEvalAnn.evalAnnotation(MyClass5.class, MyAnnotation4.class, EmptyObjectResolver.getInstance(),
                ClassContext.createRootClassContext(MyClass5.class, Object.class));
        assertNotNull(ann);
        assertEquals(1, ann.myInt());
        ann = ProcessingContextEvalAnn.evalAnnotation(MyClass5.class, MyAnnotation4.class, EmptyObjectResolver.getInstance(),
                ClassContext.createRootClassContext(MyClass5.class, Object.class, Group1.class));
        assertNotNull(ann);
        assertEquals(2, ann.myInt());
        ann = ProcessingContextEvalAnn.evalAnnotation(MyClass5.class, MyAnnotation4.class, EmptyObjectResolver.getInstance(),
                ClassContext.createRootClassContext(MyClass5.class, Object.class, Group2.class));
        assertNotNull(ann);
        assertEquals(3, ann.myInt());
        ann = ProcessingContextEvalAnn.evalAnnotation(MyClass5.class, MyAnnotation4.class, EmptyObjectResolver.getInstance(),
                ClassContext.createRootClassContext(MyClass5.class, Object.class, Group3.class));
        assertNull(ann);
    }

}
