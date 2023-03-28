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
package org.vpda.common.ioc.picocontainer;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.picocontainer.PicoContainer;
import org.picocontainer.script.xml.XMLContainerBuilder;

/** Test */
public class ListXMLComponentInstanceFactoryTest {

    /** Test */
    @Test
    public void testListWithElementFactory() {
        XMLContainerBuilder builder = new XMLContainerBuilder(getClass().getResource("ListXMLComponentInstanceFactoryTest.xml"), getClass().getClassLoader());
        PicoContainer pico = builder.buildContainer(null, "test", false);
        @SuppressWarnings("unchecked")
        List<Class<?>> list1 = (List) pico.getComponent("list1");
        assertEquals(String.class, list1.get(0));
        assertEquals(Long.class, list1.get(1));
    }

    /** Test */
    @Test
    public void testListWithItemFactory() {
        XMLContainerBuilder builder = new XMLContainerBuilder(getClass().getResource("ListXMLComponentInstanceFactoryTest.xml"), getClass().getClassLoader());
        PicoContainer pico = builder.buildContainer(null, "test", false);
        @SuppressWarnings("unchecked")
        List<Class<?>> list2 = (List) pico.getComponent("list2");
        assertEquals("Hello", list2.get(0));
        assertEquals(String.class, list2.get(1));
        assertEquals(Long.valueOf(14), list2.get(2));
    }

}
