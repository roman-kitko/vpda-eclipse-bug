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
package org.vpda.common.comps.ui;

/**
 * Text field component
 * 
 * @author kitko
 *
 */
public final class TextFieldAC extends AbstractFieldAC<String> {
    private static final long serialVersionUID = 4712976714962846739L;

    /** */
    public TextFieldAC() {
    }

    /**
     * Creates empty String field
     * 
     * @param id
     */
    public TextFieldAC(String id) {
        super(id);
    }

    /**
     * Creates empty String field
     * 
     * @param id
     * @param value
     */
    public TextFieldAC(String id, String value) {
        super(id);
        setValue(value);
    }

    @Override
    public boolean isEmpty() {
        return super.isEmpty() || (getValue() == null || getValue().isEmpty());
    }

}
