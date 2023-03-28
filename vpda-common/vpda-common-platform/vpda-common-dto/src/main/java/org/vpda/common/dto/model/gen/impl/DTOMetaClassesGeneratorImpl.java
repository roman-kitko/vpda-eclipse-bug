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
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.lang.model.element.Modifier;

import org.vpda.common.dto.model.Attribute;
import org.vpda.common.dto.model.CollectionAttribute;
import org.vpda.common.dto.model.DTOMetaModel;
import org.vpda.common.dto.model.DTOMetaModelRegistry;
import org.vpda.common.dto.model.ListAttribute;
import org.vpda.common.dto.model.ManagedType;
import org.vpda.common.dto.model.MapAttribute;
import org.vpda.common.dto.model.PluralAttribute;
import org.vpda.common.dto.model.PluralAttribute.CollectionType;
import org.vpda.common.dto.model.SetAttribute;
import org.vpda.common.dto.model.SingleAttribute;
import org.vpda.common.dto.model.gen.DTOMetaClassesGenerator;
import org.vpda.common.dto.model.gen.GeneratedJavaFile;
import org.vpda.common.dto.model.gen.MetaClassGeneratorBaseConfiguration;
import org.vpda.common.dto.model.gen.MetaClassGeneratorConfiguration;
import org.vpda.common.dto.model.gen.MetaClassesGeneratorLogger;
import org.vpda.common.dto.model.gen.MetaClassesgenerationResult;
import org.vpda.common.util.exceptions.VPDARuntimeException;
import org.vpda.internal.common.util.Assert;

import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

public final class DTOMetaClassesGeneratorImpl implements DTOMetaClassesGenerator {

    public static Modifier[] getFieldAttributeModifiers() {
        return new Modifier[] { Modifier.PUBLIC, Modifier.STATIC, Modifier.VOLATILE };
    }

    private final DTOMetaClassesGeneratorImplMemberClassHelper metaClassesGeneratorImplMemberClassHelper;
    private final MetaClassesGeneratorLogger logger;

    public DTOMetaClassesGeneratorImpl(MetaClassesGeneratorLogger logger) {
        this.logger = Assert.isNotNullArgument(logger, "logger");
        this.metaClassesGeneratorImplMemberClassHelper = new DTOMetaClassesGeneratorImplMemberClassHelper(logger);
    }

    @Override
    public MetaClassesgenerationResult generateMetaClassesForModel(DTOMetaModel model, MetaClassGeneratorConfiguration config, ClassLoader loader) {
        Map<Class, JavaFile> collector = new HashMap<>();
        for (Class clazz : config.generateSourcesForClasses()) {
            @SuppressWarnings("unchecked")
            ManagedType managedType = model.managedType(clazz);
            generateSource(managedType, model, config, collector, loader);
        }
        Map<Class, MetaClassesgenerationResult.GeneratedClassLocations> allGeneratedClassesMapping = metaClassesGeneratorImplMemberClassHelper.handleMemberClasses(model, config, loader, collector);

        return new MetaClassesgenerationResult(config, allGeneratedClassesMapping);
    }

    JavaFile generateSource(ManagedType<?> managedType, DTOMetaModel model, MetaClassGeneratorConfiguration config, Map<Class, JavaFile> collector, ClassLoader loader) {
        if (collector.containsKey(managedType.getJavaType())) {
            return collector.get(managedType.getJavaType());
        }
        String generatedFullName = config.getGeneratedFullName(managedType.getJavaType());
        try {
            loader.loadClass(generatedFullName);
            logger.logClassAlreadyGenerated(managedType.getJavaType(), generatedFullName);
            return null;
        }
        catch (ClassNotFoundException e) {
        }

        TypeSpec.Builder classBuilder = TypeSpec.classBuilder(getClassNameForManagedType(managedType, config));
        if (managedType.getSuperType() != null) {
            if (config.generateSourcesForClasses().contains(managedType.getSuperType().getJavaType())) {
                // ok, we need to generate first for supertype
                generateSource(managedType.getSuperType(), model, config, collector, loader);
            }
            else {
                String generatedFullNameForSuperType = config.getGeneratedFullName(managedType.getSuperType().getJavaType());
                try {
                    loader.loadClass(generatedFullNameForSuperType);
                }
                catch (ClassNotFoundException e) {
                    throw new VPDARuntimeException("Cannot find managed model super type class " + managedType.getSuperType().getJavaType() + " for managedtype " + managedType.getJavaType(), e);
                }
            }
            String superClassPackageName = managedType.getSuperType().getJavaType().getPackage().getName();
            if (superClassPackageName.equals(managedType.getJavaType().getPackage().getName())) {
                superClassPackageName = "";
            }
            ClassName superclassname = ClassName.get(superClassPackageName, config.getGeneratedSimpleName(managedType.getSuperType().getJavaType()));
            classBuilder.superclass(superclassname);
        }
        for (Attribute attribute : managedType.getDeclaredAttributes()) {
            FieldSpec fieldSpec = createFieldSpecForAttribute(attribute, managedType, model, config, loader);
            classBuilder.addField(fieldSpec);
        }
        AnnotationSpec staticModelAnn = AnnotationSpec.get(StaticModelClassI.create(managedType.getJavaType()));
        TypeSpec typeSpec = classBuilder.addModifiers(Modifier.PUBLIC).addAnnotation(staticModelAnn).build();
        JavaFile javaFile = JavaFile.builder(managedType.getJavaType().getPackage().getName(), typeSpec).skipJavaLangImports(true).build();
        collector.put(managedType.getJavaType(), javaFile);
        return javaFile;
    }

