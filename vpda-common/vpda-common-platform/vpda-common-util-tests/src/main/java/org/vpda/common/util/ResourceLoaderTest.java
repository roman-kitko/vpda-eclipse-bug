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
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.vpda.internal.common.util.PropertyBag;
import org.vpda.internal.common.util.ResourceLoader;

/**
 * @author kitko
 *
 */
public class ResourceLoaderTest {

    /**
     * Test method for
     * {@link org.vpda.internal.common.util.ResourceLoader#getURLOfResourceWithNameOfClass(java.lang.Class)}.
     */
    @Test
    public void testGetResourceURLClassOfQ() {
        assertNotNull(ResourceLoader.getURLOfResourceWithNameOfClass(ResourceLoaderTest.class));
        assertEquals(getClass().getClassLoader().getResource(ResourceLoaderTest.class.getName()), ResourceLoader.getURLOfResourceWithNameOfClass(ResourceLoaderTest.class));
        String oldProperty = System.getProperty(ResourceLoader.PREFIX_PROPERTY_NAME);
        System.setProperty(ResourceLoader.PREFIX_PROPERTY_NAME, "noExists");
        assertNotNull(ResourceLoader.getURLOfResourceWithNameOfClass(ResourceLoaderTest.class));
        assertEquals(getClass().getClassLoader().getResource(ResourceLoaderTest.class.getName()), ResourceLoader.getURLOfResourceWithNameOfClass(ResourceLoaderTest.class));
        System.setProperty(ResourceLoader.PREFIX_PROPERTY_NAME, "testConfig1");
        assertNotNull(ResourceLoader.getURLOfResourceWithNameOfClass(ResourceLoaderTest.class));
        assertEquals(getClass().getClassLoader().getResource("testConfig1/" + ResourceLoaderTest.class.getName()), ResourceLoader.getURLOfResourceWithNameOfClass(ResourceLoaderTest.class));
        if (oldProperty == null) {
            System.clearProperty(ResourceLoader.PREFIX_PROPERTY_NAME);
        }
        else {
            System.setProperty(ResourceLoader.PREFIX_PROPERTY_NAME, oldProperty);
        }

    }

    /**
     * Test method for
     * {@link org.vpda.internal.common.util.ResourceLoader#getResourceURL(java.lang.String)}.
     */
    @Test
    public void testGetResourceURLString() {
        assertNotNull(ResourceLoader.getResourceURL(ResourceLoaderTest.class.getName()));
        assertEquals(getClass().getClassLoader().getResource(ResourceLoaderTest.class.getName()), ResourceLoader.getResourceURL(ResourceLoaderTest.class.getName()));
        String oldProperty = System.getProperty(ResourceLoader.PREFIX_PROPERTY_NAME);
        System.setProperty(ResourceLoader.PREFIX_PROPERTY_NAME, "noExists");
        assertNotNull(ResourceLoader.getResourceURL(ResourceLoaderTest.class.getName()));
        System.setProperty(ResourceLoader.PREFIX_PROPERTY_NAME, "testConfig1");
        assertEquals(getClass().getClassLoader().getResource("testConfig1/" + ResourceLoaderTest.class.getName()), ResourceLoader.getResourceURL(ResourceLoaderTest.class.getName()));
        if (oldProperty == null) {
            System.clearProperty(ResourceLoader.PREFIX_PROPERTY_NAME);
        }
        else {
            System.setProperty(ResourceLoader.PREFIX_PROPERTY_NAME, oldProperty);
        }

    }

    /**
     * Test method for
     * {@link org.vpda.internal.common.util.ResourceLoader#getPropertyBag(java.lang.Class)}.
     * 
     * @throws IOException
     */
    @Test
    public void testGetPropertyBagClassOfQ() throws IOException {
        PropertyBag bag = ResourceLoader.getPropertyBag(ResourceLoaderTest.class);
        assertNotNull(bag);
        assertEquals("7", bag.getStringValue("x"));
        String oldProperty = System.getProperty(ResourceLoader.PREFIX_PROPERTY_NAME);
        System.setProperty(ResourceLoader.PREFIX_PROPERTY_NAME, "noExists");
        PropertyBag bag1 = ResourceLoader.getPropertyBag(ResourceLoaderTest.class);
        assertNotNull(bag1);
        assertEquals(bag, bag1);
        System.setProperty(ResourceLoader.PREFIX_PROPERTY_NAME, "testConfig1");
        bag1 = ResourceLoader.getPropertyBag(ResourceLoaderTest.class);
        assertNotNull(bag1);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("x", "6");
        map.put("y", "8");
        map.put("a", "3");
        assertEquals(new PropertyBag(map), bag1);
        if (oldProperty == null) {
            System.clearProperty(ResourceLoader.PREFIX_PROPERTY_NAME);
        }
        else {
            System.setProperty(ResourceLoader.PREFIX_PROPERTY_NAME, oldProperty);
        }

    }

    /**
     * Test method for
     * {@link org.vpda.internal.common.util.ResourceLoader#getPropertyBag(java.lang.String)}.
     * 
     * @throws IOException
     */
    @Test
    public void testGetPropertyBagString() throws IOException {
        PropertyBag bag = ResourceLoader.getPropertyBag(ResourceLoaderTest.class.getName());
        assertNotNull(bag);
        assertEquals("7", bag.getStringValue("x"));
        String oldProperty = System.getProperty(ResourceLoader.PREFIX_PROPERTY_NAME);
        System.setProperty(ResourceLoader.PREFIX_PROPERTY_NAME, "noExists");
        PropertyBag bag1 = ResourceLoader.getPropertyBag(ResourceLoaderTest.class.getName());
        assertNotNull(bag1);
        assertEquals(bag, bag1);
        System.setProperty(ResourceLoader.PREFIX_PROPERTY_NAME, "testConfig1");
        bag1 = ResourceLoader.getPropertyBag(ResourceLoaderTest.class.getName());
        assertNotNull(bag1);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("x", "6");
        map.put("y", "8");
        map.put("a", "3");
        assertEquals(new PropertyBag(map), bag1);
        if (oldProperty == null) {
            System.clearProperty(ResourceLoader.PREFIX_PROPERTY_NAME);
        }
        else {
            System.setProperty(ResourceLoader.PREFIX_PROPERTY_NAME, oldProperty);
        }
    }

}
