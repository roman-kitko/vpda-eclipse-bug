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

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.vpda.common.service.localization.LocValueBuilder;
import org.vpda.common.service.localization.LocValueBuilderFactory;
import org.vpda.common.service.localization.LocValueBuilderRegistry;
import org.vpda.common.service.localization.StringLocValue;
import org.vpda.common.service.localization.StringLocValueBuilder;
import org.vpda.common.service.localization.LocValueBuilderFactory.OneLocValueBuilderFactory;

/**
 * @author kitko
 *
 */
public class LocValueBuilderFactoryImplTest {
    private static LocValueBuilderFactory factory;

    /**
     * Test method for
     * {@link org.vpda.common.service.localization.LocValueBuilderRegistry#createBuilderByBuilderClass(java.lang.Class)}.
     */
    @Test
    public void testCreateBuilderByBuilderClass() {
        LocValueBuilder<?> builder = factory.createBuilderByBuilderClass(StringLocValueBuilder.class);
        assertNotNull(builder);
    }

    /**
     * Test method for
     * {@link org.vpda.common.service.localization.LocValueBuilderRegistry#getFactoryByBuilderClass(java.lang.Class)}.
     */
    @Test
    public void testGetFactoryByBuilderClass() {
        OneLocValueBuilderFactory<StringLocValue, StringLocValueBuilder> factoryByBuilderClass = factory.getFactoryByBuilderClass(StringLocValueBuilder.class);
        assertNotNull(factoryByBuilderClass);
    }

    /**
     * Test method for
     * {@link org.vpda.common.service.localization.LocValueBuilderRegistry#registerOneLocValueBuilderFactory(org.vpda.common.service.localization.LocValueBuilderFactory.OneLocValueBuilderFactory)}.
     */
    @Test
    public void testRegisterOneLocValueBuilderFactory() {
        LocValueBuilderFactory.OneLocValueBuilderFactory<StringLocValue, StringLocValueBuilder> sf = new StringLocValueBuilder.StringLocValueBuilderFactory();
        factory.registerOneLocValueBuilderFactory(sf);
        assertSame(sf, factory.getFactoryByBuilderClass(StringLocValueBuilder.class));
        assertSame(sf, factory.getFactoryByLocValueClass(StringLocValue.class));
    }

    /**
     * Test method for
     * {@link org.vpda.common.service.localization.LocValueBuilderRegistry#createBuilderByLocValueClass(java.lang.Class)}.
     */
    @Test
    public void testCreateBuilderByLocValueClass() {
        StringLocValueBuilder builder = factory.createBuilderByBuilderClass(StringLocValueBuilder.class);
        assertNotNull(builder);
    }

    /**
     * Test method for
     * {@link org.vpda.common.service.localization.LocValueBuilderRegistry#getFactoryByLocValueClass(java.lang.Class)}.
     */
    @Test
    public void testGetFactoryByLocValueClass() {
        LocValueBuilder<StringLocValue> builder = factory.createBuilderByLocValueClass(StringLocValue.class);
        assertNotNull(builder);
    }

    /**
     * creates factory
     */
    @BeforeAll
    public static void setupBeforeClass() {
        factory = new LocValueBuilderRegistry();
    }

}
