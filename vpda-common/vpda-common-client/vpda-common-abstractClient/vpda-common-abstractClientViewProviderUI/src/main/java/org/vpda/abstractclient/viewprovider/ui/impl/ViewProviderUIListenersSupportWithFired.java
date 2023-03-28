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
package org.vpda.abstractclient.viewprovider.ui.impl;

import org.vpda.abstractclient.viewprovider.ui.ViewProviderUI;
import org.vpda.abstractclient.viewprovider.ui.ViewProviderUIListenersSupport;

/**
 * Listener support with firing of events
 * 
 * @author rki
 *
 */
public interface ViewProviderUIListenersSupportWithFired extends ViewProviderUIListenersSupport {
    /**
     * Fires after init event
     */
    public void fireAfterInit();

    /**
     * Fires before dispose event
     */
    public void fireBeforeDispose();

    /**
     * Fire after dispose event on all registered listeners
     */
    public void fireAfterDispose();

    /**
     * Fire child was added
     * 
     * @param child
     */
    public void fireChildAdded(ViewProviderUI child);

    /**
     * Fire child was removed
     * 
     * @param child
     */
    public void fireChildRemoved(ViewProviderUI child);

    /**
     * 
     */
    public void fireLayoutChanged();

}
