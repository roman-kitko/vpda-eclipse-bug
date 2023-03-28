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

import java.io.IOException;

import org.vpda.common.dto.model.DTOMetaModel;

public interface DTOMetaClassesGenerator {
    public MetaClassesgenerationResult generateMetaClassesForModel(DTOMetaModel model, MetaClassGeneratorConfiguration config, ClassLoader loader);

    public void writeGenerationResult(MetaClassesgenerationResult result, MetaClassGeneratorConfiguration config) throws IOException;

    public MetaClassesgenerationResult generateAndWriteResult(DTOMetaModel model, MetaClassGeneratorConfiguration config, ClassLoader loader) throws IOException;

    public void initializeGeneratedMetaClasses(DTOMetaModel model, MetaClassGeneratorBaseConfiguration config, ClassLoader loader);
}