    private ClassName getClassNameForManagedType(ManagedType managedType, MetaClassGeneratorConfiguration config) {
        Class javaType = managedType.getJavaType();
        return ClassName.get(javaType.getPackage().getName(), config.getGeneratedSimpleName(javaType));
    }

    FieldSpec createFieldSpecForAttribute(Attribute attribute, ManagedType<?> managedType, DTOMetaModel model, MetaClassGeneratorConfiguration config, ClassLoader loader) {
        TypeName type = null; // ParameterizedTypeName
        switch (attribute.getAttributeType()) {
        case SINGLE_BASIC: {
            type = createSingleBasicAttributeTypeName((SingleAttribute) attribute, managedType, model, config, loader);
            break;
        }
        case SINGLE_VALUE: {
            type = createSingleBasicAttributeTypeName((SingleAttribute) attribute, managedType, model, config, loader);
            break;
        }
        case SINGLE_REFERENCE: {
            type = createSingleReferenceAttributeTypeName((SingleAttribute) attribute, managedType, model, config, loader);
            break;
        }
        case SINGLE_ASSOCIATION: {
            type = createSingleAssocationAttributeTypeName((SingleAttribute) attribute, managedType, model, config, loader);
            break;
        }
        case SINGLE_EMBEDDED: {
            type = createSingleEmbeddedAttributeTypeName((SingleAttribute) attribute, managedType, model, config, loader);
            break;
        }
        case COLLECTION_BASIC: {
            type = createCollectionBasicAttributeTypeName((PluralAttribute) attribute, managedType, model, config, loader);
            break;
        }
        case COLLECTION_ASSOCIATION: {
            type = createCollectionAssocationAttributeTypeName((PluralAttribute) attribute, managedType, model, config, loader);
            break;
        }
        case COLLECTION_REFERENCE: {
            type = createCollectionReferenceAttributeTypeName((PluralAttribute) attribute, managedType, model, config, loader);
            break;
        }
        default:
            throw new VPDARuntimeException("Invalid attribute type : " + attribute.getAttributeType());
        }
        FieldSpec.Builder builder = FieldSpec.builder(type, attribute.getName(), getFieldAttributeModifiers());
        return builder.build();
    }

    private TypeName createSingleReferenceAttributeTypeName(SingleAttribute attribute, ManagedType<?> managedType, DTOMetaModel model, MetaClassGeneratorConfiguration config, ClassLoader loader) {
        Type valueType = null;
        if (((Field) attribute.getJavaMember()).getGenericType() instanceof ParameterizedType) {
            valueType = (((Field) attribute.getJavaMember()).getGenericType());
        }
        else {
            valueType = ((Field) attribute.getJavaMember()).getType();
        }
        return ParameterizedTypeName.get(SingleAttribute.class, managedType.getJavaType(), getPrimitiveWrappedClass(valueType));
    }

    private TypeName createSingleBasicAttributeTypeName(SingleAttribute attribute, ManagedType<?> managedType, DTOMetaModel model, MetaClassGeneratorConfiguration config, ClassLoader loader) {
        return ParameterizedTypeName.get(SingleAttribute.class, managedType.getJavaType(), getPrimitiveWrappedClass(attribute.getJavaType()));
    }

