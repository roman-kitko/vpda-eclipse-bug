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
package org.vpda.common.comps.ui.resolved;

import java.io.Serializable;

import org.vpda.common.comps.ui.ComponentsEnvironment;
import org.vpda.common.comps.ui.UIComponentAccessor;

/**
 * 
 * @author kitko
 *
 * @param <VALUE>   data type
 * @param <ACCESOR> is wrapper type
 * @param <UI>      type of ui component
 */
@SuppressWarnings("unchecked")
public abstract class ComponentAccesorBasedResolver<VALUE, UI, ACCESOR extends UIComponentAccessor<VALUE, UI>> implements ComponetUIResolver<VALUE, ACCESOR>, Serializable {
    private static final long serialVersionUID = 1941166055647288243L;

    /**
     * Creates ComponentAccesorBasedResolver
     */
    public ComponentAccesorBasedResolver() {
    }

    @Override
    public ACCESOR createUIComponent(ResolvingUIBasedComponent<VALUE, ACCESOR> comp, ComponentsEnvironment env) {
        ACCESOR resolveUIAccesor = resolveUIAccesor(comp, env);
        resolveUIAccesor.updateValueToUI(comp.getValue(), comp);
        UIComponentAccessor<VALUE, UI> wrapper = env.createUIComponentAccesorWrapperUI(resolveUIAccesor);
        return (ACCESOR) wrapper;
    }

    @Override
    public void updateUIComponent(ACCESOR ui, ResolvingUIBasedComponent<VALUE, ACCESOR> comp, ComponentsEnvironment env) {
        UIComponentAccessor<VALUE, UI> wrapper = ui;
        wrapper.updateValueToUI(comp.getValue(), comp);
    }

    @Override
    public VALUE updateComponentFromUI(ACCESOR ui, ResolvingUIBasedComponent<VALUE, ACCESOR> comp, ComponentsEnvironment env) {
        UIComponentAccessor<VALUE, UI> wrapper = ui;
        VALUE updatedValue = wrapper.getUpdatedValue();
        comp.setValue(updatedValue);
        return updatedValue;
    }

    /**
     * Will resolve UI accesor
     * 
     * @param comp
     * @param env
     * @return ui native component accesor
     */
    protected abstract ACCESOR resolveUIAccesor(ResolvingUIBasedComponent<VALUE, ACCESOR> comp, ComponentsEnvironment env);

}
