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
package org.vpda.common.service.resources;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.lang.module.ModuleDescriptor;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collection;
import java.util.List;

import org.vpda.common.entrypoint.Module;
import org.vpda.internal.common.util.URLUtil;

/**
 * Creates {@link ModuleURLResolver} that will resolve url as subpath of
 * {@link Module#getModuleHomeURL()}
 * 
 * @author kitko
 *
 */
public final class ModuleHomeRelativeURLResolver implements ModuleURLResolver, Serializable {
    private static final long serialVersionUID = -190313803536000334L;
    private final String subPath;

    /**
     * Creates ModuleHomeRelativeURLModuleURLResolver
     * 
     * @param subPath
     */
    public ModuleHomeRelativeURLResolver(String subPath) {
        this.subPath = subPath;
    }

    /**
     * @return the subPath
     */
    public String getSubPath() {
        return subPath;
    }

    /**
     * Creates ModuleHomeRelativeURLModuleURLResolver
     * 
     * @param subPath
     * @return ModuleHomeRelativeURLModuleURLResolver
     */
    public static ModuleHomeRelativeURLResolver create(String subPath) {
        return new ModuleHomeRelativeURLResolver(subPath);
    }

    @Override
    public Collection<URL> resolveBaseURL(Module module) throws MalformedURLException {
        URL moduleHomeUrl = module.getModuleHomeURL();
        URL url1 = URLUtil.createRelativeURL(moduleHomeUrl, subPath);
        java.lang.Module javaModule = module.getClass().getModule();
        if (!javaModule.isNamed()) {
            Class<?> moduleClass = module.getClass();
            URL defaultClassUrl = moduleClass.getResource('/' + moduleClass.getName().replace('.', '/') + ".class");
            String removeString = moduleClass.getName().replace('.', '/') + ".class";
            String modulePath = defaultClassUrl.toExternalForm();
            modulePath = modulePath.substring(0, modulePath.length() - removeString.length());
            String moduleInfoPath = modulePath + "module-info.class";
            String javaModuleName = null;
            try {
                URL moduleInfoURL = new URL(moduleInfoPath);
                try (InputStream is = moduleInfoURL.openStream()) {
                    ModuleDescriptor descriptor = ModuleDescriptor.read(is);
                    javaModuleName = descriptor.name();
                }
            }
            catch (IOException e) {
                e.printStackTrace();
                return List.of(url1);
            }
            String qualifiedPath = javaModuleName.replace('.', '/') + "/" + subPath;
            URL url2 = URLUtil.createRelativeURL(new URL(modulePath), qualifiedPath);
            return List.of(url1, url2);
        }
        String moduleSubPath = javaModule.getName().replace('.', '/') + '/';
        URL createRelativeURL2 = URLUtil.createRelativeURL(moduleHomeUrl, moduleSubPath);
        URL url2 = URLUtil.createRelativeURL(createRelativeURL2, subPath);
        return List.of(url1, url2);
    }

    @Override
    public Collection<URL> resolveURLForJavaModule(java.lang.Module javaModule, String relativePath) throws MalformedURLException {
        String qualifiedPath = javaModule.getName().replace('.', '/') + '/' + subPath;
        if(qualifiedPath.charAt(qualifiedPath.length() - 1) != '/') {
            qualifiedPath = qualifiedPath + '/';
        }
        qualifiedPath = qualifiedPath + relativePath;
        URL resource = javaModule.getClassLoader().getResource(qualifiedPath);
        return resource != null ? singletonList(resource) : emptyList();
    }

}
