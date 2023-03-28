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

import java.io.File;
import java.io.PrintStream;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.vpda.common.dto.model.gen.MetaClassesgenerationResult.GeneratedClassLocations;

public interface MetaClassesGeneratorLogger {
    void logClassGenerated(Class dtoClass, GeneratedClassLocations locations);

    void logClassStored(Class dtoClass, String classname, File folder);

    void logClassAlreadyGenerated(Class dtoClass, String generatedFullName);

    void logGeneratedDTOMetalClassNotFound(Class dtoClass, String generatedMetaClassName);

    void logMissiningGeneratedFieldForInitialization(Class dtoClass, String attrName, Class<?> generatedClass);

    void logMetaClassInitialized(Class generatedClass);

    public static final class NoOptLoger implements MetaClassesGeneratorLogger {

        private static final MetaClassesGeneratorLogger INSTANCE = new NoOptLoger();

        public static MetaClassesGeneratorLogger getInstance() {
            return INSTANCE;
        }

        private NoOptLoger() {
        }

        @Override
        public void logClassAlreadyGenerated(Class dtoClass, String generatedFullName) {
        }

        @Override
        public void logClassGenerated(Class dtoClass, GeneratedClassLocations locations) {
        }

        @Override
        public void logClassStored(Class dtoClass, String classname, File folder) {
        }

        @Override
        public void logGeneratedDTOMetalClassNotFound(Class dtoClass, String generatedMetaClassName) {
        }

        @Override
        public void logMissiningGeneratedFieldForInitialization(Class dtoClass, String attrName, Class<?> generatedClass) {
        }

        @Override
        public void logMetaClassInitialized(Class generatedClass) {
        }
    }

    public static final class PrintStreamMetaClassesGeneratorLogger implements MetaClassesGeneratorLogger {

        private final PrintStream stream;

        public PrintStreamMetaClassesGeneratorLogger() {
            this(System.out);
        }

        public PrintStreamMetaClassesGeneratorLogger(PrintStream stream) {
            super();
            this.stream = stream;
        }

        @Override
        public void logClassGenerated(Class dtoClass, GeneratedClassLocations locations) {
            stream.printf("Generating clazz %s into concrete class %s and within top level class %s", dtoClass.getName(), locations.getConcreeteClass().getClassName(),
                    locations.getTopLevelClass().getClassName());
            stream.println();

        }

        @Override
        public void logClassStored(Class dtoClass, String classname, File folder) {
            stream.printf("Storing class %s into top level class %s and into folder %s", dtoClass.getName(), classname, folder.getAbsolutePath());
            stream.println();
        }

        @Override
        public void logClassAlreadyGenerated(Class dtoClass, String generatedFullName) {
            stream.printf("Class %s already generated as %s", dtoClass.getName(), generatedFullName);
            stream.println();

        }

        @Override
        public void logGeneratedDTOMetalClassNotFound(Class dtoClass, String generatedMetaClassName) {
            stream.printf("Generated class for dto %s with generatedName %s not found", dtoClass.getName(), generatedMetaClassName);
            stream.println();
        }

        @Override
        public void logMissiningGeneratedFieldForInitialization(Class dtoClass, String attrName, Class<?> generatedClass) {
            stream.printf("Missing generated attribute for dtoClass %s and generated meta class %s and attributename %s", dtoClass.getName(), generatedClass.getName(), attrName);
            stream.println();
        }

        @Override
        public void logMetaClassInitialized(Class generatedClass) {
            stream.printf("Metaclass %s was initialized", generatedClass.getName());
            stream.println();
        }

    }

    public static final class LoggerBasedMetaClassesGeneratorLogger implements MetaClassesGeneratorLogger {

        private final Logger logger;
        private final Level level;

        public LoggerBasedMetaClassesGeneratorLogger(Logger logger, Level level) {
            super();
            this.logger = logger;
            this.level = level;
        }

        @Override
        public void logClassGenerated(Class dtoClass, GeneratedClassLocations locations) {
            logger.log(level, "Storing class {0} into top level class %s and into folder {1}",
                    new Object[] { dtoClass.getName(), locations.getConcreeteClass().getClassName(), locations.getTopLevelClass().getClassName() });
        }

        @Override
        public void logClassStored(Class dtoClass, String classname, File folder) {
            logger.log(level, "Storing class {0} into top level class {1} and into folder {2}", new Object[] { dtoClass.getName(), classname, folder.getAbsolutePath() });
        }

        @Override
        public void logClassAlreadyGenerated(Class dtoClass, String generatedFullName) {
            logger.log(level, "Class {0} already generated as {1}", new Object[] { dtoClass.getName(), generatedFullName });

        }

        @Override
        public void logGeneratedDTOMetalClassNotFound(Class dtoClass, String generatedMetaClassName) {
            logger.log(level, "Generated class for dto {0} with generatedName {1} not found", new Object[] { dtoClass.getName(), generatedMetaClassName });

        }

        @Override
        public void logMissiningGeneratedFieldForInitialization(Class dtoClass, String attrName, Class<?> generatedClass) {
            logger.log(level, "Missing generated attribute for dtoClass {0} and generated meta class {1} and attributename {2}",
                    new Object[] { dtoClass.getName(), generatedClass.getName(), attrName });

        }

        @Override
        public void logMetaClassInitialized(Class generatedClass) {
            logger.log(level, "Metaclass {0} was initialized", new Object[] { generatedClass.getName() });
        }

    }

}
