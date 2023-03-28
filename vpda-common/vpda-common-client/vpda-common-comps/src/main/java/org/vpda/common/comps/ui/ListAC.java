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
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.vpda.common.command.Command;
import org.vpda.common.comps.loc.ListLocValue;

/**
 * ListBox component
 * 
 * @author kitko
 * @param <V> type of value in comboBox model
 *
 */
public final class ListAC<V> extends AbstractComponent<List<V>, ListLocValue> {
    /**
     * Renderer for list item
     * 
     * @author kitko
     *
     */
    public static interface ListRenderer {

        /**
         * Creates item renderer component
         * 
         * @param list
         * @param value
         * @param index
         * @param selected
         * @return component that will render list item value
         */
        public Component getRendererComponent(ListAC list, Object value, int index, boolean selected);
    }

    private static final long serialVersionUID = -4644464491223675899L;
    private static int[] EMPTY_INT_ARRAY = new int[0];
    private Command command;
    private SelectionMode selectionMode = SelectionMode.SINGLE_SELECTION;
    private int[] selectedIndices = EMPTY_INT_ARRAY;
    private List<V> values;
    private ListAC.ListRenderer renderer;

    /** */
    public ListAC() {
    }

    /**
     * Creates empty list box
     * 
     * @param localId
     */
    public ListAC(String localId) {
        super(localId);
        values = new ArrayList<>();
    }

    /**
     * Creates comboxBox with model created from values
     * 
     * @param localId
     * @param values
     * @param selectedValues
     */
    public ListAC(String localId, final List<V> values, List<V> selectedValues) {
        super(localId);
        selectionMode = SelectionMode.MULTIPLE_INTERVAL_SELECTION;
        this.values = new ArrayList<>(values);
        setValue(selectedValues);
    }

    /**
     * @return all values
     */
    public List<V> getValues() {
        return Collections.unmodifiableList(values);
    }

    @Override
    public List<V> getValue() {
        if (selectedIndices == null) {
            return Collections.emptyList();
        }
        List<V> result = new ArrayList<V>(selectedIndices.length);
        for (int index : selectedIndices) {
            result.add(values.get(index));
        }
        return result;
    }

    @Override
    public void setValue(List<V> selectedValues) {
        clearSelection();
        if (selectedValues == null) {
            return;
        }
        int size = this.values.size();
        List<Integer> indices = new ArrayList<Integer>(selectedValues.size());
        for (Object value : selectedValues) {
            for (int j = 0; j < size; j++) {
                if (value == null) {
                    if (this.values.get(j) == null) {
                        indices.add(j);
                        break;
                    }
                }
                else if (value.equals(this.values.get(j))) {
                    indices.add(j);
                    break;
                }
            }
        }
        if (!indices.isEmpty()) {
            selectedIndices = new int[indices.size()];
            for (int i = 0; i < indices.size(); i++) {
                selectedIndices[i] = indices.get(i);
            }
        }
    }

    @Override
    public void adjustFromLocValue(ListLocValue locValue) {
        setTooltip(locValue.getTooltip());
    }

    @Override
    public ListLocValue createLocValue() {
        return new ListLocValue(getTooltip());
    }

    @Override
    public Class<ListLocValue> getLocValueClass() {
        return ListLocValue.class;
    }

    /**
     * @return selection command
     */
    public Command getSelectionCommand() {
        return command;
    }

    /**
     * Sets selection command
     * 
     * @param command
     */
    public void setSelectionCommand(Command command) {
        this.command = command;
    }

    /**
     * @return seected indices
     */
    public int[] getSelectedIndices() {
        return selectedIndices;
    }

    /**
     * Sest selected indices
     * 
     * @param indices
     */
    public void setSelectedIndices(int[] indices) {
        this.selectedIndices = Arrays.copyOf(indices, indices.length);
    }

    /**
     * @return selection mode
     */
    public SelectionMode getSelectionMode() {
        return selectionMode;
    }

    /**
     * Sets selection mode
     * 
     * @param selectionMode
     */
    public void setSelectionMode(SelectionMode selectionMode) {
        this.selectionMode = selectionMode;
    }

    /**
     * @return renderer
     */
    public ListAC.ListRenderer getRenderer() {
        return renderer;
    }

    /**
     * sets renderer
     * 
     * @param renderer
     */
    public void setRenderer(ListAC.ListRenderer renderer) {
        this.renderer = renderer;
    }

    /**
     * clear selection
     */
    public void clearSelection() {
        selectedIndices = EMPTY_INT_ARRAY;
    }
}
