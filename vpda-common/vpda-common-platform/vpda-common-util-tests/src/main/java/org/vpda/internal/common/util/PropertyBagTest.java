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
package org.vpda.internal.common.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.Collections;
import java.util.Date;

import org.junit.jupiter.api.Test;

/**
 * @author kitko
 *
 */
public class PropertyBagTest {

    /**
     * Test method for {@link org.vpda.internal.common.util.PropertyBag#hashCode()}.
     */
    @Test
    public final void testHashCode() {
        int hash1 = new PropertyBag(Collections.<String, Object>emptyMap()).hashCode();
        int hash2 = new PropertyBag(CollectionUtil.mapFromPairs(Pair.<String, Object>create("A", "B"))).hashCode();
        int hash3 = new PropertyBag(CollectionUtil.mapFromPairs(Pair.<String, Object>create("C", "D"))).hashCode();
        assertTrue(hash1 != hash2 && hash1 != hash3 && hash2 != hash3);
    }

    /**
     * Test method for {@link org.vpda.internal.common.util.PropertyBag#emptyBag()}.
     */
    @Test
    public final void testEmptyBag() {
        assertEquals(0, PropertyBag.emptyBag().size());
        assertTrue(PropertyBag.emptyBag().isEmpty());
    }

    /**
     * Test method for
     * {@link org.vpda.internal.common.util.PropertyBag#getValue(java.lang.String, java.lang.Class)}.
     */
    @Test
    public final void testGetValueStringClassOfT() {
        PropertyBag bag = new PropertyBag(CollectionUtil.mapFromPairs(Pair.<String, Object>create("A", "B"), Pair.<String, Object>create("C", 2L), Pair.<String, Object>create("D", "4")));
        assertEquals("B", bag.getValue("A", String.class));
        assertEquals(new Long(2), bag.getValue("C", Long.class));
        assertEquals(new Long(4), bag.getValue("D", Long.class));
        try {
            bag.getValue("A", Date.class);
            fail();
        }
        catch (ClassCastException e) {
        }
        assertNull(bag.getValue("E", Long.class));
    }

    /**
     * Test method
     */
    @Test
    public final void testGetValueStringClassOfTTArray() {
        PropertyBag bag = new PropertyBag(CollectionUtil.mapFromPairs(Pair.<String, Object>create("A", "B"), Pair.<String, Object>create("C", 2L)));
        assertEquals("B", bag.getValue("A", String.class, "AA"));
        assertEquals(new Long(2), bag.getValue("C", Long.class, 3L));
        assertEquals(new Long(3), bag.getValue("D", Long.class, 3L));
    }

    /**
     * Test method for
     * {@link org.vpda.internal.common.util.PropertyBag#getRequiredValue(java.lang.String, java.lang.Class)}.
     */
    @Test
    public final void testGetRequiredValue() {
        PropertyBag bag = new PropertyBag(CollectionUtil.mapFromPairs(Pair.<String, Object>create("A", "B")));
        assertEquals("B", bag.getRequiredValue("A", String.class));
        try {
            bag.getRequiredValue("B", String.class);
            fail();
        }
        catch (RuntimeException e) {
        }

    }

    /**
     * Test method for
     * {@link org.vpda.internal.common.util.PropertyBag#containsKey(java.lang.String)}.
     */
    @Test
    public final void testContainsKey() {
        PropertyBag bag = new PropertyBag(CollectionUtil.mapFromPairs(Pair.<String, Object>create("A", "B"), Pair.<String, Object>create("C", null)));
        assertTrue(bag.containsKey("A"));
        assertTrue(bag.containsKey("C"));
        assertFalse(bag.containsKey("D"));
    }

    /**
     * Test method for {@link org.vpda.internal.common.util.PropertyBag#isEmpty()}.
     */
    @Test
    public final void testIsEmpty() {
        PropertyBag bag = new PropertyBag(CollectionUtil.mapFromPairs(Pair.<String, Object>create("A", "B"), Pair.<String, Object>create("C", null)));
        assertFalse(bag.isEmpty());
        assertTrue(PropertyBag.emptyBag().isEmpty());
    }

    /**
     * Test method for {@link org.vpda.internal.common.util.PropertyBag#size()}.
     */
    @Test
    public final void testSize() {
        PropertyBag bag = new PropertyBag(CollectionUtil.mapFromPairs(Pair.<String, Object>create("A", "B"), Pair.<String, Object>create("C", null)));
        assertEquals(2, bag.size());
        assertEquals(0, PropertyBag.emptyBag().size());
    }

    /**
     * Test method for {@link org.vpda.internal.common.util.PropertyBag#keys()}.
     */
    @Test
    public final void testKeys() {
        PropertyBag bag = new PropertyBag(CollectionUtil.mapFromPairs(Pair.<String, Object>create("A", "B"), Pair.<String, Object>create("C", null)));
        assertEquals(CollectionUtil.newSet("A", "C"), bag.keys());
    }

    /**
     * Test method for
     * {@link org.vpda.internal.common.util.PropertyBag#equals(java.lang.Object)}.
     */
    @Test
    public final void testEqualsObject() {
        PropertyBag bag1 = new PropertyBag(CollectionUtil.mapFromPairs(Pair.<String, Object>create("A", "B"), Pair.<String, Object>create("C", null)));
        PropertyBag bag2 = new PropertyBag(CollectionUtil.mapFromPairs(Pair.<String, Object>create("A", "B"), Pair.<String, Object>create("C", null)));
        PropertyBag bag3 = new PropertyBag(CollectionUtil.mapFromPairs(Pair.<String, Object>create("A", "B"), Pair.<String, Object>create("C", "D")));
        assertEquals(bag1, bag2);
        assertFalse(bag1.equals(bag3));
        assertFalse(bag2.equals(bag3));
    }

}
