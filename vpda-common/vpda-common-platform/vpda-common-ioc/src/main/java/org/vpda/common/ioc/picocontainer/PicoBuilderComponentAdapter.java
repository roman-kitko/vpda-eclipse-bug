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

import java.lang.reflect.Type;

import org.picocontainer.ComponentMonitor;
import org.picocontainer.Parameter;
import org.picocontainer.Parameter.Resolver;
import org.picocontainer.PicoCompositionException;
import org.picocontainer.PicoContainer;
import org.picocontainer.injectors.AbstractInjector;
import org.picocontainer.monitors.NullComponentMonitor;
import org.vpda.common.util.Builder;

/**
 * Adapter that creates instance using {@link Builder}
 * 
 * @author kitko
 *
 * @param <T>
 */
public final class PicoBuilderComponentAdapter<T> extends AbstractInjector<T> {

    /**
     * @param componentKey
     * @param componentImplementation
     * @param parameters
     * @param monitor
     * @param useNames
     */
    public PicoBuilderComponentAdapter(Object componentKey, Class<?> componentImplementation, Parameter[] parameters, ComponentMonitor monitor, boolean useNames) {
        super(componentKey, componentImplementation, parameters, monitor, useNames);
    }

    /**
     * @param componentKey
     * @param componentImplementation
     * @param parameters
     */
    public PicoBuilderComponentAdapter(Object componentKey, Class<?> componentImplementation, Parameter[] parameters) {
        super(componentKey, componentImplementation, parameters, new NullComponentMonitor(), true);
    }

    private static final long serialVersionUID = 7542530621742552343L;

    @SuppressWarnings("unchecked")
    @Override
    public T getComponentInstance(PicoContainer container, Type into) throws PicoCompositionException {
        Resolver resolve = parameters[0].resolve(container, this, null, Builder.class, null, false, null);
        if (resolve.isResolved()) {
            Builder<T> builder = (Builder<T>) resolve.resolveInstance();
            return builder.build();
        }
        throw new PicoCompositionException("Cannot create class [" + getComponentImplementation() + " using builder");
    }

    @Override
    public String getDescriptor() {
        return null;
    }

    @Override
    public void verify(PicoContainer container) throws PicoCompositionException {
    }

}