    private TypeName createSingleAssocationAttributeTypeName(SingleAttribute attribute, ManagedType<?> managedType, DTOMetaModel model, MetaClassGeneratorConfiguration config, ClassLoader loader) {
        return createSingleBasicAttributeTypeName(attribute, managedType, model, config, loader);
    }

    private TypeName createSingleEmbeddedAttributeTypeName(SingleAttribute attribute, ManagedType<?> managedType, DTOMetaModel model, MetaClassGeneratorConfiguration config, ClassLoader loader) {
        return createSingleBasicAttributeTypeName(attribute, managedType, model, config, loader);
    }

    private TypeName createCollectionReferenceAttributeTypeName(PluralAttribute attribute, ManagedType<?> managedType, DTOMetaModel model, MetaClassGeneratorConfiguration config, ClassLoader loader) {
        Type valueType = null;
        if (((Field) attribute.getJavaMember()).getGenericType() instanceof ParameterizedType) {
            valueType = (((Field) attribute.getJavaMember()).getGenericType());
        }
        else {
            valueType = ((Field) attribute.getJavaMember()).getType();
        }
        if (CollectionType.COLLECTION.equals(attribute.getCollectionType())) {
            return ParameterizedTypeName.get(CollectionAttribute.class, managedType.getJavaType(), getPrimitiveWrappedClass(valueType));
        }
        else if (CollectionType.LIST.equals(attribute.getCollectionType())) {
            return ParameterizedTypeName.get(ListAttribute.class, managedType.getJavaType(), getPrimitiveWrappedClass(valueType));
        }
        else if (CollectionType.SET.equals(attribute.getCollectionType())) {
            return ParameterizedTypeName.get(SetAttribute.class, managedType.getJavaType(), getPrimitiveWrappedClass(valueType));
        }
        else if (CollectionType.MAP.equals(attribute.getCollectionType())) {
            MapAttribute mapAttribute = (MapAttribute) attribute;
            return ParameterizedTypeName.get(MapAttribute.class, managedType.getJavaType(), getPrimitiveWrappedClass(mapAttribute.getKeyJavaType()), getPrimitiveWrappedClass(valueType));
        }
        throw new VPDARuntimeException("Invalid collection type : " + attribute.getCollectionType());
    }

    private Type getPrimitiveWrappedClass(Type type) {
        if (!(type instanceof Class)) {
            return type;
        }
        Class<?> clazz = (Class<?>) type;
        if (!clazz.isPrimitive()) {
            return clazz;
        }
        if (clazz == Integer.TYPE)
            return Integer.class;
        if (clazz == Long.TYPE)
            return Long.class;
        if (clazz == Boolean.TYPE)
            return Boolean.class;
        if (clazz == Byte.TYPE)
            return Byte.class;
        if (clazz == Character.TYPE)
            return Character.class;
        if (clazz == Float.TYPE)
            return Float.class;
        if (clazz == Double.TYPE)
            return Double.class;
        if (clazz == Short.TYPE)
            return Short.class;
        if (clazz == Void.TYPE)
            return Void.class;
        throw new IllegalArgumentException("Unknown primitive type : " + clazz);
    }

    private TypeName createCollectionAssocationAttributeTypeName(PluralAttribute attribute, ManagedType<?> managedType, DTOMetaModel model, MetaClassGeneratorConfiguration config,
            ClassLoader loader) {
        return createCollectionBasicAttributeTypeName(attribute, managedType, model, config, loader);
    }

    private TypeName createCollectionBasicAttributeTypeName(PluralAttribute attribute, ManagedType<?> managedType, DTOMetaModel model, MetaClassGeneratorConfiguration config, ClassLoader loader) {
        if (CollectionType.COLLECTION.equals(attribute.getCollectionType())) {
            return ParameterizedTypeName.get(CollectionAttribute.class, managedType.getJavaType(), getPrimitiveWrappedClass(attribute.getJavaElementType()));
        }
        else if (CollectionType.LIST.equals(attribute.getCollectionType())) {
            return ParameterizedTypeName.get(ListAttribute.class, managedType.getJavaType(), getPrimitiveWrappedClass(attribute.getJavaElementType()));
        }
        else if (CollectionType.SET.equals(attribute.getCollectionType())) {
            return ParameterizedTypeName.get(SetAttribute.class, managedType.getJavaType(), getPrimitiveWrappedClass(attribute.getJavaElementType()));
        }
        else if (CollectionType.MAP.equals(attribute.getCollectionType())) {
            MapAttribute mapAttribute = (MapAttribute) attribute;
            return ParameterizedTypeName.get(MapAttribute.class, managedType.getJavaType(), getPrimitiveWrappedClass(mapAttribute.getKeyJavaType()),
                    getPrimitiveWrappedClass(mapAttribute.getValueJavaType()));
        }
        throw new VPDARuntimeException("Invalid collection type : " + attribute.getCollectionType());
    }

