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
package org.vpda.abstractclient.core.ui;

import java.io.Serializable;

/**
 * This listener is informed about some frame events
 * 
 * @author kitko
 *
 */
public interface FrameListener extends Serializable {

    /**
     * Called after frame was opened
     * 
     * @param event
     */
    public void afterFrameOpened(FrameEvent event);

    /**
     * Called after frame activation
     * 
     * @param event
     */
    public void afterFrameActivated(FrameEvent event);

    /**
     * Called before dispose
     * 
     * @param event
     */
    public void beforeFrameDisposed(FrameEvent event);

    /**
     * Called after dispose
     * 
     * @param event
     */
    public void afterFrameDisposed(FrameEvent event);

}
