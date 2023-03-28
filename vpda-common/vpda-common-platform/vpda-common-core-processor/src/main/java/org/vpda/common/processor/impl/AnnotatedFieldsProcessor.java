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
package org.vpda.common.processor.impl;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.vpda.common.ioc.objectresolver.ObjectResolver;
import org.vpda.common.processor.ItemsClassProcessor;
import org.vpda.common.processor.ProcessorResolver;
import org.vpda.common.processor.ctx.ClassItemContext;

/**
 * Will process class looking at fields with passed annotation
 * 
 * @author kitko
 *
 * @param <T>
 */
public final class AnnotatedFieldsProcessor<T> extends AbstractItemsClassProcessor<T> implements ItemsClassProcessor<T> {
    private final Class<T> targetItemClass;
    private final List<Class<? extends Annotation>> annClasses;

    /**
     * Creates processor with annotation and target class
     * 
     * @param targetItemClass
     * @param annClasses
     */
    public AnnotatedFieldsProcessor(Class<T> targetItemClass, List<Class<? extends Annotation>> annClasses) {
        super();
        this.targetItemClass = targetItemClass;
        this.annClasses = new ArrayList<Class<? extends Annotation>>(annClasses);
    }

    /**
     * @param targetItemClass
     * @param annClass
     */
    public AnnotatedFieldsProcessor(Class<T> targetItemClass, Class<? extends Annotation> annClass) {
        this.targetItemClass = targetItemClass;
        this.annClasses = Collections.<Class<? extends Annotation>>singletonList(annClass);
    }

    /**
     * @param targetItemClass
     * @param annClasses
     */
    @SafeVarargs
    public AnnotatedFieldsProcessor(Class<T> targetItemClass, Class<? extends Annotation>... annClasses) {
        this.targetItemClass = targetItemClass;
        this.annClasses = Arrays.asList(annClasses);
    }

    @Override
    public Class<T> getTargetItemClass() {
        return targetItemClass;
    }

    @Override
    public List<T> process(ClassItemContext<T> classContext, ProcessorResolver processorResolver, ObjectResolver context) {
        return ProcessorHelper.processAnnotatedFields(ClassItemContext.createClassItemContext(classContext, classContext.getProcessedClass(), targetItemClass), processorResolver, context, annClasses);
    }

}
