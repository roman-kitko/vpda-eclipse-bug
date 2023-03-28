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
package org.vpda.common.dto.runtime.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import org.vpda.common.dto.runtime.DTORepository;

final class DTORepositoryImpl implements DTORepository {

    private final Collection<Class<?>> classes;

    @Override
    public Collection<Class<?>> getRegisteredClasses() {
        return Collections.unmodifiableCollection(classes);
    }

    public DTORepositoryImpl(Collection<Class<?>> classes) {
        this.classes = new ArrayList<Class<?>>(classes);
    }

    public DTORepositoryImpl(Class<?>... classes) {
        this.classes = Arrays.asList(classes);
    }

}
