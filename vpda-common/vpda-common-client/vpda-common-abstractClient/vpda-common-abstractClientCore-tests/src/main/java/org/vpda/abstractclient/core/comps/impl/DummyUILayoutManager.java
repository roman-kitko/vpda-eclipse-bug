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
package org.vpda.abstractclient.core.comps.impl;

import org.vpda.abstractclient.core.comps.ComponentsUI;
import org.vpda.abstractclient.core.comps.UILayoutManager;
import org.vpda.common.comps.ui.Component;
import org.vpda.common.comps.ui.ContainerAC;
import org.vpda.common.comps.ui.ContainerLayout;

/**
 * Dummy impl of {@link UILayoutManager}
 * 
 * @author kitko
 *
 */
public final class DummyUILayoutManager implements UILayoutManager {

    @Override
    public void addComponent(ComponentsUI providerUI, ContainerAC container, Object containerImpl, org.vpda.common.comps.ui.Component comp, Object componentImpl) {
    }

    @Override
    public Class<? extends ContainerLayout> getContainerLayoutClass() {
        return null;
    }

    @Override
    public void removeComponent(ComponentsUI providerUI, ContainerAC container, Object containerImpl, Component comp, Object componentImpl) {
    }

    @Override
    public void setupLayout(ComponentsUI providerUI, ContainerAC container, Object containerImpl) {
    }

    @Override
    public Object createUIContainer(ComponentsUI providerUI, ContainerAC container) {
        throw new UnsupportedOperationException();
    }

}
