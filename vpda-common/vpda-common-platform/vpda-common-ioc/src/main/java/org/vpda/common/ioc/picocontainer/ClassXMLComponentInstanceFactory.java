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

import org.picocontainer.PicoClassNotFoundException;
import org.picocontainer.PicoContainer;
import org.picocontainer.script.xml.XMLComponentInstanceFactory;
import org.vpda.internal.common.util.Assert;
import org.w3c.dom.Element;

/**
 * Will create Class using class name
 * 
 * @author kitko
 *
 */
public final class ClassXMLComponentInstanceFactory implements XMLComponentInstanceFactory {

    @Override
    public Object makeInstance(PicoContainer container, Element element, ClassLoader classLoader) {
        String classname = Assert.isNotEmpty(element.getAttribute("classname"), "Must specify classname attribute");
        Class clazz = null;
        try {
            clazz = classLoader.loadClass(classname);
            return clazz;
        }
        catch (ClassNotFoundException e) {
            throw new PicoClassNotFoundException(classname, e);
        }
    }

}
