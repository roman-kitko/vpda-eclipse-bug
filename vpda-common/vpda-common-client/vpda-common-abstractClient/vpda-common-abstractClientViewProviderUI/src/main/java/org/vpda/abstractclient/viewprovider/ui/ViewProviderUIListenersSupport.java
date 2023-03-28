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
package org.vpda.abstractclient.viewprovider.ui;

import java.util.List;

/**
 * Will provide listener support for provider ui.
 * 
 * @author kitko
 *
 */
public interface ViewProviderUIListenersSupport {

    /**
     * @return provider ui this listener support is assictiated with
     */
    public ViewProviderUI getProviderUI();

    /**
     * Add dispose listener
     * 
     * @param disposeListener
     */
    public void addDisposeListener(DisposeListener disposeListener);

    /**
     * Add init listener
     * 
     * @param initListener
     */
    public void addInitListener(InitListener initListener);

    /**
     * Adds UIStructureListener
     * 
     * @param listener
     */
    public void addUIStructureListener(UIStructureListener listener);

    /**
     * @param listener
     */
    public void addLayoutListener(ViewProviderLayoutListener listener);

    /**
     * Will remove listener if registered
     * 
     * @param clazz    - kind of listener
     * @param listener
     */
    public void removeListener(Class<? extends ViewProviderUIListener> clazz, ViewProviderUIListener listener);

    /**
     * @return all listeners
     */
    public List<? extends ViewProviderUIListener> getAllListeners();

    /**
     * @param <T>
     * @param clazz
     * @return all listeners by class
     */
    public <T extends ViewProviderUIListener> List<T> getListeners(Class<T> clazz);

}
