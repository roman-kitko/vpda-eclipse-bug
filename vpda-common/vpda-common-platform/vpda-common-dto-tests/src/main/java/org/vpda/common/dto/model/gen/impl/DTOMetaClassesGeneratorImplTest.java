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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import javax.lang.model.element.Modifier;

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;
import org.vpda.common.dto.annotations.DTOConcrete;
import org.vpda.common.dto.annotations.DTOMappedSuperIdentifiableClass;
import org.vpda.common.dto.model.DTOMetaModel;
import org.vpda.common.dto.model.ListAttribute;
import org.vpda.common.dto.model.ManagedType;
import org.vpda.common.dto.model.SingleAttribute;
import org.vpda.common.dto.model.gen.MetaClassGeneratorConfiguration;
import org.vpda.common.dto.model.gen.MetaClassesGeneratorLogger.NoOptLoger;
import org.vpda.common.dto.model.gen.MetaClassesgenerationResult;
import org.vpda.common.dto.model.gen.MetaClassesgenerationResult.GeneratedClassLocations;
import org.vpda.common.dto.runtime.SingleUnitNameConfigurationInput;
import org.vpda.common.dto.runtime.spi.DTORuntimeBootstrap;
import org.vpda.common.types.Id;

import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeSpec;

public class DTOMetaClassesGeneratorImplTest {

    private final DTOMetaClassesGeneratorImpl generator = new DTOMetaClassesGeneratorImpl(NoOptLoger.getInstance());
    private final ClassLoader loader = Thread.currentThread().getContextClassLoader();

    private final File tmpDir = new File(System.getProperty("java.io.tmpdir"));

    @DTOConcrete
    static class DtoWithTwoBasicFields {
        String a;
        Long b;
    }

    @DTOConcrete
    static class DtoWithList {
        List<String> a;
    }

    @DTOConcrete
    static class DtoWithMoreMembers {
        List<String> a;

        @DTOConcrete
        static class MemberA {
            Long b;

            @DTOConcrete
            static class MemberAA {
                String c;
            }
        }

        @DTOConcrete
        static class MemberB {
            Long d;

            @DTOConcrete
            static class MemberBB {
                String e;
            }
        }
    }

    @DTOMappedSuperIdentifiableClass
    static class MySuperClass {
        @Id
        Long id;
    }

    @DTOConcrete
    static class Concrete extends MySuperClass {
        Long a;
    }

    static enum Color {
        RED, GREEN, BLUE;
    }

    @DTOConcrete
    static class DtoWithEnum {
        Color color;
    }

    @DTOConcrete
    static class DtoForInitTest {
        String name;
        BigDecimal salary;
    }

    @Test
    public void testCreateBasicFieldSpecForAttribute() {
        MetaClassGeneratorConfiguration metaClassGeneratorConfiguration = new MetaClassGeneratorConfigurationImpl(tmpDir, Collections.singleton(DtoWithTwoBasicFields.class));
        DTOMetaModel metaModel = DTORuntimeBootstrap.createDTORuntimeFactory(SingleUnitNameConfigurationInput.createWithDefaultModel("myUnit", DtoWithTwoBasicFields.class)).createDTORuntime("myUnit")
                .getMetaModel();
        ManagedType<DtoWithTwoBasicFields> managedType = metaModel.managedType(DtoWithTwoBasicFields.class);
        FieldSpec createFieldSpecForAttribute = generator.createFieldSpecForAttribute(managedType.getAttribute("a"), managedType, metaModel, metaClassGeneratorConfiguration, loader);
        FieldSpec expectedSpec = FieldSpec
                .builder(ParameterizedTypeName.get(SingleAttribute.class, DtoWithTwoBasicFields.class, String.class), "a", DTOMetaClassesGeneratorImpl.getFieldAttributeModifiers()).build();
        assertEquals(expectedSpec.toString(), createFieldSpecForAttribute.toString());
        createFieldSpecForAttribute = generator.createFieldSpecForAttribute(managedType.getAttribute("b"), managedType, metaModel, metaClassGeneratorConfiguration, loader);
        expectedSpec = FieldSpec.builder(ParameterizedTypeName.get(SingleAttribute.class, DtoWithTwoBasicFields.class, Long.class), "b", DTOMetaClassesGeneratorImpl.getFieldAttributeModifiers())
                .build();
        assertEquals(expectedSpec.toString(), createFieldSpecForAttribute.toString());
    }

