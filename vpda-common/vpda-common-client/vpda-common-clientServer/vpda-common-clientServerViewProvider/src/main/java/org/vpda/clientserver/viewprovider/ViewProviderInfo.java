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
package org.vpda.clientserver.viewprovider;

import java.io.Serializable;

import org.vpda.common.comps.shortcuts.UIShortcuts;
import org.vpda.common.comps.ui.ACConstants;
import org.vpda.common.comps.ui.Container;
import org.vpda.common.comps.ui.ContainerAC;
import org.vpda.common.comps.ui.menu.MenuBarAC;
import org.vpda.common.context.TenementalContext;
import org.vpda.common.service.localization.LocPair;
import org.vpda.common.service.localization.LocalizationService;
import org.vpda.internal.common.util.Assert;

/**
 * Abstraction data holder for {@link ViewProviderInfo}
 * 
 * @author kitko
 *
 */
public abstract class ViewProviderInfo implements Serializable {
    private static final long serialVersionUID = 4470210796413965663L;
    /** Id of provider */
    protected final ViewProviderID viewProviderID;
    /** Root component */
    protected final Container rootViewProviderComponentContainer;
    /** Localization of provider */
    protected final LocPair<ViewProviderLocValue> locValue;
    private static final ContainerAC EMPTY_CONTAINER = new ContainerAC(ACConstants.ROOT_CONTAINER);
    /** Menu bar */
    protected final MenuBarAC menuBar;
    /** ViewProviderShurtcuts */
    protected final UIShortcuts viewProviderShurtcuts;

    /**
     * @return providerid
     */
    public ViewProviderID getViewProviderID() {
        return viewProviderID;
    }

    /**
     * Creates AbstractViewProviderInfoImpl from builder
     * 
     * @param builder
     */
    protected ViewProviderInfo(Builder<?> builder) {
        this.viewProviderID = Assert.isNotNullArgument(builder.getViewProviderID(), "viewProviderID");
        this.rootViewProviderComponentContainer = builder.getRootViewProviderComponentContainer();
        this.locValue = builder.getLocValue();
        this.menuBar = builder.getMenuBar();
        this.viewProviderShurtcuts = builder.getViewProviderShurtcuts();
    }

    /**
     * @return root container
     */
    public Container getRootViewProviderComponentContainer() {
        if (rootViewProviderComponentContainer == null) {
            return EMPTY_CONTAINER;
        }
        return rootViewProviderComponentContainer;
    }

    /**
     * @return locvalue
     */
    public LocPair<ViewProviderLocValue> getLocValue() {
        return locValue;
    }

    /**
     * @return menuBar
     */
    public MenuBarAC getMenuBar() {
        return menuBar;
    }

    /**
     * @return the viewProviderShurtcuts
     */
    public UIShortcuts getViewProviderShurtcuts() {
        return viewProviderShurtcuts;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("ViewProviderInfo [viewProviderID=");
        builder.append(viewProviderID);
        builder.append(", rootViewProviderComponentContainer=");
        builder.append(rootViewProviderComponentContainer);
        builder.append(", locValue=");
        builder.append(locValue);
        builder.append("]");
        return builder.toString();
    }

    /**
     * @param <T>
     * @return new Builder for concrete info subclass
     */
    protected abstract <T extends ViewProviderInfo> Builder<T> createBuilder();

    /**
     * Abstract builder of ViewProviderInfo
     * 
     * @param <T> type of ViewProviderInfo
     */
    public static abstract class Builder<T extends ViewProviderInfo> implements org.vpda.common.util.Builder<T> {
        private ViewProviderID viewProviderID;
        private LocPair<ViewProviderLocValue> locValue;
        private Container rootViewProviderComponentContainer;
        private MenuBarAC menuBar;
        private UIShortcuts viewProviderShurtcuts;

        /**
         * @return the viewProviderShurtcuts
         */
        public UIShortcuts getViewProviderShurtcuts() {
            return viewProviderShurtcuts;
        }

        /**
         * @param viewProviderShurtcuts the viewProviderShurtcuts to set
         * @return this
         */
        public Builder setViewProviderShurtcuts(UIShortcuts viewProviderShurtcuts) {
            this.viewProviderShurtcuts = viewProviderShurtcuts;
            return this;
        }

        /**
         * @return locvalue
         */
        public LocPair<ViewProviderLocValue> getLocValue() {
            return locValue;
        }

        /**
         * @return menuBar
         */
        public MenuBarAC getMenuBar() {
            return menuBar;
        }

        /**
         * @return root container
         */
        public Container getRootViewProviderComponentContainer() {
            return rootViewProviderComponentContainer;
        }

        /**
         * @return providerID
         */
        public ViewProviderID getViewProviderID() {
            return viewProviderID;
        }

        /**
         * @param locValue
         * @return this
         */
        public Builder setLocValue(LocPair<ViewProviderLocValue> locValue) {
            this.locValue = locValue;
            return this;
        }

        /**
         * @param menubar
         * @return this
         */
        public Builder setMenuBar(MenuBarAC menubar) {
            this.menuBar = menubar;
            return this;
        }

        /**
         * @param rootViewProviderComponentContainer
         * @return this
         */
        public Builder setRootViewProviderComponentContainer(ContainerAC rootViewProviderComponentContainer) {
            this.rootViewProviderComponentContainer = rootViewProviderComponentContainer;
            return this;
        }

        /**
         * @param values
         * @return this
         */
        public Builder setValues(T values) {
            this.locValue = values.getLocValue();
            this.menuBar = values.getMenuBar();
            this.rootViewProviderComponentContainer = values.getRootViewProviderComponentContainer();
            this.viewProviderID = values.getViewProviderID();
            this.viewProviderShurtcuts = values.getViewProviderShurtcuts();
            return this;
        }

        /**
         * @param viewProviderID
         * @return this
         */
        public Builder setViewProviderID(ViewProviderID viewProviderID) {
            this.viewProviderID = viewProviderID;
            return this;
        }
    }

    /**
     * @author kitko
     *
     */
    public static final class Lozalizer implements org.vpda.common.service.localization.Localizer<ViewProviderInfo> {

        @Override
        public ViewProviderInfo localize(ViewProviderInfo t, LocalizationService localizationService, TenementalContext context) {
            Builder<ViewProviderInfo> builder = t.createBuilder();
            builder.setValues(t);
            builder.setLocValue(builder.getLocValue().localize(localizationService, context));
            return builder.build();
        }

    }

}
