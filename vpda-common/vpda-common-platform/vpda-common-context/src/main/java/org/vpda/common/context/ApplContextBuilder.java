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
package org.vpda.common.context;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

import org.vpda.common.types.NamedCode;
import org.vpda.common.util.Builder;
import org.vpda.internal.common.util.OrderedMap;

/**
 * Builder for {@link ApplContext}
 * 
 * @author kitko
 *
 */
public final class ApplContextBuilder implements Builder<ApplContext> {

    private OrderedMap<ContextItemKey, ContextItem<?>> items;
    private long id;
    private String name;
    private String code;
    private String description;
    private ApplContext parent;

    /**
     * Creates ApplContextBuilder
     */
    public ApplContextBuilder() {
        this.items = new OrderedMap<>(2);
    }

    @Override
    public Class<? extends ApplContext> getTargetClass() {
        return ApplContext.class;
    }

    @Override
    public ApplContext build() {
        return new ApplContext(this);
    }

    /**
     * @return the items
     */
    public Map<ContextItemKey, ContextItem<?>> getItems() {
        return Collections.unmodifiableMap(items);
    }

    OrderedMap<ContextItemKey, ContextItem<?>> getInternalItems() {
        return items;
    }

    /**
     * @param item
     * @return this
     */
    public ApplContextBuilder addItem(ContextItem item) {
        this.items.put(item.getKey(), item);
        return this;
    }

    /**
     * Add more items
     * 
     * @param items
     * @return this
     */
    public ApplContextBuilder addItems(ContextItem... items) {
        for (ContextItem item : items) {
            this.items.put(item.getKey(), item);
        }
        return this;
    }

    /**
     * @param items
     * @return this
     */
    public ApplContextBuilder addItems(Collection<ContextItem<?>> items) {
        for (ContextItem item : items) {
            this.items.put(item.getKey(), item);
        }
        return this;
    }

    /**
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * @param id the id to set
     * @return this
     */
    public ApplContextBuilder setId(long id) {
        this.id = id;
        return this;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     * @return this
     */
    public ApplContextBuilder setName(String name) {
        this.name = name;
        return this;
    }

    /**
     * @return the code
     */
    public String getCode() {
        return code;
    }

    /**
     * @param code the code to set
     * @return this
     */
    public ApplContextBuilder setCode(String code) {
        this.code = code;
        return this;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     * @return this
     */
    public ApplContextBuilder setDescription(String description) {
        this.description = description;
        return this;
    }

    /**
     * @param node
     * @return this
     */
    public ApplContextBuilder setNamedNodeValues(NamedCode node) {
        setCode(node.getDescription());
        setName(node.getName());
        setDescription(node.getDescription());
        return this;
    }

    /**
     * @return the parent
     */
    public ApplContext getParent() {
        return parent;
    }

    /**
     * @param parent the parent to set
     * @return this
     */
    public ApplContextBuilder setParent(ApplContext parent) {
        this.parent = parent;
        return this;
    }

    /**
     * @param ctx
     * @return this
     */
    public ApplContextBuilder setValues(ApplContext ctx) {
        this.code = ctx.getCode();
        this.name = ctx.getName();
        this.description = ctx.getDescription();
        this.id = ctx.getId();
        this.items = new OrderedMap<>(ctx.getItemsInternal());
        return this;
    }

}
