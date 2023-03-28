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
import org.picocontainer.ComponentMonitor;
import org.picocontainer.LifecycleStrategy;
import org.picocontainer.Parameter;
import org.picocontainer.PicoCompositionException;
import org.picocontainer.PicoContainer;
import org.picocontainer.PicoVisitor;

/**
 * Factory for {@link PicoBuilderComponentAdapter}
 * 
 * @author kitko
 *
 */
public final class PicoBuilderComponentAdapterFactory implements ComponentFactory {

    @Override
    public void accept(PicoVisitor visitor) {
        visitor.visitComponentFactory(this);
    }

    @Override
    public <T> ComponentAdapter<T> createComponentAdapter(ComponentMonitor componentMonitor, LifecycleStrategy lifecycleStrategy, Properties componentProperties, Object componentKey,
            Class<T> componentImplementation, Parameter... parameters) throws PicoCompositionException {
        return new PicoBuilderComponentAdapter<T>(componentKey, componentImplementation, parameters, componentMonitor, false);
    }

    @Override
    public void verify(PicoContainer container) {
    }

}