    @Test
    public void testCreateBasicFieldSpecForEnumAttribute() {
        MetaClassGeneratorConfiguration metaClassGeneratorConfiguration = new MetaClassGeneratorConfigurationImpl(tmpDir, Collections.singleton(DtoWithEnum.class));
        DTOMetaModel metaModel = DTORuntimeBootstrap.createDTORuntimeFactory(SingleUnitNameConfigurationInput.createWithDefaultModel("myUnit", DtoWithEnum.class)).createDTORuntime("myUnit")
                .getMetaModel();
        ManagedType<DtoWithEnum> managedType = metaModel.managedType(DtoWithEnum.class);
        FieldSpec createFieldSpecForAttribute = generator.createFieldSpecForAttribute(managedType.getAttribute("color"), managedType, metaModel, metaClassGeneratorConfiguration, loader);
        FieldSpec expectedSpec = FieldSpec.builder(ParameterizedTypeName.get(SingleAttribute.class, DtoWithEnum.class, Color.class), "color", DTOMetaClassesGeneratorImpl.getFieldAttributeModifiers())
                .build();
        assertEquals(expectedSpec.toString(), createFieldSpecForAttribute.toString());
    }

    @Test
    public void testCreateListFieldSpecForAttribute() {
        MetaClassGeneratorConfiguration metaClassGeneratorConfiguration = new MetaClassGeneratorConfigurationImpl(tmpDir, Collections.singleton(DtoWithTwoBasicFields.class));
        DTOMetaModel metaModel = DTORuntimeBootstrap.createDTORuntimeFactory(SingleUnitNameConfigurationInput.createWithDefaultModel("myUnit", DtoWithList.class)).createDTORuntime("myUnit")
                .getMetaModel();
        ManagedType<DtoWithList> managedType = metaModel.managedType(DtoWithList.class);
        FieldSpec createFieldSpecForAttribute = generator.createFieldSpecForAttribute(managedType.getAttribute("a"), managedType, metaModel, metaClassGeneratorConfiguration, loader);
        FieldSpec expectedSpec = FieldSpec.builder(ParameterizedTypeName.get(ListAttribute.class, DtoWithList.class, String.class), "a", DTOMetaClassesGeneratorImpl.getFieldAttributeModifiers())
                .build();
        assertEquals(expectedSpec.toString(), createFieldSpecForAttribute.toString());
    }

    @Test
    public void testGenerateSource() throws IOException {
        MetaClassGeneratorConfiguration metaClassGeneratorConfiguration = new MetaClassGeneratorConfigurationImpl(tmpDir, Collections.singleton(DtoWithTwoBasicFields.class));
        DTOMetaModel metaModel = DTORuntimeBootstrap.createDTORuntimeFactory(SingleUnitNameConfigurationInput.createWithDefaultModel("myUnit", DtoWithTwoBasicFields.class)).createDTORuntime("myUnit")
                .getMetaModel();
        ManagedType<DtoWithTwoBasicFields> managedType = metaModel.managedType(DtoWithTwoBasicFields.class);
        JavaFile source = generator.generateSource(managedType, metaModel, metaClassGeneratorConfiguration, new HashMap<>(), loader);
        String expected = IOUtils.toString(getClass().getResource(DtoWithTwoBasicFields.class.getSimpleName() + ".generated"), "UTF-8");
        assertEquals(expected, source.toString());
    }

