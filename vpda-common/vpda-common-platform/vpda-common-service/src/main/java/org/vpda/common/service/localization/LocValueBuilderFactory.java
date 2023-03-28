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

/**
 * This is factory for loc value builders. Its purpose is to get factories for
 * value builders
 * 
 * @author kitko
 *
 */
public interface LocValueBuilderFactory {
    /**
     * gets factory by builder class
     * 
     * @param builderClass
     * @param <L>
     * @param <B>
     * @return OneLocValueBuilderFactory by LocValueBuilder class
     */
    public <L extends LocValue, B extends LocValueBuilder<L>> OneLocValueBuilderFactory<L, B> getFactoryByBuilderClass(Class<B> builderClass);

    /**
     * Register factory that will create Value builders
     * 
     * @param <L>
     * @param <B>
     * @param factory
     */
    public <L extends LocValue, B extends LocValueBuilder<L>> void registerOneLocValueBuilderFactory(OneLocValueBuilderFactory<L, B> factory);

    /**
     * Creates builder by its class
     * 
     * @param builderClass
     * @param <L>
     * @param <B>
     * @return LocValueBuilder
     */
    public <L extends LocValue, B extends LocValueBuilder<L>> B createBuilderByBuilderClass(Class<B> builderClass);

    public default <L extends LocValue, B extends LocValueBuilder<L>> B createBuilderByBuilderClass(Class<B> builderClass, B defaultBuilder) {
        B builder = createBuilderByBuilderClass(builderClass);
        return builder != null ? builder : defaultBuilder;
    }

    /**
     * Gets builder factory by locvalue class
     * 
     * @param <L>
     * @param <B>
     * @param locValueClass
     * @return buildefactory that is able to create builder for locvalue class
     */
    public <L extends LocValue, B extends LocValueBuilder<L>> OneLocValueBuilderFactory<L, B> getFactoryByLocValueClass(Class<L> locValueClass);

    /**
     * Creates builder by locvalue class
     * 
     * @param <L>
     * @param <B>
     * @param locValueClass
     * @return LocValueBuilder
     */
    public <L extends LocValue, B extends LocValueBuilder<L>> B createBuilderByLocValueClass(Class<L> locValueClass);

    /**
     * Factory for one LocValue builder
     * 
     * @param <L>
     * @param <B>
     */
    public static interface OneLocValueBuilderFactory<L extends LocValue, B extends LocValueBuilder<L>> {
        /**
         * Creates builder
         * 
         * @return new or cached builder
         */
        public B createBuilder();

        /**
         * 
         * @return builder class
         */
        public Class<B> getBuilderClass();

        /**
         * @return locbuilder locvalue class
         */
        public Class<? extends LocValue> getLocValueClass();

    }
}
