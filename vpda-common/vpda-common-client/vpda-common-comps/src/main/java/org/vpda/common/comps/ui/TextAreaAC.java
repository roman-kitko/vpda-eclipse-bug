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
 * Text area field component
 * 
 * @author kitko
 *
 */
public final class TextAreaAC extends AbstractFieldAC<String> {
    private static final long serialVersionUID = 4712976714962846739L;
    /** Number of rows in area */
    protected int rows;

    /** */
    public TextAreaAC() {
    }

    /**
     * Creates empty String field
     * 
     * @param id
     */
    public TextAreaAC(String id) {
        super(id);
    }

    /**
     * Creates empty String field
     * 
     * @param id
     * @param value
     */
    public TextAreaAC(String id, String value) {
        super(id);
        setValue(value);
    }

    /**
     * @return the rows
     */
    public int getRows() {
        return rows;
    }

    /**
     * @param rows the rows to set
     */
    public void setRows(int rows) {
        this.rows = rows;
    }

    @Override
    public boolean isEmpty() {
        return super.isEmpty() || (getValue() == null || getValue().isEmpty());
    }

}
