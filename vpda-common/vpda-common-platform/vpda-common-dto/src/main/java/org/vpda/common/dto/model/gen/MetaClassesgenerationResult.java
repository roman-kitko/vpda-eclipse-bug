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
package org.vpda.common.dto.model.gen;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

public final class MetaClassesgenerationResult {
    private final MetaClassGeneratorConfiguration cfg;
    private Map<Class, GeneratedClassLocations> generatedFiles;

    /**
     * @param cfg
     * @param generatedFiles
     */
    public MetaClassesgenerationResult(MetaClassGeneratorConfiguration cfg, Map<Class, GeneratedClassLocations> generatedFiles) {
        super();
        this.cfg = cfg;
        this.generatedFiles = generatedFiles;
    }

    public MetaClassGeneratorConfiguration getMetaClassGeneratorConfiguration() {
        return cfg;
    }

    public Set<Class> getClasses() {
        return Collections.unmodifiableSet(generatedFiles.keySet());
    }

    public GeneratedClassLocations getGeneratedFile(Class clazz) {
        return this.generatedFiles.get(clazz);
    }

    public static final class GeneratedClassLocations {
        private final GeneratedJavaFile concreeteClass;
        private final GeneratedJavaFile topLevelClass;

        public GeneratedClassLocations(GeneratedJavaFile concreeteClass, GeneratedJavaFile topLevelClass) {
            super();
            this.concreeteClass = concreeteClass;
            this.topLevelClass = topLevelClass;
        }

        public GeneratedJavaFile getConcreeteClass() {
            return concreeteClass;
        }

        public GeneratedJavaFile getTopLevelClass() {
            return topLevelClass;
        }

    }
}
