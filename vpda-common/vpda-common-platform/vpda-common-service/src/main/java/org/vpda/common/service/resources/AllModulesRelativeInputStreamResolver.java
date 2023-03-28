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

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Set;

import org.apache.commons.vfs2.FileSystemException;
import org.apache.commons.vfs2.VFS;
import org.vpda.common.entrypoint.Application;
import org.vpda.common.entrypoint.Module;
import org.vpda.internal.common.util.Assert;
import org.vpda.internal.common.util.URLUtil;

/**
 * Resolver that searches all modules from project and find first that can
 * resolve some url with relative path
 * 
 * @author kitko
 *
 */
public final class AllModulesRelativeInputStreamResolver implements RelativePathInputStreamResolver {

    private static final long serialVersionUID = 5780092469149373440L;
    private final String relativePath;
    private final ModuleURLResolver moduleURLResolver;
    private final Application project;

    @Override
    public URL resolveKey() throws IOException {
        return resolveKey(relativePath);
    }

    /**
     * Creates AllModulesRelativeInputStreamResolver
     * 
     * @param project
     * @param relativePath
     * @param moduleURLResolver
     */
    public AllModulesRelativeInputStreamResolver(Application project, String relativePath, ModuleURLResolver moduleURLResolver) {
        this.project = Assert.isNotNullArgument(project, "project");
        this.relativePath = Assert.isNotNullArgument(relativePath, "relativePath");
        this.moduleURLResolver = Assert.isNotNullArgument(moduleURLResolver, "moduleURLResolver");
    }

    /**
     * @return the relativePath
     */
    public String getRelativePath() {
        return relativePath;
    }

    @Override
    public InputStream resolveStream() throws IOException {
        return resolveKey().openStream();
    }

    @Override
    public URL resolveKey(String relativePath) throws IOException, ResourceException {
        String path = relativePath;
        for (Module module : project.getModules()) {
            for (URL baseURL : moduleURLResolver.resolveBaseURL(module)) {
                URL url = null;
                try {
                    url = URLUtil.createRelativeURL(baseURL, path);
                }
                catch (MalformedURLException e) {
                    throw new ResourceException(e);
                }
                try {
                    if (VFS.getManager().resolveFile(url.toExternalForm()).exists()) {
                        return url;
                    }
                }
                catch (FileSystemException e) {
                    throw new ResourceException("Cannot retrieve resource", e);
                }
            }
        }
        java.lang.Module javaModule = getClass().getModule();
        if(javaModule.isNamed() && javaModule.getLayer() != null) {
            Set<java.lang.Module> javaModules = javaModule.getLayer().modules();
            for (java.lang.Module module : javaModules) {
                if (module.getName().startsWith("java.") || module.getName().startsWith("jdk.") || module.getClassLoader() == null) {
                    continue;
                }
                for (URL url : moduleURLResolver.resolveURLForJavaModule(module, relativePath)) {
                    //Return any first
                    return url;
                }
            }
        }
        else {
            //We might not know the prefix path in this case 
        }
        throw new IOException("Cannot find resource " + relativePath);
    }

    @Override
    public InputStream resolveStream(String relativePath) throws IOException, ResourceException {
        return resolveKey(relativePath).openStream();
    }

}
