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

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import javax.lang.model.element.Modifier;

import org.vpda.common.dto.model.DTOMetaModel;
import org.vpda.common.dto.model.gen.MetaClassGeneratorConfiguration;
import org.vpda.common.dto.model.gen.MetaClassesGeneratorLogger;
import org.vpda.common.dto.model.gen.MetaClassesgenerationResult;

import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeSpec;

final class DTOMetaClassesGeneratorImplMemberClassHelper {

    private final MetaClassesGeneratorLogger logger;

    DTOMetaClassesGeneratorImplMemberClassHelper(MetaClassesGeneratorLogger logger) {
        super();
        this.logger = logger;
    }

    Map<Class, MetaClassesgenerationResult.GeneratedClassLocations> handleMemberClasses(DTOMetaModel model, MetaClassGeneratorConfiguration config, ClassLoader loader,
            Map<Class, JavaFile> collector) {
        Map<Class, JavaFile> concreteMap = new HashMap<>();
        // Map of member to enclosing class
        Map<Class, TypeSpec> typeSpecMap = new HashMap<>();
        for (Map.Entry<Class, JavaFile> entry : collector.entrySet()) {
            Class clazz = entry.getKey();
            typeSpecMap.put(clazz, entry.getValue().typeSpec);
            if (!clazz.isMemberClass()) {
                concreteMap.put(clazz, entry.getValue());
                continue;
            }
        }
        Map<Class, Class> memberToEnclosingMap = new TreeMap<>((Class a, Class b) -> {
            if (a == b) {
                return 0;
            }
            Class current = a;
            int levela = 0;
            while (current.isMemberClass()) {
                current = current.getEnclosingClass();
                levela++;
            }
            current = b;
            int levelb = 0;
            while (current.isMemberClass()) {
                current = current.getEnclosingClass();
                levelb++;
            }
            return levelb == levela ? a.getName().compareTo(b.getName()) : levelb - levela;
        });
        // memberToEnclosingMap = new HashMap<>();
        for (Class clazz : typeSpecMap.keySet()) {
            Class current = clazz;
            while (current.isMemberClass()) {
                Class enclosing = current.getEnclosingClass();
                memberToEnclosingMap.put(current, enclosing);
                current = enclosing;
            }
        }
        // Now we need to create back the TypeSpec for enclosing classes. But we must
        // sort the classes based on the member level, the most memember deep first
        for (Map.Entry<Class, Class> entry : memberToEnclosingMap.entrySet()) {
            TypeSpec typeSpecForKey = typeSpecMap.get(entry.getKey());
            // if that is null, mean we have new enclosing class, that again has some
            // enclosing
            if (typeSpecForKey == null) {
                typeSpecForKey = TypeSpec.classBuilder(config.getGeneratedSimpleName(entry.getKey())).build();
                typeSpecMap.put(entry.getKey(), typeSpecForKey);
            }
            typeSpecForKey = typeSpecForKey.toBuilder().addModifiers(Modifier.PUBLIC, Modifier.STATIC).build();
            TypeSpec enclosingTypeSpec = typeSpecMap.get(entry.getValue());
            if (enclosingTypeSpec == null) {
                enclosingTypeSpec = TypeSpec.classBuilder(config.getGeneratedSimpleName(entry.getValue())).addModifiers(Modifier.PUBLIC).build();
            }
            enclosingTypeSpec = enclosingTypeSpec.toBuilder().addType(typeSpecForKey).build();
            typeSpecMap.put(entry.getValue(), enclosingTypeSpec);
        }

        // And now just go through all mapping and remap to top level typespec
        Map<Class, MetaClassesgenerationResult.GeneratedClassLocations> result = new HashMap<>();
        for (Map.Entry<Class, TypeSpec> entry : typeSpecMap.entrySet()) {
            Class current = entry.getKey();
            while (current.isMemberClass()) {
                current = current.getEnclosingClass();
            }
            TypeSpec topTypeSpec = typeSpecMap.get(current);
            TypeSpec concreteSpec = entry.getValue();
            JavaFile topJavaFile = JavaFile.builder(current.getPackage().getName(), topTypeSpec).skipJavaLangImports(true).build();
            JavaFile concreateJavaFile = JavaFile.builder(current.getPackage().getName(), concreteSpec).skipJavaLangImports(true).build();
            MetaClassesgenerationResult.GeneratedClassLocations locations = new MetaClassesgenerationResult.GeneratedClassLocations(
                    new DTOMetaClassesGeneratorImpl.JavaFileToGeneratedJavaFileAdapter(concreateJavaFile, concreateJavaFile.packageName + '.' + concreateJavaFile.typeSpec.name),
                    new DTOMetaClassesGeneratorImpl.JavaFileToGeneratedJavaFileAdapter(topJavaFile, topJavaFile.packageName + '.' + topJavaFile.typeSpec.name));
            result.put(entry.getKey(), locations);
            logger.logClassGenerated(entry.getKey(), locations);
        }
        return result;
    }

}
