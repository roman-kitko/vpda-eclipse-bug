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
package org.vpda.common.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.vpda.internal.common.util.Assert;
import org.vpda.internal.common.util.PropertyBag;

/** Test for {@link PropertyBag} */
public class PropertyBagTest {

    private Map<String, Object> singleItemMap(String key, Object value) {
        Map<String, Object> map = new HashMap<String, Object>(1);
        map.put(key, value);
        return map;
    }

    /** test */
    @Test
    public void testEmptyBag() {
        assertTrue(PropertyBag.emptyBag().isEmpty());
        assertSame(PropertyBag.emptyBag(), PropertyBag.emptyBag());
    }

    /** test */
    @Test
    public void testGetValue() {
        PropertyBag bag = new PropertyBag(Collections.<String, Object>emptyMap());
        assertNull(bag.getValue("key", String.class));
        bag = new PropertyBag(singleItemMap("key", "value"));
        assertEquals("value", bag.getValue("key", String.class));
        bag = new PropertyBag(singleItemMap("key", new Integer(7)));
        try {
            bag.getValue("key", String.class);
            fail();
        }
        catch (ClassCastException e) {
        }
        bag = new PropertyBag(singleItemMap("key", "value"));
        assertEquals("value1", bag.getValue("key1", String.class, "value1"));
        bag = new PropertyBag(singleItemMap("key", null));
        assertNull(bag.getValue("key", String.class, "value1"));
        assertEquals("val2", bag.getValue("key2", String.class, null, "val2"));
    }

    /** test */
    @Test
    public void testGetStringValue() {
        PropertyBag bag = new PropertyBag(Collections.<String, Object>emptyMap());
        assertNull(bag.getStringValue("key"));
        bag = new PropertyBag(singleItemMap("key", "value"));
        assertEquals("value", bag.getStringValue("key"));
        bag = new PropertyBag(singleItemMap("key", Integer.valueOf(7)));
        try {
            bag.getStringValue("key");
            fail();
        }
        catch (ClassCastException e) {
        }
        assertEquals("val2", bag.getStringValue("key2", null, "val2"));
    }

    /** test */
    @Test
    public void testGetRequiredValue() {
        PropertyBag bag = new PropertyBag(singleItemMap("key", "value"));
        assertEquals("value", bag.getRequiredValue("key", String.class));
        try {
            bag.getRequiredValue("key1", String.class);
            fail();
        }
        catch (RuntimeException e) {
        }
    }

    /** test */
    @Test
    public void testGetRequiredStringValue() {
        PropertyBag bag = new PropertyBag(singleItemMap("key", "value"));
        assertEquals("value", bag.getRequiredStringValue("key"));
        try {
            bag.getRequiredStringValue("key1");
            fail();
        }
        catch (RuntimeException e) {
        }
    }

    /** test */
    @Test
    public void testContainsKey() {
        PropertyBag bag = new PropertyBag(singleItemMap("key", "value"));
        assertTrue(bag.containsKey("key"));
        assertFalse(bag.containsKey("key1"));
    }

    /** test */
    @Test
    public void testIsEmpty() {
        assertTrue(new PropertyBag(Collections.<String, Object>emptyMap()).isEmpty());
        assertFalse(new PropertyBag(singleItemMap("key", "value")).isEmpty());
    }

    /** test */
    @Test
    public void testSize() {
        PropertyBag bag = new PropertyBag(singleItemMap("key", "value"));
        assertEquals(1, bag.size());
    }

    /** test */
    @Test
    public void testKeys() {
        PropertyBag bag = new PropertyBag(singleItemMap("key", "value"));
        assertEquals(Collections.singleton("key"), bag.keys());
    }

}
