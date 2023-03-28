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
import java.io.IOException;

import org.vpda.common.dto.model.DTOMetaModel;
import org.vpda.common.dto.model.gen.DTOMetaClassesGenerator;
import org.vpda.common.dto.model.gen.MetaClassGeneratorConfiguration;
import org.vpda.common.dto.model.gen.MetaClassesGeneratorLogger;
import org.vpda.common.dto.model.gen.MetaClassesGeneratorLogger.PrintStreamMetaClassesGeneratorLogger;
import org.vpda.common.dto.model.gen.MetaClassesgenerationResult;
import org.vpda.common.dto.runtime.DTORuntime;
import org.vpda.common.dto.runtime.DTORuntimeConfigurationInput;
import org.vpda.common.dto.runtime.DTORuntimeConfigurationInputFactory;
import org.vpda.common.dto.runtime.DTORuntimeFactory;
import org.vpda.common.dto.runtime.spi.DTORuntimeBootstrap;
import org.vpda.internal.common.util.ClassUtil;

import picocli.CommandLine;
import picocli.CommandLine.ITypeConverter;
import picocli.CommandLine.Option;

public final class DTOMetaModelGeneratorMain {

    public static final class Model {

        @Option(names = { "-v", "--verbose" }, description = "Be verbose.")
        private boolean verbose = false;

        @Option(names = { "-o", "--outputdir" }, required = true, description = "Where to put generated java files")
        private File outputDir;

        @Option(names = { "-c", "--clean" }, description = "Clean output dir before classes generation")
        private boolean cleanOutputDir = false;

        @Option(names = { "-f", "--factory" }, required = true, description = "Classname of DTORuntimeConfigurationInputFactory factory", converter = FactoryConverter.class)
        private DTORuntimeConfigurationInputFactory factory;

        @Option(names = { "-h", "--help" }, usageHelp = true, description = "display this help message")
        boolean usageHelpRequested;

        public boolean isVerbose() {
            return verbose;
        }

        public void setVerbose(boolean verbose) {
            this.verbose = verbose;
        }

        public File getOutputDir() {
            return outputDir;
        }

        public void setOutputDir(File outputDir) {
            this.outputDir = outputDir;
        }

        public boolean isCleanOutputDir() {
            return cleanOutputDir;
        }

        public void setCleanOutputDir(boolean cleanOutputDir) {
            this.cleanOutputDir = cleanOutputDir;
        }

        public DTORuntimeConfigurationInputFactory getFactory() {
            return factory;
        }

        public void setFactory(DTORuntimeConfigurationInputFactory factory) {
            this.factory = factory;
        }

        public boolean isUsageHelpRequested() {
            return usageHelpRequested;
        }

        public void setUsageHelpRequested(boolean usageHelpRequested) {
            this.usageHelpRequested = usageHelpRequested;
        }
    }

    public static final class FactoryConverter implements ITypeConverter<DTORuntimeConfigurationInputFactory> {

        @Override
        public DTORuntimeConfigurationInputFactory convert(String value) throws Exception {
            return ClassUtil.createInstance(value, DTORuntimeConfigurationInputFactory.class);
        }

    }

    public static void main(String[] args) {
        Model model = CommandLine.populateCommand(new Model(), args);
        try {
            generateDTOs(model, new PrintStreamMetaClassesGeneratorLogger());
        }
        catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public static Model populateModel(String[] args) {
        return CommandLine.populateCommand(new Model(), args);
    }

    public static void generateDTOs(Model model, MetaClassesGeneratorLogger logger) throws IOException {
        if (model.usageHelpRequested) {
            CommandLine.usage(model, System.out);
            return;
        }
        if (model.cleanOutputDir) {
            org.vpda.internal.common.util.FileUtils.deleteDirectory(model.outputDir.toPath());
        }
        DTORuntimeConfigurationInput input = model.factory.createInput();
        DTORuntimeFactory factory = DTORuntimeBootstrap.createDTORuntimeFactory(input);
        DTOMetaClassesGenerator generator = DTORuntimeBootstrap.createDTOMetaClassesGenerator(logger);
        for (String unitName : input.getUnitNames()) {
            DTORuntime runtime = factory.createDTORuntime(unitName);
            DTOMetaModel metaModel = runtime.getMetaModel();
            MetaClassGeneratorConfiguration genCfg = new MetaClassGeneratorConfigurationImpl(model.outputDir, input.getClassesForUnitName(unitName));
            MetaClassesgenerationResult generationResult = generator.generateMetaClassesForModel(metaModel, genCfg, DTOMetaModelGeneratorMain.class.getClassLoader());
            generator.writeGenerationResult(generationResult, genCfg);
        }
    }

}
