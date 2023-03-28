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

import java.net.URL;

import org.junit.jupiter.api.Test;
import org.picocontainer.DefaultPicoContainer;
import org.picocontainer.Parameter;
import org.picocontainer.behaviors.PropertyApplicator;
import org.picocontainer.injectors.ConstructorInjector;
import org.picocontainer.parameters.ComponentParameter;
import org.picocontainer.parameters.ConstantParameter;
import org.picocontainer.script.xml.XMLContainerBuilder;

/** */
public class PicoBuilderComponentAdapterTest {
    /** Test adapter java */
    @Test
    public void testBuilderJava() {
        DefaultPicoContainer container = new DefaultPicoContainer();

        Builder builder = new Builder().setName("hello").setVal(66);
        container.addAdapter(new PicoBuilderComponentAdapter<>(MyClass.class, MyClass.class, new Parameter[] { new ConstantParameter(builder) }));
        MyClass component = container.getComponent(MyClass.class);
        assertEquals(builder.build(), component);

        container = new DefaultPicoContainer();
        PropertyApplicator<Builder> pa = new PropertyApplicator<>(new ConstructorInjector<>(Builder.class, Builder.class));
        pa.setProperty("val", "77");
        pa.setProperty("name", "world");
        container.addAdapter(pa);
        container.addAdapter(new PicoBuilderComponentAdapter<MyClass>(MyClass.class, MyClass.class, new Parameter[] { new ComponentParameter(Builder.class) }));
        component = container.getComponent(MyClass.class);
        assertEquals(new Builder().setName("world").setVal(77).build(), component);
    }

    /** */
    @Test
    public void testBuilderXml() {
        DefaultPicoContainer container = new DefaultPicoContainer();
        URL url = PicoBuilderComponentAdapterTest.class.getResource("picoBuilderAdapter.xml");
        XMLContainerBuilder xmlBuilder = new XMLContainerBuilder(url, PicoBuilderComponentAdapterTest.class.getClassLoader());
        xmlBuilder.populateContainer(container);
        MyClass component = container.getComponent(MyClass.class);
        assertEquals(new Builder().setName("Hello world").setVal(666).build(), component);

    }

}
