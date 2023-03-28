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

import java.util.Properties;

import org.picocontainer.ComponentAdapter;
import org.picocontainer.ComponentFactory;
import org.picocontainer.PicoClassNotFoundException;
import org.picocontainer.PicoContainer;
import org.picocontainer.behaviors.PropertyApplicator;
import org.picocontainer.injectors.AdaptingInjection;
import org.picocontainer.lifecycle.NullLifecycleStrategy;
import org.picocontainer.monitors.NullComponentMonitor;
import org.picocontainer.script.xml.BeanComponentInstanceFactory;
import org.picocontainer.script.xml.XMLComponentInstanceFactory;
import org.vpda.common.util.Builder;
import org.vpda.internal.common.util.Assert;
import org.vpda.internal.common.util.ClassUtil;
import org.vpda.internal.common.util.StringUtil;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Will create builder instance . Element must specify real 'builderClassName'
 * attribute and set builder properties as child nodes.
 * 
 * @author kitko
 *
 */
public final class BuilderFactory implements XMLComponentInstanceFactory {

    private static final String NAME_ATTRIBUTE = "name";

    @SuppressWarnings("unchecked")
    @Override
    public Object makeInstance(PicoContainer container, Element element, ClassLoader classLoader) {
        String builderClassName = Assert.isNotEmpty(element.getAttribute("builderClassName"), "Must specify builder class name attribute");
        PropertyApplicator propertyAdapter = new PropertyApplicator(createComponentAdapter(builderClassName, classLoader));
        java.util.Properties properties = createProperties(container, element.getChildNodes(), classLoader);
        propertyAdapter.setProperties(properties);
        Builder<?> builder = (Builder<?>) propertyAdapter.getComponentInstance(container, ComponentAdapter.NOTHING.class);
        return builder;
    }

    @SuppressWarnings("unchecked")
    private ComponentAdapter createComponentAdapter(String className, ClassLoader classLoader) {
        Class implementation = loadClass(classLoader, className);
        ComponentFactory factory = new AdaptingInjection();
        return factory.createComponentAdapter(new NullComponentMonitor(), new NullLifecycleStrategy(), new Properties(), className, implementation);
    }

    private Class<?> loadClass(final ClassLoader classLoader, final String className) {
        try {
            return classLoader.loadClass(className);
        }
        catch (ClassNotFoundException e) {
            throw new PicoClassNotFoundException(className, e);
        }
    }

    private java.util.Properties createProperties(PicoContainer container, NodeList nodes, ClassLoader classLoader) {
        java.util.Properties properties = new java.util.Properties();
        for (int i = 0; i < nodes.getLength(); i++) {
            Node n = nodes.item(i);
            if (n.getNodeType() == Node.ELEMENT_NODE) {
                Element itemElement = (Element) n;
                String name = itemElement.getNodeName();

                String mappedName = itemElement.getAttribute(NAME_ATTRIBUTE);
                name = StringUtil.isNotEmpty(mappedName) ? mappedName : name;
                String ref = itemElement.getAttribute("ref");
                if (StringUtil.isNotEmpty(ref)) {
                    Object item = container.getComponent(ref);
                    properties.put(name, item);
                }
                else if (getFirstElement(n) != null && "component-instance".equals(getFirstElement(n).getNodeName())) {
                    String factoryClassName = getFirstElement(n).getAttributes().getNamedItem("factory") != null ? getFirstElement(n).getAttributes().getNamedItem("factory").getNodeValue()
                            : BeanComponentInstanceFactory.class.getName();
                    XMLComponentInstanceFactory factory = ClassUtil.createInstance(factoryClassName, XMLComponentInstanceFactory.class, classLoader);
                    Node enumChild = getFirstElement(getFirstElement(n));
                    Object value = factory.makeInstance(container, (Element) enumChild, classLoader);
                    properties.put(name, value);
                }
                else {
                    String value = n.getFirstChild().getNodeValue();
                    properties.setProperty(name, value);
                }
            }
        }
        return properties;
    }

    private Node getFirstElement(Node n) {
        NodeList nodes = n.getChildNodes();
        for (int i = 0; i < nodes.getLength(); i++) {
            Node child = nodes.item(i);
            if (child.getNodeType() == Node.ELEMENT_NODE) {
                if (child instanceof Element) {
                    return child;
                }
                Node nextSibling = child.getNextSibling();
                if (nextSibling instanceof Element) {
                    return nextSibling;
                }
                while (nextSibling != null) {
                    if (nextSibling instanceof Element) {
                        return nextSibling;
                    }
                    nextSibling = nextSibling.getNextSibling();
                }
            }
        }
        return null;
    }

}
