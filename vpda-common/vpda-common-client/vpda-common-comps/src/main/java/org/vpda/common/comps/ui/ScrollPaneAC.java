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

import org.vpda.common.comps.loc.AbstractCompLocValue;
import org.vpda.internal.common.util.Assert;

/**
 * Scrollpane arround component
 * 
 * @author kitko
 *
 */
public final class ScrollPaneAC extends AbstractComponent<Void, AbstractCompLocValue> {

    /** Policy for scrollbar */
    public static enum SCrollBarPolicy {
        /** Scrollbar visible only ehen needed */
        AS_NEEDED,
        /** No scrollbar */
        NEVER,
        /** Scrollbar is alwyas visible */
        ALWAYS;
    }

    private static final long serialVersionUID = -14232980271657457L;

    private AbstractComponent mainComp;
    private ScrollPaneAC.SCrollBarPolicy horizontalSBPolicy;
    private ScrollPaneAC.SCrollBarPolicy verticalSBPolicy;

    /** */
    public ScrollPaneAC() {
    }

    /**
     * Creates ViewProviderScrollPaneComponentImpl with id,comp and both scrolling
     * policies {@link SCrollBarPolicy#AS_NEEDED}
     * 
     * @param localId
     * @param mainComp
     */
    public ScrollPaneAC(String localId, AbstractComponent mainComp) {
        this(localId, mainComp, ScrollPaneAC.SCrollBarPolicy.AS_NEEDED, ScrollPaneAC.SCrollBarPolicy.AS_NEEDED);
    }

    /**
     * Creates ViewProviderScrollPaneComponentImpl with id and scrolling policies
     * 
     * @param localId
     * @param mainComp
     * @param horizonalSBpolicy
     * @param verticalSBpolicy
     */
    public ScrollPaneAC(String localId, AbstractComponent mainComp, ScrollPaneAC.SCrollBarPolicy horizonalSBpolicy, ScrollPaneAC.SCrollBarPolicy verticalSBpolicy) {
        super(localId);
        this.mainComp = Assert.isNotNullArgument(mainComp, "mainComp");
        this.horizontalSBPolicy = Assert.isNotNullArgument(horizonalSBpolicy, "horizonalSBpolicy");
        this.verticalSBPolicy = Assert.isNotNullArgument(verticalSBpolicy, "verticalSBpolicy");
    }

    /**
     * @return policy for horizontal scrollbar
     */
    public ScrollPaneAC.SCrollBarPolicy getHorizontalSBPolicy() {
        return horizontalSBPolicy;
    }

    /**
     * @return policy for vertical scrollbar
     */
    public ScrollPaneAC.SCrollBarPolicy getVerticalSBPolicy() {
        return verticalSBPolicy;
    }

    @Override
    public void adjustFromLocValue(AbstractCompLocValue locValue) {
    }

    @Override
    public AbstractCompLocValue createLocValue() {
        throw new UnsupportedOperationException("createLocValue not supported for " + getClass());
    }

    @Override
    public Class<AbstractCompLocValue> getLocValueClass() {
        return AbstractCompLocValue.class;
    }

    /**
     * @return the mainComp
     */
    public final AbstractComponent getMainComp() {
        return mainComp;
    }

    /**
     * @param mainComp the mainComp to set
     */
    public final void setMainComp(AbstractComponent mainComp) {
        this.mainComp = mainComp;
    }

    /**
     * @param horizontalSBPolicy the horizontalSBPolicy to set
     */
    public final void setHorizontalSBPolicy(ScrollPaneAC.SCrollBarPolicy horizontalSBPolicy) {
        this.horizontalSBPolicy = horizontalSBPolicy;
    }

    /**
     * @param verticalSBpolicy the verticalSBpolicy to set
     */
    public final void setVerticalSBPolicy(ScrollPaneAC.SCrollBarPolicy verticalSBpolicy) {
        this.verticalSBPolicy = verticalSBpolicy;
    }

}