    @Test
    public void testGenerateMetaClassesForModel() {
        MetaClassGeneratorConfiguration metaClassGeneratorConfiguration = new MetaClassGeneratorConfigurationImpl(tmpDir, Collections.singleton(DtoWithTwoBasicFields.class));
        DTOMetaModel metaModel = DTORuntimeBootstrap.createDTORuntimeFactory(SingleUnitNameConfigurationInput.createWithDefaultModel("myUnit", DtoWithTwoBasicFields.class, DtoWithList.class))
                .createDTORuntime("myUnit").getMetaModel();
        MetaClassesgenerationResult generateMetaClassesForModel = generator.generateMetaClassesForModel(metaModel, metaClassGeneratorConfiguration, loader);
        assertNotNull(generateMetaClassesForModel.getGeneratedFile(DtoWithTwoBasicFields.class));
        assertNotNull(generateMetaClassesForModel.getGeneratedFile(DtoWithTwoBasicFields.class));
        assertNotNull(generateMetaClassesForModel.getGeneratedFile(DTOMetaClassesGeneratorImplTest.class));
        assertNull(generateMetaClassesForModel.getGeneratedFile(String.class));
    }

    @Test
    public void testGenerateMetaClassesForModelForSuperClass() {
        MetaClassGeneratorConfiguration metaClassGeneratorConfiguration = new MetaClassGeneratorConfigurationImpl(tmpDir, new HashSet<>(Arrays.asList(Concrete.class, MySuperClass.class)));
        DTOMetaModel metaModel = DTORuntimeBootstrap.createDTORuntimeFactory(SingleUnitNameConfigurationInput.createWithDefaultModel("myUnit", Concrete.class, MySuperClass.class))
                .createDTORuntime("myUnit").getMetaModel();
        MetaClassesgenerationResult generateMetaClassesForModel = generator.generateMetaClassesForModel(metaModel, metaClassGeneratorConfiguration, loader);
        GeneratedClassLocations concreteClass = generateMetaClassesForModel.getGeneratedFile(Concrete.class);
        assertNotNull(concreteClass);
        assertNotNull(generateMetaClassesForModel.getGeneratedFile(MySuperClass.class));
        TypeSpec typeSpec = concreteClass.getConcreeteClass().unwrap(JavaFile.class).typeSpec;
        assertNotNull(typeSpec.superclass);
        System.out.println(concreteClass.getTopLevelClass());
        assertEquals(metaClassGeneratorConfiguration.getGeneratedSimpleName(MySuperClass.class), typeSpec.superclass.toString());
    }

