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

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Properties;

import org.picocontainer.ComponentAdapter;
import org.picocontainer.Disposable;
import org.picocontainer.MutablePicoContainer;
import org.picocontainer.NameBinding;
import org.picocontainer.Parameter;
import org.picocontainer.PicoContainer;
import org.picocontainer.PicoVisitor;
import org.picocontainer.Startable;
import org.picocontainer.lifecycle.LifecycleState;
import org.vpda.internal.common.util.Assert;

/**
 * @author kitko
 *
 */
public final class ChainPicoContainer implements MutablePicoContainer, Serializable {
    private static final long serialVersionUID = -3946981137020840438L;

    private final MutablePicoContainer mutableDelegate;

    private final List<PicoContainer> chain;

    /**
     * Creates Chained pico container
     */
    public ChainPicoContainer() {
        this.mutableDelegate = new ChangeAdapterContainer(this);
        chain = new ArrayList<PicoContainer>();
    }

    /**
     * Creates chained container with mutable delegate
     * 
     * @param mutableDelegate
     */
    public ChainPicoContainer(MutablePicoContainer mutableDelegate) {
        this.mutableDelegate = Assert.isNotNullArgument(mutableDelegate, "mutableDelegate");
        chain = new ArrayList<PicoContainer>();
    }

    @Override
    public ComponentAdapter<?> getComponentAdapter(final Object componentKey) {
        ComponentAdapter<?> adapter = mutableDelegate.getComponentAdapter(componentKey);
        if (adapter == null) {
            for (PicoContainer container : chain) {
                adapter = container.getComponentAdapter(componentKey);
                if (adapter != null) {
                    break;
                }
            }
        }
        return adapter;
    }

    /**
     * Add container to chain
     * 
     * @param container
     */
    public void addDependendsOnContainer(PicoContainer container) {
        if (container == this) {
            throw new IllegalArgumentException("Cannot depend on itself");
        }
        if (!this.chain.contains(container)) {
            this.chain.add(container);
        }
    }

    @Override
    public Object getComponent(Object componentKeyOrType) {
        Object component = mutableDelegate.getComponent(componentKeyOrType);
        if (component != null) {
            return component;
        }
        for (PicoContainer c : chain) {
            component = c.getComponent(componentKeyOrType);
            if (component != null) {
                return component;
            }
        }
        return null;
    }

    @Override
    public Object getComponent(Object componentKeyOrType, Type into) {
        Object component = mutableDelegate.getComponent(componentKeyOrType, into);
        if (component != null) {
            return component;
        }
        for (PicoContainer c : chain) {
            component = c.getComponent(componentKeyOrType, into);
            if (component != null) {
                return component;
            }
        }
        return null;
    }

    @Override
    public <T> T getComponent(Class<T> componentType) {
        T component = mutableDelegate.getComponent(componentType);
        if (component != null) {
            return component;
        }
        for (PicoContainer c : chain) {
            component = c.getComponent(componentType);
            if (component != null) {
                return component;
            }
        }
        return null;
    }

    @Override
    public <T> T getComponent(Class<T> componentType, Class<? extends Annotation> binding) {
        T component = mutableDelegate.getComponent(componentType, binding);
        if (component != null) {
            return component;
        }
        for (PicoContainer c : chain) {
            component = c.getComponent(componentType, binding);
            if (component != null) {
                return component;
            }
        }
        return null;
    }

    @Override
    public List<Object> getComponents() {
        List<Object> components = new ArrayList<Object>();
        components.addAll(mutableDelegate.getComponents());
        for (PicoContainer c : chain) {
            components.addAll(c.getComponents());
        }
        return components;
    }

    @Override
    public PicoContainer getParent() {
        return mutableDelegate.getParent();
    }

    @Override
    public <T> ComponentAdapter<T> getComponentAdapter(Class<T> componentType, NameBinding componentNameBinding) {
        ComponentAdapter<T> adapter = mutableDelegate.getComponentAdapter(componentType, componentNameBinding);
        if (adapter != null) {
            return adapter;
        }
        for (PicoContainer c : chain) {
            adapter = c.getComponentAdapter(componentType, componentNameBinding);
            if (adapter != null) {
                return adapter;
            }
        }
        return null;
    }

    @Override
    public <T> ComponentAdapter<T> getComponentAdapter(Class<T> componentType, Class<? extends Annotation> binding) {
        ComponentAdapter<T> adapter = mutableDelegate.getComponentAdapter(componentType, binding);
        if (adapter != null) {
            return adapter;
        }
        for (PicoContainer c : chain) {
            adapter = c.getComponentAdapter(componentType, binding);
            if (adapter != null) {
                return adapter;
            }
        }
        return null;
    }

