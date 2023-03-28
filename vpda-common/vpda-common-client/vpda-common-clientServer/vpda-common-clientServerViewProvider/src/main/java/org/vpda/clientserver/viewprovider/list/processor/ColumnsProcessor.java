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
package org.vpda.clientserver.viewprovider.list.processor;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.vpda.clientserver.viewprovider.list.Column;
import org.vpda.clientserver.viewprovider.list.annotations.Columns;
import org.vpda.common.ioc.objectresolver.ObjectResolver;
import org.vpda.common.processor.ItemsClassProcessor;
import org.vpda.common.processor.ProcessorResolver;
import org.vpda.common.processor.ctx.ClassItemContext;
import org.vpda.common.processor.ctx.FieldContext;
import org.vpda.common.processor.impl.AbstractItemsClassProcessor;
import org.vpda.common.processor.impl.ProcessorHelper;

final class ColumnsProcessor extends AbstractItemsClassProcessor<Column> implements ItemsClassProcessor<Column> {

    @Override
    public Class<Column> getTargetItemClass() {
        return Column.class;
    }

    @Override
    public List<Column> process(ClassItemContext<Column> classItem, ProcessorResolver processorResolver, ObjectResolver context) {
        List<Column> columns = new ArrayList<Column>();
        for (Field iField : ProcessorHelper.getDeclaredFieldsConsideringProcessingInfo(classItem, context)) {
            if (iField.getType().equals(Column.class)) {
                Column column = ProcessorHelper.resolveAndBuildField(FieldContext.createFieldContext(classItem, iField, Column.class), processorResolver, context);
                columns.add(column);
            }
            else if (iField.getType().isAnnotationPresent(Columns.class)) {
                List<Column> nestedColumns = process(ClassItemContext.createClassItemContext(classItem, iField.getType(), Column.class), processorResolver, context);
                columns.addAll(nestedColumns);
            }
        }
        return columns;
    }

}
