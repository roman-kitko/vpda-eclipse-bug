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

import java.util.List;

/**
 * This interface will encapsulate listeners handling for frame
 * 
 * @author kitko
 *
 */
public interface FrameListenersSupport {

    /**
     * Adds frame listener to frame
     * 
     * @param listener
     */
    public void addFrameListener(FrameListener listener);

    /**
     * Removes Frame listener
     * 
     * @param listener
     */
    public void removeFrameListener(FrameListener listener);

    /**
     * @return list of all frame listeners
     */
    public List<FrameListener> getFrameListeners();

    /**
     * @return Frame this support is assciated with
     */
    public Frame getFrame();

}