    @Override
    public Collection<ComponentAdapter<?>> getComponentAdapters() {
        Collection<ComponentAdapter<?>> adapters = new ArrayList<ComponentAdapter<?>>();
        adapters.addAll(mutableDelegate.getComponentAdapters());
        for (PicoContainer c : chain) {
            adapters.addAll(c.getComponentAdapters());
        }
        return adapters;

    }

    @Override
    public <T> List<ComponentAdapter<T>> getComponentAdapters(Class<T> componentType) {
        List<ComponentAdapter<T>> adapters = new ArrayList<ComponentAdapter<T>>();
        adapters.addAll(mutableDelegate.getComponentAdapters(componentType));
        for (PicoContainer c : chain) {
            adapters.addAll(c.getComponentAdapters(componentType));
        }
        return adapters;
    }

    @Override
    public <T> List<ComponentAdapter<T>> getComponentAdapters(Class<T> componentType, Class<? extends Annotation> binding) {
        List<ComponentAdapter<T>> adapters = new ArrayList<ComponentAdapter<T>>();
        adapters.addAll(mutableDelegate.getComponentAdapters(componentType, binding));
        for (PicoContainer c : chain) {
            adapters.addAll(c.getComponentAdapters(componentType, binding));
        }
        return adapters;
    }

    @Override
    public <T> List<T> getComponents(Class<T> componentType) {
        List<T> components = new ArrayList<T>();
        components.addAll(mutableDelegate.getComponents(componentType));
        for (PicoContainer c : chain) {
            components.addAll(c.getComponents(componentType));
        }
        return components;
    }

    @Override
    public void accept(PicoVisitor visitor) {
        mutableDelegate.accept(visitor);
        for (PicoContainer c : chain) {
            c.accept(visitor);
        }
    }

    @Override
    public void start() {
        mutableDelegate.start();
        for (PicoContainer c : chain) {
            if (c instanceof Startable) {
                ((Startable) c).start();
            }
        }
    }

    @Override
    public void stop() {
        mutableDelegate.stop();
        for (PicoContainer c : chain) {
            if (c instanceof Startable) {
                ((Startable) c).stop();
            }
        }
    }

    @Override
    public void dispose() {
        mutableDelegate.dispose();
        for (PicoContainer child : chain) {
            if (child instanceof Disposable) {
                ((Disposable) child).dispose();
            }
        }
    }

    @Override
    public MutablePicoContainer addComponent(Object componentKey, Object componentImplementationOrInstance, Parameter... parameters) {
        mutableDelegate.addComponent(componentKey, componentImplementationOrInstance, parameters);
        return this;
    }

    @Override
    public MutablePicoContainer addComponent(Object implOrInstance) {
        mutableDelegate.addComponent(implOrInstance);
        return this;
    }

    @Override
    public MutablePicoContainer addConfig(String name, Object val) {
        mutableDelegate.addConfig(name, val);
        return this;
    }

    @Override
    public MutablePicoContainer addAdapter(ComponentAdapter<?> componentAdapter) {
        mutableDelegate.addAdapter(componentAdapter);
        return this;
    }

    @Override
    public <T> ComponentAdapter<T> removeComponent(Object componentKey) {
        return mutableDelegate.removeComponent(componentKey);
    }

    @Override
    public <T> ComponentAdapter<T> removeComponentByInstance(T componentInstance) {
        return mutableDelegate.removeComponentByInstance(componentInstance);
    }

    @Override
    public MutablePicoContainer makeChildContainer() {
        return mutableDelegate.makeChildContainer();
    }

    @Override
    public MutablePicoContainer addChildContainer(PicoContainer child) {
        mutableDelegate.addChildContainer(child);
        return this;
    }

    @Override
    public boolean removeChildContainer(PicoContainer child) {
        return mutableDelegate.removeChildContainer(child);
    }

    @Override
    public MutablePicoContainer change(Properties... properties) {
        mutableDelegate.change(properties);
        return this;
    }

    @Override
    public MutablePicoContainer as(Properties... properties) {
        mutableDelegate.as(properties);
        return this;
    }

    @Override
    public void setName(String name) {
        mutableDelegate.setName(name);

    }

    @Override
    public void setLifecycleState(LifecycleState lifecycleState) {
        mutableDelegate.setLifecycleState(lifecycleState);
    }

    @Override
    public LifecycleState getLifecycleState() {
        return mutableDelegate.getLifecycleState();
    }

    @Override
    public String getName() {
        return mutableDelegate.getName();
    }

    @Override
    public String toString() {
        return getName();
    }

}
