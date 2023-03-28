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

public interface MetaClassGeneratorBaseConfiguration {
    default public String getGeneratedSimpleName(Class clazz) {
        return clazz.getSimpleName() + '_';
    }

    default public String getGeneratedFullJavaFileName(Class clazz) {
        if (!clazz.isMemberClass()) {
            return clazz.getCanonicalName() + '_';
        }
        return getGeneratedFullJavaFileName(clazz.getEnclosingClass()) + '.' + getGeneratedSimpleName(clazz);
    }

    default public String getGeneratedFullName(Class clazz) {
        if (!clazz.isMemberClass()) {
            return clazz.getCanonicalName() + '_';
        }
        return getGeneratedFullName(clazz.getEnclosingClass()) + '$' + getGeneratedSimpleName(clazz);
    }

}
