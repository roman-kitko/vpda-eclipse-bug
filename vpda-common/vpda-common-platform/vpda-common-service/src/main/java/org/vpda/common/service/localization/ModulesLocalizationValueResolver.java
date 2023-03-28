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
package org.vpda.common.service.localization;

import java.io.IOException;
import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicReference;

import org.vpda.common.context.TenementalContext;
import org.vpda.common.context.localization.LocKey;
import org.vpda.common.entrypoint.AppEntryPoint;
import org.vpda.common.entrypoint.AppEntryPointListenerSupport;
import org.vpda.common.entrypoint.Application;
import org.vpda.common.entrypoint.ConfigurationConstants;
import org.vpda.common.entrypoint.EntryPointListenerAdapter;
import org.vpda.common.entrypoint.Module;
import org.vpda.common.util.exceptions.VPDARuntimeException;

/**
 * {@link LocalizationValueResolver} that resolves value from url list. This is
 * list of classpath of each module with folder data/loc
 * 
 * @author kitko
 *
 */
public final class ModulesLocalizationValueResolver implements LocalizationValueResolver, Serializable {
    private static final long serialVersionUID = 758127946047384312L;
    private AtomicReference<LocalizationValueResolver> delegate = new AtomicReference<>();
    private final Application project;
    private final ConcurrentMap<Integer, URLsLocalizationValueResolver> uRLsLocalizationValueResolvers;

    @Override
    public String resolveValue(LocKey key, TenementalContext context) {
        if (delegate.get() != null) {
            return delegate.get().resolveValue(key, context);
        }
        int modulesHashCode = project.getModulesHashCode();
        URLsLocalizationValueResolver resolver = uRLsLocalizationValueResolvers.get(modulesHashCode);
        if (resolver == null) {
            List<URL> urls = findModulesURLS(project);
            resolver = new URLsLocalizationValueResolver(urls);
            URLsLocalizationValueResolver old = uRLsLocalizationValueResolvers.putIfAbsent(modulesHashCode, resolver);
            resolver = old != null ? old : resolver;
        }
        return resolver.resolveValue(key, context);
    }

    /**
     * Creates resolver with project We will iterate through all project modules and
     * add for each module data/loc url
     * 
     * @param project
     * @param appEntryPointListenerSupport
     */
    public ModulesLocalizationValueResolver(Application project, AppEntryPointListenerSupport appEntryPointListenerSupport) {
        if (project == null) {
            throw new IllegalArgumentException("Project argument is null");
        }
        this.project = project;
        this.uRLsLocalizationValueResolvers = new ConcurrentHashMap<>();
        appEntryPointListenerSupport.registerEntryPointListener(new EntryPointListenerAdapter() {
            @Override
            public void applicationEntryExit(AppEntryPoint entryPoint, Application app) {
                URLsLocalizationValueResolverCache cache = Objects.requireNonNullElseGet(app.getAllModulesContainer().getComponent(URLsLocalizationValueResolverCache.class),
                        () -> new EHCacheLikeURLsLocalizationValueResolverCache());
                List<URL> urls = findModulesURLS(app);
                ModulesLocalizationValueResolver.this.delegate.set(new URLsLocalizationValueResolver(urls, cache));
            }
        });
    }

    private List<URL> findModulesURLS(Application project) {
        List<URL> urls = new ArrayList<URL>();
        try {
            for (Module module : project.getModules()) {
                ClassLoader loader = module.getModuleClassloader();
                collectUrls(urls, module, loader);
            }
            collectUrls(urls, null, Thread.currentThread().getContextClassLoader());
        }
        catch (IOException e) {
            throw new VPDARuntimeException("Error gettings resources", e);
        }
        java.lang.Module javaModule = getClass().getModule();
        if(javaModule.isNamed()) {
           Set<java.lang.Module> allJavaModules = javaModule.getLayer().modules(); 
           collectDataLocFromJavaModules(allJavaModules, urls);
        }
        return urls;
    }

    private void collectDataLocFromJavaModules(Set<java.lang.Module> allJavaModules, List<URL> urls) {
        for(java.lang.Module javaModule : allJavaModules) {
            if (javaModule.getName().startsWith("java.") || javaModule.getName().startsWith("jdk.") || javaModule.getClassLoader() == null) {
                continue;
            }
            URL url = javaModule.getClassLoader().getResource(ConfigurationConstants.DATA_LOC_PATH);
            if(url != null && !urls.contains(url)) {
                urls.add(url);
            }
            String qualifiedPath = javaModule.getName().replace('.', '/') + "/" + ConfigurationConstants.DATA_LOC_PATH;
            url = javaModule.getClassLoader().getResource(qualifiedPath);
            if(url != null && !urls.contains(url)) {
                urls.add(url);
            }
        }
        
    }

    private void collectUrls(List<URL> urls, Module module, ClassLoader loader) throws IOException {
        Enumeration<URL> foundUrls = loader.getResources(ConfigurationConstants.DATA_LOC_PATH);
        while (foundUrls.hasMoreElements()) {
            URL url = foundUrls.nextElement();
            if (!urls.contains(url)) {
                urls.add(url);
            }
        }
        if (module != null && module.getJavaModule() != null && module.getJavaModule().isNamed()) {
            String name = module.getJavaModule().getName();
            String newPath = name.replace('.', '/') + "/" + ConfigurationConstants.DATA_LOC_PATH;
            foundUrls = loader.getResources(newPath);
            while (foundUrls.hasMoreElements()) {
                URL url = foundUrls.nextElement();
                if (!urls.contains(url)) {
                    urls.add(url);
                }
            }
        }
    }

}