    @Test
    public void testGenerateMetaClassesForModelWithMoreMemberClasses() {
        MetaClassGeneratorConfiguration metaClassGeneratorConfiguration = new MetaClassGeneratorConfigurationImpl(tmpDir,
                new HashSet<>(Arrays.asList(DtoWithMoreMembers.class, DtoWithMoreMembers.MemberA.MemberAA.class, DtoWithMoreMembers.MemberB.MemberBB.class))); //here we have just inner members
        DTOMetaModel metaModel = DTORuntimeBootstrap.createDTORuntimeFactory(
                SingleUnitNameConfigurationInput.createWithDefaultModel("myUnit", DtoWithMoreMembers.class, DtoWithMoreMembers.MemberA.MemberAA.class, DtoWithMoreMembers.MemberB.MemberBB.class))
                .createDTORuntime("myUnit").getMetaModel();
        MetaClassesgenerationResult generateMetaClassesForModel = generator.generateMetaClassesForModel(metaModel, metaClassGeneratorConfiguration, loader);
        assertNotNull(generateMetaClassesForModel.getGeneratedFile(DtoWithMoreMembers.class));
        assertNotNull(generateMetaClassesForModel.getGeneratedFile(DtoWithMoreMembers.MemberA.MemberAA.class));
        assertNotNull(generateMetaClassesForModel.getGeneratedFile(DtoWithMoreMembers.MemberB.MemberBB.class));
        FieldSpec a = FieldSpec.builder(ParameterizedTypeName.get(ListAttribute.class, DtoWithMoreMembers.class, String.class), "a", DTOMetaClassesGeneratorImpl.getFieldAttributeModifiers()).build();
        FieldSpec c = FieldSpec
                .builder(ParameterizedTypeName.get(SingleAttribute.class, DtoWithMoreMembers.MemberA.MemberAA.class, String.class), "c", DTOMetaClassesGeneratorImpl.getFieldAttributeModifiers())
                .build();
        FieldSpec e = FieldSpec
                .builder(ParameterizedTypeName.get(SingleAttribute.class, DtoWithMoreMembers.MemberB.MemberBB.class, String.class), "e", DTOMetaClassesGeneratorImpl.getFieldAttributeModifiers())
                .build();

        TypeSpec aaType = TypeSpec.classBuilder(metaClassGeneratorConfiguration.getGeneratedSimpleName(DtoWithMoreMembers.MemberA.MemberAA.class)).addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .addAnnotation(AnnotationSpec.get(StaticModelClassI.create(DtoWithMoreMembers.MemberA.MemberAA.class)))
                .addField(c).build();
        TypeSpec bbType = TypeSpec.classBuilder(metaClassGeneratorConfiguration.getGeneratedSimpleName(DtoWithMoreMembers.MemberB.MemberBB.class)).addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .addAnnotation(AnnotationSpec.get(StaticModelClassI.create(DtoWithMoreMembers.MemberB.MemberBB.class)))
                .addField(e).build();
        TypeSpec aType = TypeSpec.classBuilder(metaClassGeneratorConfiguration.getGeneratedSimpleName(DtoWithMoreMembers.MemberA.class)).addModifiers(Modifier.PUBLIC, Modifier.STATIC).addType(aaType)
                .build();
        TypeSpec bType = TypeSpec.classBuilder(metaClassGeneratorConfiguration.getGeneratedSimpleName(DtoWithMoreMembers.MemberB.class)).addModifiers(Modifier.PUBLIC, Modifier.STATIC).addType(bbType)
                .build();
        TypeSpec dtoWithMoreMembersType = TypeSpec.classBuilder(metaClassGeneratorConfiguration.getGeneratedSimpleName(DtoWithMoreMembers.class)).addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .addField(a)
                .addAnnotation(AnnotationSpec.get(StaticModelClassI.create(DtoWithMoreMembers.class)))
                .addType(aType).addType(bType).build();
        TypeSpec dTOMetaClassesGeneratorImplTestType = TypeSpec.classBuilder(metaClassGeneratorConfiguration.getGeneratedSimpleName(DTOMetaClassesGeneratorImplTest.class))
                .addModifiers(Modifier.PUBLIC).addType(dtoWithMoreMembersType).build();
        JavaFile source = JavaFile.builder(DTOMetaClassesGeneratorImplTest.class.getPackage().getName(), dTOMetaClassesGeneratorImplTestType).skipJavaLangImports(true).build();
        assertEquals(source.toString(), generateMetaClassesForModel.getGeneratedFile(DtoWithMoreMembers.MemberA.MemberAA.class).getTopLevelClass().toString());
    }

    @Test
    public void testWriteGenerationResult() throws IOException {
        Path createTempDirectory = java.nio.file.Files.createTempDirectory(tmpDir.toPath(), "testWriteGenerationResult");
        MetaClassGeneratorConfiguration metaClassGeneratorConfiguration = new MetaClassGeneratorConfigurationImpl(createTempDirectory.toFile(), Collections.singleton(DtoWithTwoBasicFields.class));
        DTOMetaModel metaModel = DTORuntimeBootstrap.createDTORuntimeFactory(SingleUnitNameConfigurationInput.createWithDefaultModel("myUnit", DtoWithTwoBasicFields.class, DtoWithList.class))
                .createDTORuntime("myUnit").getMetaModel();
        MetaClassesgenerationResult result = generator.generateMetaClassesForModel(metaModel, metaClassGeneratorConfiguration, loader);
        generator.writeGenerationResult(result, metaClassGeneratorConfiguration);

        for (Class<?> expected : metaClassGeneratorConfiguration.generateSourcesForClasses()) {
            GeneratedClassLocations generatedFile = result.getGeneratedFile(expected);
            assertNotNull(generatedFile, "Must have generated file for class + " + expected);
            Path theJavaFile = createTempDirectory.resolve(generatedFile.getTopLevelClass().getClassName().replace('.', '/') + ".java");
            assertTrue(theJavaFile.toFile().exists(), "Generateded file must exist : " + theJavaFile);
        }

    }

