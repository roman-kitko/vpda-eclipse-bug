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
package org.vpda.common.dto.model.gen.impl;

import java.io.File;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.vpda.common.dto.model.gen.MetaClassGeneratorConfiguration;

public final class MetaClassGeneratorConfigurationImpl implements MetaClassGeneratorConfiguration {

    private final File generateSourcesInFolder;
    private final Set<Class> generateSourcesForClasses;

    /**
     * @param generateSourcesInFolder
     * @param generateSourcesForClasses
     */
    public MetaClassGeneratorConfigurationImpl(File generateSourcesInFolder, Set<Class<?>> generateSourcesForClasses) {
        super();
        this.generateSourcesInFolder = generateSourcesInFolder;
        this.generateSourcesForClasses = new HashSet<>(generateSourcesForClasses);
    }

    @Override
    public Set<Class> generateSourcesForClasses() {
        return Collections.unmodifiableSet(generateSourcesForClasses);
    }

    @Override
    public File generateSourcesInFolder() {
        return generateSourcesInFolder;
    }

}
