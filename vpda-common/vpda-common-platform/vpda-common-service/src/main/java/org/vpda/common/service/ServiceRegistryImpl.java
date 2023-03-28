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
package org.vpda.common.service;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 * Default holder of registered services
 * 
 * @author kitko
 *
 */
@ServiceInfo(kind = ServiceKind.Stateless, clientExportTypes = { ServiceRegistry.class })
public final class ServiceRegistryImpl implements MuttableServiceRegistry, Serializable {
    private static final long serialVersionUID = -6908104084660627008L;
    private final Map<Class<? extends Service>, Service> services;
    private final Map<Class<? extends Service>, ServiceFactory> factories;

    /**
     * Creates empty ServiceRegistryImpl
     */
    public ServiceRegistryImpl() {
        this.services = new HashMap<Class<? extends Service>, Service>();
        this.factories = new HashMap<>();
    }

    /**
     * Creates ServiceRegistryImpl and register services from map
     * 
     * @param <T>
     * @param services
     */
    public <T extends Service> ServiceRegistryImpl(Map<Class<T>, T> services) {
        this();
        for (Map.Entry<Class<T>, T> entry : services.entrySet()) {
            registerService(entry.getKey(), entry.getValue());
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends Service> T getService(Class<T> serviceClass) {
        T service = (T) services.get(serviceClass);
        if (service != null) {
            return service;
        }
        ServiceFactory<T> factory = factories.get(serviceClass);
        if (factory != null) {
            service = factory.createService(EmptyServiceEnvironment.getInstance());
        }
        return service;
    }

    @Override
    public Collection<Class<? extends Service>> getServicesKeys() {
        HashSet<Class<? extends Service>> set = new HashSet<Class<? extends Service>>(services.keySet());
        set.addAll(factories.keySet());
        return set;
    }

    @Override
    public <T extends Service> void registerService(Class<T> serviceClass, T service) {
        services.put(serviceClass, service);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void registerNotRegisteredServices(ServiceRegistry sr) {
        for (Class clazz : sr.getServicesKeys()) {
            if (!services.containsKey(clazz)) {
                registerService(clazz, sr.getService(clazz));
            }
        }
    }

    @Override
    public <T extends Service> void registerServiceFactory(ServiceFactory<T> factory) {
        factories.put(factory.getServiceClass(), factory);
    }

    @Override
    public <T extends Service> T getService(Class<T> serviceClass, ServiceEnvironment env) {
        @SuppressWarnings("unchecked")
        T service = (T) services.get(serviceClass);
        if (service != null) {
            return service;
        }
        @SuppressWarnings("unchecked")
        ServiceFactory<T> factory = factories.get(serviceClass);
        if (factory != null) {
            service = factory.createService(env);
        }
        return service;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T extends Service> ServiceFactory<T> getFactory(Class<T> serviceClass) {
        return factories.get(serviceClass);
    }

}
