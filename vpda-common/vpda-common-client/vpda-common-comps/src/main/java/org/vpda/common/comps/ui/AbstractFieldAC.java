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
/**
 * 
 */
package org.vpda.common.comps.ui;

import org.vpda.common.comps.autocomplete.AutoCompleteDef;
import org.vpda.common.comps.loc.FieldLocValue;

/**
 * Abstract input field
 * 
 * @author kitko
 * @param <V>
 *
 */
public abstract class AbstractFieldAC<V> extends AbstractComponent<V, FieldLocValue> {
    private static final long serialVersionUID = 264102062302354708L;
    /** Flag whether field is editable */
    protected boolean editable;
    /** Id of fetch */
    protected String fetchId;
    /** Count of columns */
    protected int columns;
    /** AutoComplete def */
    protected AutoCompleteDef autoCompleteFieldDef;

    /** */
    protected AbstractFieldAC() {
    }

    /**
     * @param localId
     */
    protected AbstractFieldAC(String localId) {
        super(localId);
        editable = true;
    }

    /**
     * @return true if editable
     */
    public boolean isEditable() {
        return editable;
    }

    /**
     * Sets editable
     * 
     * @param editable
     */
    public void setEditable(boolean editable) {
        this.editable = editable;

    }

    @Override
    public void adjustFromLocValue(FieldLocValue locValue) {
        if (locValue == null) {
            return;
        }
        setTooltip(locValue.getTooltip());
    }

    @Override
    public FieldLocValue createLocValue() {
        return new FieldLocValue.Builder().setTooltip(getTooltip()).build();
    }

    @Override
    public Class<FieldLocValue> getLocValueClass() {
        return FieldLocValue.class;
    }

    /**
     * @return fetch id
     */
    public String getFetchId() {
        return fetchId;
    }

    /**
     * Sets fetch id
     * 
     * @param fetchId
     */
    public void setFetchId(String fetchId) {
        this.fetchId = fetchId;
    }

    /**
     * @return the columns
     */
    public int getColumns() {
        return columns;
    }

    /**
     * @param columns the columns to set
     */
    public void setColumns(int columns) {
        this.columns = columns;
    }

    /**
     * @return the autoCompleteFieldDef
     */
    public AutoCompleteDef getAutoCompleteFieldDef() {
        return autoCompleteFieldDef;
    }

    /**
     * @param autoCompleteFieldDef the autoCompleteFieldDef to set
     */
    public void setAutoCompleteFieldDef(AutoCompleteDef autoCompleteFieldDef) {
        this.autoCompleteFieldDef = autoCompleteFieldDef;
    }

    @Override
    public void assignValues(org.vpda.common.comps.ui.Component<V, FieldLocValue> c) {
        super.assignValues(c);
        AbstractFieldAC f = (AbstractFieldAC) c;
        this.setColumns(f.getColumns());
        this.setEditable(f.isEditable());
        this.setFetchId(f.getFetchId());
        this.setAutoCompleteFieldDef(f.getAutoCompleteFieldDef());
    }

    @Override
    public void setValue(V value) {
        super.setValue(value);
        if (value instanceof String) {
            setColumns(((String) value).length());
        }

    }

}
