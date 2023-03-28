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

import java.lang.reflect.Field;

import org.vpda.clientserver.viewprovider.list.Column;
import org.vpda.clientserver.viewprovider.list.ColumnsGroup;
import org.vpda.clientserver.viewprovider.list.annotations.ColumnInfo;
import org.vpda.common.context.localization.LocKey;
import org.vpda.common.ioc.objectresolver.ObjectResolver;
import org.vpda.common.processor.FieldBuilder;
import org.vpda.common.processor.ProcessorResolver;
import org.vpda.common.processor.ctx.FieldContext;
import org.vpda.common.processor.impl.AbstractFieldBuilder;
import org.vpda.common.service.localization.LocKeyFieldBuilder;
import org.vpda.common.service.localization.LocPair;
import org.vpda.common.service.localization.StringLocValue;
import org.vpda.common.util.AnnotationConstants;

/**
 * @author kitko
 *
 */
final class ColumnFieldBuilder extends AbstractFieldBuilder<Column> implements FieldBuilder<Column> {

    @Override
    public Column build(FieldContext<Column> fieldContext, ProcessorResolver resolver, ObjectResolver context) {
        Column col = fieldContext.tryGetTargetFieldValue();
        ColumnsGroup group = context.resolveObject(ColumnsGroup.class);
        if (group == null) {
            throw new IllegalStateException("No group in context for field [" + fieldContext + "]");
        }
        Field field = fieldContext.getField();
        ColumnInfo info = field.getAnnotation(ColumnInfo.class);
        if (info == null) {
            return buildDefaultWithGroup(col, group, field, resolver, context);
        }
        Column.Builder builder = new Column.Builder();
        if (col != null) {
            builder.setValue(col);
        }
        builder.setType(!AnnotationConstants.UNDEFINED_TYPE.class.equals(info.type()) ? info.type() : (builder.getType() != null ? builder.getType() : String.class));
        builder.setGroupId(group.getId());
        builder.setLocalId(!AnnotationConstants.UNDEFINED_STRING.equals(info.localId()) ? info.localId() : (builder.getLocalId() != null ? builder.getLocalId() : field.getName()));
        LocKey titleKey = null;
        if (builder.getTitle() == null || !AnnotationConstants.UNDEFINED_STRING.equals(info.titleKey().path())) {
            if (!AnnotationConstants.UNDEFINED_STRING.equals(info.titleKey().path())) {
                titleKey = LocKeyFieldBuilder.createLocKey(info.titleKey());
            }
            else {
                if (group.getColumnsTitleKeyPrefix() != null) {
                    titleKey = group.getColumnsTitleKeyPrefix().createChildKey(builder.getLocalId());
                }
                else {
                    titleKey = group.getTitle().getLocKey().createChildKey(builder.getLocalId());
                }
            }
            LocPair<StringLocValue> title = LocPair.createStringLocPair(titleKey, null);
            builder.setTitle(title);
        }
        if (ColumnInfo.Visibility.INVISIBLE.equals(info.visibility()) || field.isAnnotationPresent(ColumnInfo.Invisible.class)) {
            builder.setInvisible(true);
        }
        Column.ColumnLocalizer localizer = context.resolveObject(Column.ColumnLocalizer.class);
        builder.setLocalizer(localizer);
        Column column = builder.build();
        return column;
    }

    private Column buildDefaultWithGroup(Column col, ColumnsGroup group, Field field, ProcessorResolver resolver, ObjectResolver context) {
        Column.Builder builder = new Column.Builder();
        if (col != null) {
            builder.setValue(col);
        }
        if (builder.getType() == null) {
            builder.setType(String.class);
        }
        builder.setGroupId(group.getId());
        if (builder.getLocalId() == null) {
            builder.setLocalId(field.getName());
        }
        if (builder.getTitle() == null) {
            LocKey titleKey = null;
            if (group.getColumnsTitleKeyPrefix() != null) {
                titleKey = group.getColumnsTitleKeyPrefix().createChildKey(builder.getLocalId());
            }
            else {
                titleKey = group.getTitle().getLocKey().createChildKey(builder.getLocalId());
            }
            LocPair<StringLocValue> title = LocPair.createStringLocPair(titleKey, null);
            builder.setTitle(title);
        }
        if (field.isAnnotationPresent(ColumnInfo.Invisible.class)) {
            builder.setInvisible(true);
        }
        Column.ColumnLocalizer localizer = context.resolveObject(Column.ColumnLocalizer.class);
        builder.setLocalizer(localizer);
        return builder.build();
    }

    @Override
    public Class<? extends Column> getTargetClass() {
        return Column.class;
    }

}
