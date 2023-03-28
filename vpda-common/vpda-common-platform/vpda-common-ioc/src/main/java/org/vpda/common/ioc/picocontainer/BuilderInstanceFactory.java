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

import org.picocontainer.PicoContainer;
import org.picocontainer.script.xml.XMLComponentInstanceFactory;
import org.vpda.common.util.Builder;
import org.w3c.dom.Element;

/**
 * Will create component instance using builder pattern. Element must specify
 * real 'builderClassName' attribute and set builder properties as child nodes.
 * 
 * @author kitko
 *
 */
public final class BuilderInstanceFactory implements XMLComponentInstanceFactory {

    @Override
    public Object makeInstance(PicoContainer container, Element element, ClassLoader classLoader) {
        Builder<?> builder = (Builder<?>) new BuilderFactory().makeInstance(container, element, classLoader);
        return builder.build();
    }
}