    @Test
    public void testGenerateAndWriteResult() throws IOException {
        Path createTempDirectory = java.nio.file.Files.createTempDirectory(tmpDir.toPath(), "testGenerateAndWriteResult");
        MetaClassGeneratorConfiguration metaClassGeneratorConfiguration = new MetaClassGeneratorConfigurationImpl(createTempDirectory.toFile(), Collections.singleton(DtoWithTwoBasicFields.class));
        DTOMetaModel metaModel = DTORuntimeBootstrap.createDTORuntimeFactory(SingleUnitNameConfigurationInput.createWithDefaultModel("myUnit", DtoWithTwoBasicFields.class, DtoWithList.class))
                .createDTORuntime("myUnit").getMetaModel();
        MetaClassesgenerationResult result = generator.generateAndWriteResult(metaModel, metaClassGeneratorConfiguration, loader);
        for (Class<?> expected : metaClassGeneratorConfiguration.generateSourcesForClasses()) {
            GeneratedClassLocations generatedFile = result.getGeneratedFile(expected);
            assertNotNull(generatedFile, "Must have generated file for class + " + expected);
            Path theJavaFile = createTempDirectory.resolve(generatedFile.getTopLevelClass().getClassName().replace('.', '/') + ".java");
            assertTrue(theJavaFile.toFile().exists(), "Generateded file must exist : " + theJavaFile);
        }
    }

    @Test
    public void testInitMetaClass() throws IOException {
        Path createTempDirectory = java.nio.file.Files.createTempDirectory(tmpDir.toPath(), "testGenerateAndWriteResult");
        MetaClassGeneratorConfiguration metaClassGeneratorConfiguration = new MetaClassGeneratorConfigurationImpl(createTempDirectory.toFile(), Collections.singleton(DtoForInitTest.class));
        DTOMetaModel metaModel = DTORuntimeBootstrap.createDTORuntimeFactory(SingleUnitNameConfigurationInput.createWithDefaultModel("myUnit", DtoForInitTest.class)).createDTORuntime("myUnit")
                .getMetaModel();
        generator.initializeGeneratedMetaClasses(metaModel, metaClassGeneratorConfiguration, loader);
        assertNotNull(DTOMetaClassesGeneratorImplTest_.DtoForInitTest_.name);
        assertNotNull(DTOMetaClassesGeneratorImplTest_.DtoForInitTest_.salary);
        ManagedType<DtoForInitTest> managedType =  metaModel.managedType(DtoForInitTest.class);
        assertSame(managedType.getAttribute(DTOMetaClassesGeneratorImplTest_.DtoForInitTest_.name.getName()), DTOMetaClassesGeneratorImplTest_.DtoForInitTest_.name);
        assertSame(managedType.getAttribute(DTOMetaClassesGeneratorImplTest_.DtoForInitTest_.salary.getName()),
                DTOMetaClassesGeneratorImplTest_.DtoForInitTest_.salary);
        
        
        assertEquals(DTOMetaClassesGeneratorImplTest_.DtoForInitTest_.class, metaModel.getModelClass(DTOMetaClassesGeneratorImplTest.DtoForInitTest.class));
        assertEquals(DTOMetaClassesGeneratorImplTest_.DtoForInitTest_.class, metaModel.getModelClassForManagedType(managedType));
        
    }

}
