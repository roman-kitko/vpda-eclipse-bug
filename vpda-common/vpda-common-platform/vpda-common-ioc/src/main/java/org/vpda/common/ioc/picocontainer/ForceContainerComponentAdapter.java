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

import java.io.Serializable;
import java.lang.reflect.Type;

import org.picocontainer.ComponentAdapter;
import org.picocontainer.PicoCompositionException;
import org.picocontainer.PicoContainer;
import org.picocontainer.PicoVisitor;

final class ForceContainerComponentAdapter<T> implements ComponentAdapter<T>, Serializable {
    private static final long serialVersionUID = 6376217679944731585L;
    private final ComponentAdapter<T> delegate;
    private final PicoContainer forceContainer;

    ForceContainerComponentAdapter(PicoContainer forceContainer, ComponentAdapter<T> delegate) {
        super();
        this.delegate = delegate;
        this.forceContainer = forceContainer;
    }

    @Override
    public Object getComponentKey() {
        return delegate.getComponentKey();
    }

    @Override
    public Class<? extends T> getComponentImplementation() {
        return delegate.getComponentImplementation();
    }

    @SuppressWarnings("deprecation")
    @Override
    public T getComponentInstance(PicoContainer container) throws PicoCompositionException {
        return delegate.getComponentInstance(forceContainer);
    }

    @Override
    public T getComponentInstance(PicoContainer container, Type into) throws PicoCompositionException {
        return delegate.getComponentInstance(forceContainer, into);
    }

    @Override
    public void verify(PicoContainer container) throws PicoCompositionException {
        delegate.verify(forceContainer);
    }

    @Override
    public void accept(PicoVisitor visitor) {
        delegate.accept(visitor);
    }

    @Override
    public ComponentAdapter<T> getDelegate() {
        return delegate.getDelegate();
    }

    @Override
    public <U extends ComponentAdapter> U findAdapterOfType(Class<U> adapterType) {
        return delegate.findAdapterOfType(adapterType);
    }

    @Override
    public String getDescriptor() {
        return delegate.getDescriptor();
    }

}
