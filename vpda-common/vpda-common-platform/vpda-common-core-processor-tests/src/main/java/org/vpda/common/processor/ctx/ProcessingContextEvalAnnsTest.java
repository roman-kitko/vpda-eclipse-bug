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
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.hamcrest.MatcherAssert.assertThat;

import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Collections;
import java.util.List;

import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;
import org.vpda.common.ioc.objectresolver.EmptyObjectResolver;
import org.vpda.common.processor.annotation.GroupFiltering;
import org.vpda.common.processor.annotation.GroupFilteringMethod;
import org.vpda.common.processor.annotation.ProcessingInfo;
import org.vpda.common.processor.annotation.eval.ProcessingContextEvalAnns;
import org.vpda.common.processor.ctx.ClassContext;
import org.vpda.common.processor.ctx.DefaultGroup;
import org.vpda.common.processor.ctx.ProcessingContextEvalAnnsTest.MyAnnotation4.MyAnnotation4s;

@SuppressWarnings("javadoc")
public class ProcessingContextEvalAnnsTest {

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
    @MyAnnotation4(myInt = 4, buildGroups = { DefaultGroup.class })
    @MyAnnotation4(myInt = 5, buildGroups = { DefaultGroup.class, Group2.class })
    public static class MyClass5 {
    }

    @Test
    public void testEvalAnnotation1() {
        assertThat(ProcessingContextEvalAnns.evalAnnotations(ProcessingInfo.class, EmptyObjectResolver.getInstance(), ClassContext.createRootClassContext(MyClass1.class, Number.class, Group1.class),
                a -> a.processGroups(), a -> a.targetClass()), CoreMatchers.hasItem(MyClass1.class.getAnnotation(ProcessingInfo.class)));
        assertThat(ProcessingContextEvalAnns.evalAnnotations(ProcessingInfo.class, EmptyObjectResolver.getInstance(), ClassContext.createRootClassContext(MyClass1.class, Integer.class, Group1.class),
                a -> a.processGroups(), a -> a.targetClass()).size(), CoreMatchers.equalTo(0));
        assertThat(ProcessingContextEvalAnns.evalAnnotations(ProcessingInfo.class, EmptyObjectResolver.getInstance(), ClassContext.createRootClassContext(MyClass1.class, Number.class, Group2.class),
                a -> a.processGroups(), a -> a.targetClass()).size(), CoreMatchers.equalTo(0));
        assertThat(ProcessingContextEvalAnns.evalAnnotations(ProcessingInfo.class, EmptyObjectResolver.getInstance(), ClassContext.createRootClassContext(MyClass1.class, Number.class, Group4.class),
                a -> a.processGroups(), a -> a.targetClass()), CoreMatchers.hasItem(MyClass1.class.getAnnotation(ProcessingInfo.class)));
    }

    @Test
    public void testEvalAnnotation2() {
        assertThat(
                ProcessingContextEvalAnns.evalAnnotations(MyClass1.class, ProcessingInfo.class, EmptyObjectResolver.getInstance(),
                        ClassContext.createRootClassContext(MyClass1.class, Number.class, Group1.class), a -> a.processGroups(), a -> a.targetClass()),
                CoreMatchers.hasItem(MyClass1.class.getAnnotation(ProcessingInfo.class)));

        assertThat(ProcessingContextEvalAnns.evalAnnotations(MyClass1.class, ProcessingInfo.class, EmptyObjectResolver.getInstance(),
                ClassContext.createRootClassContext(MyClass1.class, Integer.class, Group1.class), a -> a.processGroups(), a -> a.targetClass()).size(), CoreMatchers.equalTo(0));

        assertThat(ProcessingContextEvalAnns.evalAnnotations(MyClass1.class, ProcessingInfo.class, EmptyObjectResolver.getInstance(),
                ClassContext.createRootClassContext(MyClass1.class, Number.class, Group2.class), a -> a.processGroups(), a -> a.targetClass()).size(), CoreMatchers.equalTo(0));
        assertThat(
                ProcessingContextEvalAnns.evalAnnotations(MyClass1.class, ProcessingInfo.class, EmptyObjectResolver.getInstance(),
                        ClassContext.createRootClassContext(MyClass1.class, Number.class, Group4.class), a -> a.processGroups(), a -> a.targetClass()),
                CoreMatchers.hasItem(MyClass1.class.getAnnotation(ProcessingInfo.class)));
    }

