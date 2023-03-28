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
package org.vpda.common.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;
import org.vpda.common.dto.PropertyPath;

public class PropertyPathTest {

    @Test
    public void testGetPath() {
        assertEquals(Collections.singletonList("Employee"), PropertyPath.createRoot("Employee").getPath());
        assertEquals(Arrays.asList("Employee", "surname"), PropertyPath.createRoot("Employee").createChild("surname").getPath());
        assertEquals(Arrays.asList("Employee", "department", "code"), PropertyPath.createRoot("Employee").createChild("department").createChild("code").getPath());
    }

    @Test
    public void testGetLocalId() {
        assertEquals("Employee", PropertyPath.createRoot("Employee").getLocalId());
        assertEquals("surname", PropertyPath.createRoot("Employee").createChild("surname").getLocalId());
    }

    @Test
    public void testGetFullId() {
        assertEquals("Employee", PropertyPath.createRoot("Employee").getFullId());
        assertEquals("Employee.surname", PropertyPath.createRoot("Employee").createChild("surname").getFullId());
    }

    @Test
    public void testGetFullIdChar() {
        assertEquals("Employee", PropertyPath.createRoot("Employee").getFullId('/'));
        assertEquals("Employee/surname", PropertyPath.createRoot("Employee").createChild("surname").getFullId('/'));
    }

    @Test
    public void testGetParent() {
        PropertyPath parent = PropertyPath.createRoot("Employee");
        assertNull(parent.getParent());
        PropertyPath child = parent.createChild("surname");
        assertSame(parent, child.getParent());
    }

    @Test
    public void testCreateChild() {
    }

    @Test
    public void testCreateRoot() {
        PropertyPath emp = PropertyPath.createRoot("Employee");
        assertNull(emp.getParent());
        assertEquals("Employee", emp.getLocalId());
        assertEquals("Employee", emp.getFullId());
    }

}
