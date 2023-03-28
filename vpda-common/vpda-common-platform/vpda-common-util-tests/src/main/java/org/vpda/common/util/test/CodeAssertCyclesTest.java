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
package org.vpda.common.util.test;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import guru.nidi.codeassert.config.AnalyzerConfig;
import guru.nidi.codeassert.config.AnalyzerConfig.Path;
import guru.nidi.codeassert.dependency.Dependencies;
import guru.nidi.codeassert.dependency.DependencyAnalyzer;
import guru.nidi.codeassert.dependency.DependencyResult;

/**
 * Will assert packages cycles
 * 
 * @author kitko
 *
 */
public abstract class CodeAssertCyclesTest {

    private AnalyzerConfig config;

    private String module = null;

    /**
     * Setup of analyze
     * 
     * @throws IOException
     */
    @BeforeEach
    public void setup() throws IOException {
        List<Path> sources = path(new String[0], "src/main/java");
        String moduleClassesDirectory = getModuleClassesDirectory(getSampleModuleClass());
        List<Path> classes = path(new String[0], moduleClassesDirectory);
        config = new VPDAAnalyzerConfig(sources, classes);
    }

    /** Test cycles */
    @Test
    public void noCycles() {
        DependencyResult analyze = new DependencyAnalyzer(config).analyze();
        Dependencies findings = analyze.findings();
        assertTrue(findings.getCycles().isEmpty());

    }

    private String getModuleClassesDirectory(Class clazz) {
        URL url = clazz.getResource(clazz.getSimpleName() + ".class");
        String path = url.getPath();
        path = path.substring(0, path.length() - ".class".length());
        path = path.substring(0, path.length() - clazz.getName().length());
        if (path.startsWith("file:/")) {
            path = path.substring("file:/".length());
        }
        return path;
    }

    /**
     * @return sample class from module
     */
    protected abstract Class getSampleModuleClass();

    private List<Path> path(String[] packs, String... paths) {
        final List<Path> res = new ArrayList<>();
        for (final String path : paths) {
            final String normPath = path(path);
            if (packs.length == 0) {
                res.add(new Path(normPath, ""));
            }
            else {
                for (final String pack : packs) {
                    final String normPack = pack.replace('.', '/');
                    res.add(new Path(normPath, normPack));
                }
            }
        }
        return res;
    }

    private String path(String relative) {
        if (module == null || module.length() == 0 || runningInModuleDir()) {
            return relative;
        }
        return module.endsWith("/") ? module + relative : module + "/" + relative;
    }

    private boolean runningInModuleDir() {
        return new File(".").getAbsolutePath().endsWith("/" + module + "/.");
    }

}
