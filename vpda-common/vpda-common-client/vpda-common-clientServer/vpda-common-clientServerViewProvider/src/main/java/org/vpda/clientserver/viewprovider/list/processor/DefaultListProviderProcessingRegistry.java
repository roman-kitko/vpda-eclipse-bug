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
package org.vpda.clientserver.viewprovider.list.processor;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

import org.vpda.clientserver.viewprovider.list.Column;
import org.vpda.clientserver.viewprovider.list.ColumnsGroup;
import org.vpda.clientserver.viewprovider.list.ColumnsGroupsDef;
import org.vpda.clientserver.viewprovider.list.annotations.ColumnInfo;
import org.vpda.clientserver.viewprovider.list.annotations.Columns;
import org.vpda.clientserver.viewprovider.list.annotations.ColumnsGroupInfo;
import org.vpda.clientserver.viewprovider.list.annotations.ColumnsGroupsDefInfo;
import org.vpda.common.ioc.objectresolver.ObjectResolver;
import org.vpda.common.processor.ClassProcessor;
import org.vpda.common.processor.FieldBuilder;
import org.vpda.common.processor.ItemsClassProcessor;
import org.vpda.common.processor.ProcessingRegistry;
import org.vpda.common.processor.impl.AbstractProcessingRegistry;
import org.vpda.common.processor.impl.AnnotatedFieldsProcessor;

/**
 * Default ProcessingRegistry for list view providers basic object
 * 
 * @author kitko
 *
 */
public final class DefaultListProviderProcessingRegistry extends AbstractProcessingRegistry implements ProcessingRegistry {

    @SuppressWarnings("unchecked")
    @Override
    public <T> FieldBuilder<T> getBuilderByFieldAnnotation(Class<? extends Annotation> annClass, Class<T> targetType, Field field, ObjectResolver context) {
        if (ColumnInfo.class.equals(annClass)) {
            return (FieldBuilder<T>) new ColumnFieldBuilder();
        }
        else if (ColumnsGroupInfo.class.equals(annClass)) {
            return (FieldBuilder<T>) new ColumnsGroupFieldBuilder();
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> FieldBuilder<T> getBuilderByFieldType(Class<?> type, Class<T> targetType, Field field, ObjectResolver context) {
        if (Column.class.equals(type)) {
            return (FieldBuilder<T>) new ColumnFieldBuilder();
        }
        else if (ColumnsGroup.class.equals(type)) {
            return (FieldBuilder<T>) new ColumnsGroupFieldBuilder();
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> ClassProcessor<T> getProcessorByAnnotation(Class<? extends Annotation> annClass, Class<?> clazz, Class<T> targetClass, ObjectResolver context) {
        if (ColumnsGroupInfo.class.equals(annClass)) {
            return (ClassProcessor<T>) new ColumnsGroupProcessor();
        }
        else if (ColumnsGroupsDefInfo.class.equals(annClass)) {
            return (ClassProcessor<T>) new ColumnsGroupsDefProcessor();
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> ClassProcessor<T> getProcessorByProcessedAndTargetClass(Class<?> processedClass, Class<T> targetClass, ObjectResolver context) {
        if (ColumnsGroup.class.equals(targetClass)) {
            return (ClassProcessor<T>) new ColumnsGroupProcessor();
        }
        else if (ColumnsGroupsDef.class.equals(targetClass)) {
            return (ClassProcessor<T>) new ColumnsGroupsDefProcessor();
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> ItemsClassProcessor<T> getItemsProcessorByProcessedAndTargetItemClass(Class<?> processedClass, Class<T> targetItemClass, ObjectResolver context) {
        if (processedClass.isAnnotationPresent(Columns.class)) {
            return (ItemsClassProcessor<T>) new ColumnsProcessor();
        }
        else if (Column.class.equals(targetItemClass)) {
            return new AnnotatedFieldsProcessor<T>(targetItemClass, ColumnInfo.class);
        }
        return null;
    }

}
