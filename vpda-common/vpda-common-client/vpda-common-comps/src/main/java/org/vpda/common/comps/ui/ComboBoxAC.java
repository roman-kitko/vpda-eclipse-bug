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

import java.util.ArrayList;
import java.util.List;

import org.vpda.common.command.Command;
import org.vpda.common.comps.loc.ComboBoxLocValue;

/**
 * ComboBox component
 * 
 * @author kitko
 * @param <V> type of value in comboBox model
 *
 */
public final class ComboBoxAC<V> extends AbstractComponent<V, ComboBoxLocValue> {
    /**
     * Renderer for comboBox item
     * 
     * @author kitko
     *
     */
    public static interface ComboxBoxRenderer {

        /**
         * Creates item renderer component
         * 
         * @param comboxBox
         * @param value
         * @param index
         * @param selected
         * @return component that will render comboBox value
         */
        public Component getRendererComponent(ComboBoxAC comboxBox, Object value, int index, boolean selected);
    }

    private static final long serialVersionUID = -4644464491223675899L;
    private ComboBoxAC.ComboxBoxRenderer renderer;
    private Command command;
    private List<V> values;
    private int selectedIndex;

    /** */
    public ComboBoxAC() {
        this.values = new ArrayList<>(1);
        this.selectedIndex = -1;
    }

    /**
     * Creates empty comboBox
     * 
     * @param localId
     */
    public ComboBoxAC(String localId) {
        super(localId);
        this.values = new ArrayList<>(1);
        this.selectedIndex = -1;
    }

    /**
     * Creates comboxBox with model created from values
     * 
     * @param localId
     * @param values
     * @param selectedValue
     */
    public ComboBoxAC(String localId, List<? extends V> values, V selectedValue) {
        super(localId);
        this.values = new ArrayList<V>(values);
        this.selectedIndex = -1;
        setValue(selectedValue);
    }

    /**
     * @return model
     */
    public List<V> getValues() {
        return values;
    }

    @Override
    public void adjustFromLocValue(ComboBoxLocValue locValue) {
        setTooltip(locValue.getTooltip());
    }

    @Override
    public ComboBoxLocValue createLocValue() {
        return new ComboBoxLocValue(getTooltip());
    }

    @Override
    public Class<ComboBoxLocValue> getLocValueClass() {
        return ComboBoxLocValue.class;
    }

    /**
     * @return renderer
     */
    public ComboBoxAC.ComboxBoxRenderer getRenderer() {
        return renderer;
    }

    /**
     * Sets renderer
     * 
     * @param renderer
     */
    public void setRenderer(ComboBoxAC.ComboxBoxRenderer renderer) {
        this.renderer = renderer;
    }

    /**
     * @return command
     */
    public Command getCommand() {
        return command;
    }

    /**
     * Sets command
     * 
     * @param command
     */
    public void setCommand(Command command) {
        this.command = command;
    }

    /**
     * @return selected index
     */
    public int getSelectedIndex() {
        return selectedIndex;
    }

    /**
     * Sets selected index
     * 
     * @param selectedIndex
     */
    public void setSelectedIndex(int selectedIndex) {
        this.selectedIndex = selectedIndex;
    }

    @Override
    public V getValue() {
        return selectedIndex == -1 ? null : values.get(selectedIndex);
    }

    @Override
    public void setValue(V value) {
        this.selectedIndex = values.indexOf(value);
    }

    /**
     * Set values creating new model
     * 
     * @param values
     */
    public void setValues(List<? extends V> values) {
        this.values = new ArrayList<>(values);
        selectedIndex = !values.isEmpty() ? 0 : -1;
    }

    @Override
    public void assignValues(org.vpda.common.comps.ui.Component<V, ComboBoxLocValue> c) {
        super.assignValues(c);
        @SuppressWarnings("unchecked")
        ComboBoxAC<V> combo = (ComboBoxAC) c;
        this.renderer = combo.getRenderer();
        this.command = combo.getCommand();
        this.values = combo.getValues() != null ? new ArrayList<V>(combo.getValues()) : null;
        this.selectedIndex = combo.getSelectedIndex();
    }

}
