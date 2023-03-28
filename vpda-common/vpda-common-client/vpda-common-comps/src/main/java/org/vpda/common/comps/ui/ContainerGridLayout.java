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
 * Grid layout
 * 
 * @author kitko
 *
 */
public final class ContainerGridLayout extends AbstractContainerLayout {
    private static final long serialVersionUID = 8065946355532448134L;
    private int colsCount;
    private int rowsCount;
    private int hGap;
    private int vGap;

    /**
     * 
     */
    public ContainerGridLayout() {
    }

    /**
     * @param rowsCount
     * @param colsCount
     */
    public ContainerGridLayout(int rowsCount, int colsCount) {
        this(rowsCount, colsCount, 5, 5);
    }

    /**
     * Creates ContainerGridLayoutImpl with columns count
     * 
     * @param colsCount
     */
    public ContainerGridLayout(int colsCount) {
        this(0, colsCount, 5, 5);
    }

    /**
     * @param rowsCount
     * @param colsCount
     * @param hGap
     * @param vGap
     */
    public ContainerGridLayout(int rowsCount, int colsCount, int hGap, int vGap) {
        this.rowsCount = rowsCount;
        this.colsCount = colsCount;
        this.hGap = hGap;
        this.vGap = vGap;
    }

    /**
     * @return columns count
     */
    public int getColsCount() {
        return colsCount;
    }

    /**
     * @return horizontal gap
     */
    public int getHGap() {
        return hGap;
    }

    /**
     * @return rows count
     */
    public int getRowsCount() {
        return rowsCount;
    }

    /**
     * @return vertical gap
     */
    public int getVGap() {
        return vGap;
    }

    /**
     * @param colsCount the colsCount to set
     */
    public void setColsCount(int colsCount) {
        this.colsCount = colsCount;
    }

    /**
     * @param rowsCount the rowsCount to set
     */
    public void setRowsCount(int rowsCount) {
        this.rowsCount = rowsCount;
    }

    /**
     * @param gap the hGap to set
     */
    public void setHGap(int gap) {
        hGap = gap;
    }

    /**
     * @param gap the vGap to set
     */
    public void setVGap(int gap) {
        vGap = gap;
    }

    @Override
    public void componentAdded(Component component, ComponentLayoutConstraint contraint) {
        if (colsCount != 0) {
            int totalComponentsCount = targetContainer.getComponentsCount();
            rowsCount = totalComponentsCount / colsCount;
        }
    }

    @Override
    public void componentRemoved(Component component, ComponentLayoutConstraint contraint) {
        if (colsCount != 0) {
            int totalComponentsCount = targetContainer.getComponentsCount();
            rowsCount = totalComponentsCount / colsCount;
        }
    }

}
