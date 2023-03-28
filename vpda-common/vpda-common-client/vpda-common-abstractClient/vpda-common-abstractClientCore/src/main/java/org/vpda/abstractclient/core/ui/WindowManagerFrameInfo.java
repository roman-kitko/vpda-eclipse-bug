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

import org.vpda.internal.common.util.Assert;

/**
 * Extra info windowManager needs to keep for frames registered in window
 * manager
 * 
 * @author kitko
 *
 */
public final class WindowManagerFrameInfo implements Serializable {
    private static final long serialVersionUID = -3917973317095660107L;
    private final Frame frame;
    private boolean alreadyShown;

    /**
     * @param frame
     * @param alreadyShown
     */
    public WindowManagerFrameInfo(Frame frame, boolean alreadyShown) {
        super();
        this.frame = Assert.isNotNull(frame);
        this.alreadyShown = alreadyShown;
    }

    /**
     * @param frame
     */
    public WindowManagerFrameInfo(Frame frame) {
        this(frame, false);
    }

    /**
     * @return the alreadyShown
     */
    public boolean isAlreadyShown() {
        return alreadyShown;
    }

    /**
     * @param alreadyShown the alreadyShown to set
     */
    public void setAlreadyShown(boolean alreadyShown) {
        this.alreadyShown = alreadyShown;
    }

    /**
     * @return the frame
     */
    public Frame getFrame() {
        return frame;
    }

    @Override
    public String toString() {
        return frame.toString();
    }

}
