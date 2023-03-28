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
package org.vpda.clientserver.viewprovider.criteria;

import java.io.Serializable;

import org.vpda.common.comps.ui.AbstractComponent;
import org.vpda.common.criteria.Operator;

/**
 * Selection that for all operators but one returns same component
 * 
 * @author kitko
 *
 */
public final class SingleButOperatorSelection implements OperatorComponentSelection, Serializable {
    private static final long serialVersionUID = -5512345084709302245L;
    private AbstractComponent commonComp;
    private AbstractComponent butComp;
    private Operator butOperator;

    /**
     * Creates SingleButOperatorSelection
     * 
     * @param commonComp
     * @param butOperator
     * @param butComp
     */
    public SingleButOperatorSelection(AbstractComponent commonComp, Operator butOperator, AbstractComponent butComp) {
        super();
        this.butComp = butComp;
        this.butOperator = butOperator;
        this.commonComp = commonComp;
    }

    @Override
    public AbstractComponent getComponent(Operator operator) {
        if (butOperator.equals(operator)) {
            return butComp;
        }
        return commonComp;
    }

}