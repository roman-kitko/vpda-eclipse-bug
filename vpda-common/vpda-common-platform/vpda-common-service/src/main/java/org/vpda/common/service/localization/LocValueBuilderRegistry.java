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
package org.vpda.common.service.localization;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.vpda.common.service.localization.LocValueBuilderFactory.OneLocValueBuilderFactory;

/**
 * Registry of {@link LocValueBuilderFactory.OneLocValueBuilderFactory}
 * 
 * @author kitko
 *
 */
public final class LocValueBuilderRegistry implements LocValueBuilderFactory, Serializable {
    private static final long serialVersionUID = -3970361119868303776L;
    private final Map<Class<? extends LocValueBuilder>, OneLocValueBuilderFactory> factoryMapByBuilderClass;
    private final Map<Class<? extends LocValue>, OneLocValueBuilderFactory> factoryMapByLocValueClass;

    /**
     * Creates LocValueBuilderFactoryImpl with common builders
     */
    public LocValueBuilderRegistry() {
        factoryMapByBuilderClass = new HashMap<Class<? extends LocValueBuilder>, OneLocValueBuilderFactory>(20);
        factoryMapByLocValueClass = new HashMap<Class<? extends LocValue>, OneLocValueBuilderFactory>(20);
        initCommonBuilders();
    }

    private void initCommonBuilders() {
        registerOneLocValueBuilderFactory(new StringLocValueBuilder.StringLocValueBuilderFactory());

    }

    @Override
    public <L extends LocValue, B extends LocValueBuilder<L>> B createBuilderByBuilderClass(Class<B> builderClass) {
        OneLocValueBuilderFactory<L, B> f = getFactoryByBuilderClass(builderClass);
        return f != null ? f.createBuilder() : null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <L extends LocValue, B extends LocValueBuilder<L>> OneLocValueBuilderFactory<L, B> getFactoryByBuilderClass(Class<B> builderClass) {
        return factoryMapByBuilderClass.get(builderClass);
    }

    @Override
    public <L extends LocValue, B extends LocValueBuilder<L>> void registerOneLocValueBuilderFactory(OneLocValueBuilderFactory<L, B> factory) {
        factoryMapByBuilderClass.put(factory.getBuilderClass(), factory);
        factoryMapByLocValueClass.put(factory.getLocValueClass(), factory);
    }

    @Override
    public <L extends LocValue, B extends LocValueBuilder<L>> B createBuilderByLocValueClass(Class<L> locValueClass) {
        OneLocValueBuilderFactory<L, B> factory = getFactoryByLocValueClass(locValueClass);
        return factory != null ? factory.createBuilder() : null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <L extends LocValue, B extends LocValueBuilder<L>> OneLocValueBuilderFactory<L, B> getFactoryByLocValueClass(Class<L> locValueClass) {
        return factoryMapByLocValueClass.get(locValueClass);
    }

}
