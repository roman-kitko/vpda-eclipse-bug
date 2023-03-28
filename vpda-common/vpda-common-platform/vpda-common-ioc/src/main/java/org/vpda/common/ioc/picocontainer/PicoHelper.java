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

import org.picocontainer.ComponentAdapter;
import org.picocontainer.MutablePicoContainer;
import org.picocontainer.Parameter;
import org.picocontainer.PicoContainer;
import org.vpda.common.util.exceptions.VPDARuntimeException;

/**
 * This is helper class for common operations with pico containers
 * 
 * @author kitko
 * 
 */
public final class PicoHelper {
    private PicoHelper() {
    }

    /**
     * Retrieves component by class from container If no component is resolved
     * throws {@link RuntimeException}
     * 
     * @param <T>
     * @param container
     * @param componentType
     * @return component, never null
     */
    public static <T> T getMandatoryComponent(PicoContainer container, Class<T> componentType) {
        T t = container.getComponent(componentType);
        if (t == null) {
            throw new VPDARuntimeException("Component with key not registered in container : " + componentType);
        }
        return t;
    }

    /**
     * Retrieves component by class from container If no component is resolved
     * throws {@link RuntimeException}
     * 
     * @param <T>
     * @param container
     * @param componentType
     * @param key
     * @return component
     */
    public static <T> T getMandatoryComponent(PicoContainer container, Class<T> componentType, Object key) {
        Object o = container.getComponent(key);
        if (o == null) {
            throw new VPDARuntimeException("Component with key not registered in container : " + key);
        }
        if (!componentType.isInstance(o)) {
            throw new VPDARuntimeException("Component is of type [" + o.getClass() + "] ,not comaptible to [" + componentType + "]");
        }
        return componentType.cast(o);
    }

    /**
     * Return typed component
     * 
     * @param <T>
     * @param container
     * @param componentType
     * @param key
     * @return null if component not registered by key or component
     */
    public static <T> T getTypedComponent(PicoContainer container, Class<T> componentType, Object key) {
        Object o = container.getComponent(key);
        if (o == null) {
            return null;
        }
        if (!componentType.isInstance(o)) {
            throw new VPDARuntimeException("Component is of type [" + o.getClass() + "] ,not comaptible to [" + componentType + "]");
        }
        return componentType.cast(o);
    }

    /**
     * Gets adapter from container by key if no adapter is registered by class
     * throws RuntimeException
     * 
     * @param container
     * @param componentKey
     * @return adapter, never null
     */
    public static ComponentAdapter<?> getMandatoryComponentAdapter(PicoContainer container, Object componentKey) {
        ComponentAdapter<?> adapter = container.getComponentAdapter(componentKey);
        if (adapter == null) {
            throw new VPDARuntimeException("Adapter with key not registered in container : " + componentKey);
        }
        return adapter;
    }

    /**
     * Register component by key, if it is not already present
     * 
     * @param container
     * @param componentKey
     * @param componentImplementationOrInstance
     * @param parameters
     */
    public static void registerComponentIfNotPresent(MutablePicoContainer container, Object componentKey, Object componentImplementationOrInstance, Parameter... parameters) {
        if (container.getComponentAdapter(componentKey) == null) {
            container.addComponent(componentKey, componentImplementationOrInstance, parameters);
        }
    }

    /**
     * Retrieves component from container or returns default one
     * 
     * @param <T>
     * @param container
     * @param clazz
     * @param def
     * @return component from container or default value if no component is
     *         registered by class in container
     */
    public static <T> T getComponentWithDefault(PicoContainer container, Class<T> clazz, T def) {
        T result = container.getComponent(clazz);
        return result != null ? result : def;
    }

    /**
     * Register adapter by its key to container if not already present
     * 
     * @param <T>
     * @param container
     * @param adapter
     */
    public static <T> void registerAdapterIfNotPresent(MutablePicoContainer container, ComponentAdapter<T> adapter) {
        if (container.getComponentAdapter(adapter.getComponentKey()) == null) {
            container.addAdapter(adapter);
        }
    }

    /**
     * Remove component registered by key and register passed one
     * 
     * @param container
     * @param componentKey
     * @param componentImplementationOrInstance
     * @param parameters
     */
    public static void registerComponentOverride(MutablePicoContainer container, Object componentKey, Object componentImplementationOrInstance, Parameter... parameters) {
        container.removeComponent(componentKey);
        container.addComponent(componentKey, componentImplementationOrInstance, parameters);
    }

    /**
     * Remove adapter registered by its key and register passed one
     * 
     * @param <T>
     * @param container
     * @param adapter
     */
    public static <T> void registerAdapterOverrride(MutablePicoContainer container, ComponentAdapter<T> adapter) {
        container.removeComponent(adapter.getComponentKey());
        container.addAdapter(adapter);
    }

}
