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
package org.vpda.common.ioc.picocontainer;

import java.util.ArrayList;
import java.util.List;

import org.picocontainer.PicoClassNotFoundException;
import org.picocontainer.PicoCompositionException;
import org.picocontainer.PicoContainer;
import org.picocontainer.behaviors.PropertyApplicator;
import org.picocontainer.script.xml.XMLComponentInstanceFactory;
import org.vpda.internal.common.util.StringUtil;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Will create list of items from xml element. Root list element can specify
 * 'itemFactory' attribute. That factory will be used by all items. Item
 * elements must be named 'item' and can override 'itemFactory' for that item.
 * If no factory is specified,
 * {@link PropertyApplicator#convert(String, String, ClassLoader)} is used to
 * create item
 * 
 * @author kitko
 *
 */
public final class ListXMLComponentInstanceFactory implements XMLComponentInstanceFactory {

    @Override
    public Object makeInstance(PicoContainer container, Element element, ClassLoader classLoader) {
        if (!"list".equals(element.getNodeName())) {
            throw new PicoCompositionException("ListXMLComponentInstanceFactory supports just list elements");
        }
        XMLComponentInstanceFactory itemFactory = createInstancefactory(element.getAttribute("itemFactory"), classLoader);
        List<Object> result = new ArrayList<Object>();
        NodeList nodes = element.getChildNodes();
        for (int i = 0; i < nodes.getLength(); i++) {
            Node n = nodes.item(i);
            if (n.getNodeType() == Node.ELEMENT_NODE) {
                if (!"item".equals(n.getNodeName())) {
                    throw new PicoCompositionException("ListXMLComponentInstanceFactory items supports just item elements");
                }
                Element itemElement = (Element) n;
                XMLComponentInstanceFactory itemFactoryi = StringUtil.isNotEmpty(itemElement.getAttribute("factory")) ? createInstancefactory(itemElement.getAttribute("factory"), classLoader)
                        : itemFactory;
                Object item = null;
                String skipFactory = itemElement.getAttribute("skipFactory");
                if ("true".equals(skipFactory)) {
                    itemFactoryi = null;
                }
                if (itemFactoryi != null) {
                    item = itemFactoryi.makeInstance(container, (Element) n, classLoader);
                }
                else {
                    if (StringUtil.isNotEmpty(itemElement.getAttribute("ref"))) {
                        item = container.getComponent(itemElement.getAttribute("ref"));
                    }
                    else if (StringUtil.isNotEmpty(itemElement.getAttribute("itemClass"))) {
                        String type = itemElement.getAttribute("itemClass");
                        if (StringUtil.isEmpty(itemElement.getAttribute("itemValue"))) {
                            throw new PicoCompositionException("ListXMLComponentInstanceFactory items must specify itemValue for itemClass");
                        }
                        String itemValue = itemElement.getAttribute("itemValue");
                        item = PropertyApplicator.convert(type, itemValue, classLoader);
                    }
                    else {
                        throw new PicoCompositionException("Cannot create item");
                    }
                }
                result.add(item);
            }
        }
        return result;
    }

    @SuppressWarnings("unchecked")
    private XMLComponentInstanceFactory createInstancefactory(String classname, ClassLoader classLoader) {
        if (StringUtil.isEmpty(classname)) {
            return null;
        }
        Class<XMLComponentInstanceFactory> itemfactoryClass = null;
        XMLComponentInstanceFactory itemFactory = null;
        try {
            itemfactoryClass = (Class<XMLComponentInstanceFactory>) classLoader.loadClass(classname);
        }
        catch (ClassNotFoundException e) {
            throw new PicoClassNotFoundException(classname, e);
        }
        try {
            itemFactory = itemfactoryClass.newInstance();
        }
        catch (Exception e) {
            throw new PicoCompositionException("Cannot create instance factory", e);
        }
        return itemFactory;

    }

}
