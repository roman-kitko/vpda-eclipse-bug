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
package org.vpda.common.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.junit.jupiter.api.Test;
import org.vpda.internal.common.util.PropertiesResolver;

/**
 * Test for {@link PropertiesResolver}
 * 
 * @author kitko
 *
 */
public class PropertiesResolverTest {

    /**
     * Test
     *
     */
    @Test
    public void testResolvePropertiesPropertiesProperties() {
        Properties p1 = new Properties();
        p1.setProperty("key1", "value1");
        p1.setProperty("key2", "value2");
        p1.setProperty("key7", "${key1}");
        Map<Object, Object> p1Copy = new HashMap<Object, Object>(p1);
        Properties p2 = new Properties();
        p2.setProperty("key3", "${key1}");
        p2.setProperty("key4", "${key2}");
        Map<Object, Object> p2Copy = new HashMap<Object, Object>(p2);
        Properties p3 = PropertiesResolver.resolveProperties(p2, p1);
        assertEquals(p1Copy, p1);
        assertEquals(p2Copy, p2);
        assertEquals("value1", p3.getProperty("key3"));
        assertEquals("value2", p3.getProperty("key4"));
    }

    /**
     * Simple test
     * 
     * @throws InterruptedException
     */
    @Test
    public void testSimpleResolveProperties() throws InterruptedException {
        Properties properties = new Properties();
        properties.setProperty("key1", "value1");
        properties.setProperty("key2", "Value of key1 is ${key1}");
        properties.setProperty("key3", "Reference ${key4}");
        properties = PropertiesResolver.resolveProperties(properties);
        assertEquals("Value of key1 is value1", properties.get("key2"));
        assertEquals("Reference ${key4}", properties.get("key3"));
    }

    /**
     * Tesd adavanced
     */
    @Test
    public void testAdvancedResolved() {
        Properties properties = new Properties();
        properties.setProperty("key1", "value1");
        properties.setProperty("key2", "${key1}");
        properties.setProperty("key3", "value3");
        properties.setProperty("key4", "${key2} ${key3}");
        properties = PropertiesResolver.resolveProperties(properties);
        assertEquals("value1 value3", properties.get("key4"));
    }

    /** Test that we will not fail on StackOverflowError */
    @Test
    public void testRecursion() {
        Properties properties = new Properties();
        properties.setProperty("key1", "value1 ${key3}");
        properties.setProperty("key2", "value2 ${key1}");
        properties.setProperty("key3", "value3 ${key2}");
        properties = PropertiesResolver.resolveProperties(properties);
        assertEquals("value3 value2 value1 value3 value2 RECURSION", properties.get("key3"));
    }

}
