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
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.vpda.internal.common.util.Assert;

/**
 * Default impl for frame listeners support
 * 
 * @author kitko
 *
 */
public class FrameListenersSupportImpl implements FrameListenersSupportWithFire, Serializable {
    private static final long serialVersionUID = 2370707038262868069L;
    private List<FrameListener> frameListeners;
    private Frame frame;

    @Override
    public void addFrameListener(FrameListener listener) {
        if (frameListeners == null) {
            frameListeners = new CopyOnWriteArrayList<FrameListener>();
            frameListeners.add(listener);
        }
        else if (!frameListeners.contains(listener)) {
            frameListeners.add(listener);
        }
    }

    @Override
    public List<FrameListener> getFrameListeners() {
        if (frameListeners == null) {
            return Collections.emptyList();
        }
        return Collections.unmodifiableList(frameListeners);
    }

    @Override
    public void removeFrameListener(FrameListener listener) {
        if (frameListeners != null) {
            frameListeners.remove(listener);
        }
    }

    /**
     * Creates listners support
     * 
     * @param frame
     */
    public FrameListenersSupportImpl(Frame frame) {
        this.frame = Assert.isNotNullArgument(frame, "frame");
    }

    @Override
    public Frame getFrame() {
        return frame;
    }

    @Override
    public void fireAfterDisposed() {
        if (frameListeners != null) {
            for (FrameListener fl : frameListeners) {
                fl.afterFrameDisposed(new FrameEvent(frame));
            }
        }
    }

    @Override
    public void fireBeforeDisposed() {
        if (frameListeners != null) {
            for (FrameListener fl : frameListeners) {
                fl.beforeFrameDisposed(new FrameEvent(frame));
            }
        }
    }

    @Override
    public void fireAfterFrameActivated() {
        if (frameListeners != null) {
            for (FrameListener fl : frameListeners) {
                fl.afterFrameActivated(new FrameEvent(frame));
            }
        }
    }

    @Override
    public void fireAfterFrameOpened() {
        if (frameListeners != null) {
            for (FrameListener fl : frameListeners) {
                fl.afterFrameOpened(new FrameEvent(frame));
            }
        }
    }

}