    @Override
    public void writeGenerationResult(MetaClassesgenerationResult result, MetaClassGeneratorConfiguration config) throws IOException {
        File rootFolder = config.generateSourcesInFolder();
        rootFolder.mkdirs();
        Set<String> generatedClassNames = new HashSet<>();
        for (Class clazz : result.getClasses()) {
            MetaClassesgenerationResult.GeneratedClassLocations generatedFileLocaltions = result.getGeneratedFile(clazz);
            String topClassName = generatedFileLocaltions.getTopLevelClass().getClassName();
            if (generatedClassNames.contains(topClassName)) {
                continue;
            }
            generatedFileLocaltions.getTopLevelClass().writeTo(rootFolder);
            logger.logClassStored(clazz, generatedFileLocaltions.getTopLevelClass().getClassName(), rootFolder);
            generatedClassNames.add(topClassName);
        }

    }

    @Override
    public MetaClassesgenerationResult generateAndWriteResult(DTOMetaModel model, MetaClassGeneratorConfiguration config, ClassLoader loader) throws IOException {
        MetaClassesgenerationResult result = generateMetaClassesForModel(model, config, loader);
        writeGenerationResult(result, config);
        return result;
    }

    static final class JavaFileToGeneratedJavaFileAdapter implements GeneratedJavaFile {
        private final JavaFile source;
        private final String className;

        JavaFileToGeneratedJavaFileAdapter(JavaFile source, String className) {
            super();
            this.source = source;
            this.className = className;
        }

        @Override
        public void writeTo(Appendable out) throws IOException {
            source.writeTo(out);
        }

        @Override
        public void writeTo(Path directory) throws IOException {
            source.writeTo(directory);

        }

        @Override
        public void writeTo(File directory) throws IOException {
            source.writeTo(directory);

        }

        @Override
        public String getContent() throws IOException {
            StringBuilder builder = new StringBuilder();
            writeTo(builder);
            return builder.toString();
        }

        @Override
        public String getClassName() {
            return className;
        }

        @Override
        public String toString() {
            return source.toString();
        }

        @Override
        public <T> T unwrap(Class<T> type) {
            if (type == JavaFile.class) {
                return type.cast(source);
            }
            throw new IllegalArgumentException("Can unwrap only JavaFile type");
        }

    }

    @Override
    public void initializeGeneratedMetaClasses(DTOMetaModel model, MetaClassGeneratorBaseConfiguration config, ClassLoader loader) {
        for (ManagedType<?> managedType : model.getManagedTypes()) {
            String generatedName = config.getGeneratedFullName(managedType.getJavaType());
            Class<?> generatedClass = null;
            try {
                generatedClass = loader.loadClass(generatedName);
            }
            catch (ClassNotFoundException e) {
                logger.logGeneratedDTOMetalClassNotFound(managedType.getJavaType(), generatedName);
                continue;
            }
            initializedGeneratedClass(model, managedType, generatedClass);
            if(model instanceof DTOMetaModelRegistry registry) {
                registry.registerMetaModelClassForManagedType(managedType, generatedClass);
            }
        }
    }

    private void initializedGeneratedClass(DTOMetaModel model, ManagedType<?> managedType, Class<?> generatedClass) {
        for (Attribute<?, ?> attr : managedType.getAttributes()) {
            String attrName = attr.getName();
            Field metaField = null;
            try {
                metaField = generatedClass.getField(attrName);
            }
            catch (NoSuchFieldException | SecurityException e) {
                logger.logMissiningGeneratedFieldForInitialization(managedType.getClass(), attrName, generatedClass);
                continue;
            }
            try {
                // we are directly setting the attribute, but that is immutable, so we should be
                // ok with that
                metaField.set(generatedClass, attr);
            }
            catch (IllegalArgumentException | IllegalAccessException e) {
                throw new VPDARuntimeException("Cannot set metafield value", e);
            }
        }
        logger.logMetaClassInitialized(generatedClass);
    }

}