    @Test
    public void testAnnotationReflective() {
        assertEquals(Collections.singletonList(MyClass2.class.getAnnotation(MyAnnotation2.class)),
                ProcessingContextEvalAnns.evalAnnotations(MyAnnotation2.class, EmptyObjectResolver.getInstance(), ClassContext.createRootClassContext(MyClass2.class, Object.class)));
        assertTrue(ProcessingContextEvalAnns.evalAnnotations(MyAnnotation3.class, EmptyObjectResolver.getInstance(), ClassContext.createRootClassContext(MyClass2.class, Object.class)).isEmpty());
        assertEquals(Collections.singletonList(MyClass3.class.getAnnotation(MyAnnotation3.class)),
                ProcessingContextEvalAnns.evalAnnotations(MyAnnotation3.class, EmptyObjectResolver.getInstance(), ClassContext.createRootClassContext(MyClass3.class, Object.class)));
        assertTrue(ProcessingContextEvalAnns.evalAnnotations(MyAnnotation3.class, EmptyObjectResolver.getInstance(), ClassContext.createRootClassContext(MyClass3.class, Object.class, Group1.class))
                .isEmpty());
        assertEquals(Collections.singletonList(MyClass3.class.getAnnotation(MyAnnotation3.class)), ProcessingContextEvalAnns.evalAnnotations(MyAnnotation3.class, EmptyObjectResolver.getInstance(),
                ClassContext.createRootClassContext(MyClass3.class, Object.class, DefaultGroup.class)));
        assertEquals(Collections.singletonList(MyClass4.class.getAnnotation(MyAnnotation3.class)),
                ProcessingContextEvalAnns.evalAnnotations(MyAnnotation3.class, EmptyObjectResolver.getInstance(), ClassContext.createRootClassContext(MyClass4.class, Object.class, Group2.class)));
        assertTrue(ProcessingContextEvalAnns.evalAnnotations(MyAnnotation3.class, EmptyObjectResolver.getInstance(), ClassContext.createRootClassContext(MyClass4.class, Object.class, Group1.class))
                .isEmpty());
    }

    @Test
    public void testAnnotationReflectiveWithElement() {
        assertEquals(Collections.singletonList(MyClass2.class.getAnnotation(MyAnnotation2.class)),
                ProcessingContextEvalAnns.evalAnnotations(MyClass2.class, MyAnnotation2.class, EmptyObjectResolver.getInstance(), ClassContext.createRootClassContext(MyClass2.class, Object.class)));
        assertTrue(ProcessingContextEvalAnns.evalAnnotations(MyClass2.class, MyAnnotation3.class, EmptyObjectResolver.getInstance(), ClassContext.createRootClassContext(MyClass2.class, Object.class))
                .isEmpty());
        assertEquals(Collections.singletonList(MyClass3.class.getAnnotation(MyAnnotation3.class)),
                ProcessingContextEvalAnns.evalAnnotations(MyClass3.class, MyAnnotation3.class, EmptyObjectResolver.getInstance(), ClassContext.createRootClassContext(MyClass3.class, Object.class)));
        assertTrue(ProcessingContextEvalAnns
                .evalAnnotations(MyClass3.class, MyAnnotation3.class, EmptyObjectResolver.getInstance(), ClassContext.createRootClassContext(MyClass3.class, Object.class, Group1.class)).isEmpty());
        assertEquals(Collections.singletonList(MyClass3.class.getAnnotation(MyAnnotation3.class)), ProcessingContextEvalAnns.evalAnnotations(MyClass3.class, MyAnnotation3.class,
                EmptyObjectResolver.getInstance(), ClassContext.createRootClassContext(MyClass3.class, Object.class, DefaultGroup.class)));
        assertEquals(Collections.singletonList(MyClass4.class.getAnnotation(MyAnnotation3.class)), ProcessingContextEvalAnns.evalAnnotations(MyClass4.class, MyAnnotation3.class,
                EmptyObjectResolver.getInstance(), ClassContext.createRootClassContext(MyClass4.class, Object.class, Group2.class)));
        assertTrue(ProcessingContextEvalAnns
                .evalAnnotations(MyClass4.class, MyAnnotation3.class, EmptyObjectResolver.getInstance(), ClassContext.createRootClassContext(MyClass4.class, Object.class, Group1.class)).isEmpty());
    }

    @Test
    public void testAnnotationReflectiveWithRepeatable() {
        List<MyAnnotation4> anns = ProcessingContextEvalAnns.evalAnnotations(MyClass5.class, MyAnnotation4.class, EmptyObjectResolver.getInstance(),
                ClassContext.createRootClassContext(MyClass5.class, Object.class));
        assertEquals(3, anns.size());

        anns = ProcessingContextEvalAnns.evalAnnotations(MyClass5.class, MyAnnotation4.class, EmptyObjectResolver.getInstance(),
                ClassContext.createRootClassContext(MyClass5.class, Object.class, Group1.class));
        assertEquals(1, anns.size());

        anns = ProcessingContextEvalAnns.evalAnnotations(MyClass5.class, MyAnnotation4.class, EmptyObjectResolver.getInstance(),
                ClassContext.createRootClassContext(MyClass5.class, Object.class, Group2.class));
        assertEquals(2, anns.size());

        anns = ProcessingContextEvalAnns.evalAnnotations(MyClass5.class, MyAnnotation4.class, EmptyObjectResolver.getInstance(),
                ClassContext.createRootClassContext(MyClass5.class, Object.class, Group3.class));
        assertTrue(anns.isEmpty());
    }

}
