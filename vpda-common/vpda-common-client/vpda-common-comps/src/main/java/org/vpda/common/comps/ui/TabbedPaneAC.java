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

import java.io.Serializable;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.vpda.common.command.Command;
import org.vpda.common.comps.loc.AbstractCompLocValue;
import org.vpda.common.comps.loc.LabelLocValue;
import org.vpda.common.context.TenementalContext;
import org.vpda.common.service.localization.LocValueBuilder;
import org.vpda.common.service.localization.LocalizationService;

/**
 * Tabs container
 * 
 * @author kitko
 *
 */
public final class TabbedPaneAC extends AbstractComponent<Void, AbstractCompLocValue> {

    /**
     * One tab in tabbed pane.
     * 
     * @author kitko
     *
     */
    public static final class Tab implements Serializable {
        private static final long serialVersionUID = 6870434424480066912L;
        private LabelLocValue label;
        private AbstractComponent comp;
        private String locKey;
        private boolean enabled;
        private boolean closable;

        private Tab(LabelLocValue label, AbstractComponent comp) {
            super();
            this.label = label;
            this.comp = comp;
            this.enabled = true;
            this.closable = false;
        }

        private Tab(String locKey, AbstractComponent comp) {
            super();
            this.comp = comp;
            this.locKey = locKey;
            this.label = new LabelLocValue(locKey);
            this.enabled = true;
            this.closable = false;
        }

        /**
         * @return the closable
         */
        public boolean isClosable() {
            return closable;
        }

        /**
         * @param closable the closable to set
         * @return this
         */
        public Tab setClosable(boolean closable) {
            this.closable = closable;
            return this;
        }

        /**
         * @return the label
         */
        public LabelLocValue getLabel() {
            return label;
        }

        /**
         * @return the comp
         */
        public Component<?, ?> getComp() {
            return comp;
        }

        /**
         * @return the enabled
         */
        public boolean isEnabled() {
            return enabled;
        }

        /**
         * @param enabled the enabled to set
         * @return this
         */
        public Tab setEnabled(boolean enabled) {
            this.enabled = enabled;
            return this;
        }

    }

    private List<Tab> tabs;
    private Command changeCommand;
    private int selectedTabIndex;

    /** */
    public TabbedPaneAC() {
        tabs = new CopyOnWriteArrayList<Tab>();
        selectedTabIndex = -1;
    }

    /**
     * Creates empty tab
     * 
     * @param localId
     */
    public TabbedPaneAC(String localId) {
        super(localId);
        tabs = new CopyOnWriteArrayList<Tab>();
        selectedTabIndex = -1;
    }

    /**
     * Adds tab with label and component
     * 
     * @param label
     * @param comp
     * @return this
     */
    public TabbedPaneAC addTab(LabelLocValue label, AbstractComponent comp) {
        return insertTab(label, comp, tabs.size());
    }

    /**
     * Adds tab with localization key
     * 
     * @param locKey Relative localization key
     * @param comp
     * @return this
     */
    public TabbedPaneAC addTab(String locKey, AbstractComponent comp) {
        return insertTab(locKey, comp, tabs.size());
    }

    /**
     * Inserts tab at index
     * 
     * @param label
     * @param comp
     * @param index
     * @return this
     */
    public TabbedPaneAC insertTab(LabelLocValue label, AbstractComponent comp, int index) {
        if (label == null) {
            throw new IllegalArgumentException("Label argument is null");
        }
        if (comp == null) {
            throw new IllegalArgumentException("Comp argument is null");
        }
        for (Tab tab : tabs) {
            if (tab.comp == comp) {
                throw new IllegalArgumentException("Comp " + comp.getId() + " is already in another tab");
            }
        }
        tabs.add(index, new Tab(label, comp));
        if (selectedTabIndex == -1) {
            selectedTabIndex = 0;
        }
        return this;
    }

    /**
     * Inserts tab at index
     * 
     * @param locKey Relative localization key
     * @param comp
     * @param index
     * @return this
     */
    public TabbedPaneAC insertTab(String locKey, AbstractComponent comp, int index) {
        if (locKey == null) {
            throw new IllegalArgumentException("LocKey argument is null");
        }
        if (comp == null) {
            throw new IllegalArgumentException("Comp argument is null");
        }
        for (Tab tab : tabs) {
            if (tab.comp == comp) {
                throw new IllegalArgumentException("Comp " + comp.getId() + " is already in another tab");
            }
        }
        tabs.add(index, new Tab(locKey, comp));
        if (selectedTabIndex == -1) {
            selectedTabIndex = 0;
        }
        return this;
    }

    /**
     * Removes tab containing component
     * 
     * @param comp
     * @return tab that contained component or null if not found
     */
    public Tab removeTab(AbstractComponent comp) {
        Tab ret = null;
        for (Iterator<Tab> i = tabs.iterator(); i.hasNext();) {
            Tab tab = i.next();
            if (tab.comp.equals(comp)) {
                i.remove();
                ret = tab;
                break;
            }
        }
        selectedTabIndex = -1;
        return ret;
    }

    /**
     * Removes tab by index
     * 
     * @param index
     * @return removed tab
     */
    public Tab removeTab(int index) {
        Tab remove = tabs.remove(index);
        selectedTabIndex = -1;
        return remove;
    }

    /**
     * @return count of tabs
     */
    public int getTabsCount() {
        return tabs.size();
    }

    /**
     * List of tabs
     * 
     * @return all tabs
     */
    public List<Tab> getTabs() {
        return Collections.unmodifiableList(tabs);
    }

    /**
     * @param index
     * @return tab at position
     */
    public Tab getTab(int index) {
        return tabs.get(index);
    }

    /**
     * @return the selectedTabIndex
     */
    public int getSelectedTabIndex() {
        return selectedTabIndex;
    }

    /**
     * @param selectedTabIndex the selectedTabIndex to set
     * @return this
     */
    public TabbedPaneAC setSelectedTabIndex(int selectedTabIndex) {
        if (selectedTabIndex != -1 && selectedTabIndex < 0 || selectedTabIndex >= tabs.size()) {
            throw new ArrayIndexOutOfBoundsException("Invalid selectedTabIndex");
        }
        this.selectedTabIndex = selectedTabIndex;
        return this;
    }

    /**
     * @return the changeCommand
     */
    public Command getChangeCommand() {
        return changeCommand;
    }

    /**
     * @param changeCommand the changeCommand to set
     */
    public void setChangeCommand(Command changeCommand) {
        this.changeCommand = changeCommand;
    }

    private static final long serialVersionUID = -2930809820956599891L;

    @Override
    protected void adjustFromLocValue(AbstractCompLocValue locValue) {
    }

    @Override
    protected AbstractCompLocValue createLocValue() {
        return null;
    }

    @Override
    public Class<AbstractCompLocValue> getLocValueClass() {
        return null;
    }

    @Override
    public void localize(LocalizationService localizationService, TenementalContext context) {
        if (locKey != null) {
            LocValueBuilder<LabelLocValue> labelBuilder = localizationService.getLocValueBuilderFactory().createBuilderByLocValueClass(LabelLocValue.class);
            for (Tab tab : tabs) {
                tab.label = localizationService.localizeData(locKey.createChildKey(tab.locKey), context, labelBuilder, null, null);
                if (tab.label == null) {
                    tab.label = new LabelLocValue(tab.locKey);
                }
            }
        }
    }
}
