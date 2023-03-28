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
package org.vpda.abstractclient.core.ui.dummy;

import org.vpda.abstractclient.core.ui.Frame;
import org.vpda.abstractclient.core.ui.FrameListenersSupport;
import org.vpda.abstractclient.core.ui.FrameParentChildSupport;
import org.vpda.clientserver.communication.data.FrameUIDef;

/**
 * @author kitko
 *
 */
public class DummyFrame implements Frame {
    private boolean isInitialized;

    /**
     * 
     */
    public DummyFrame() {
        super();
    }

    @Override
    public void setVisible(boolean visible) {
    }

    @Override
    public void initialize() {
        isInitialized = true;
    }

    @Override
    public boolean isInitialized() {
        return isInitialized;
    }

    @Override
    public void dispose() {
    }

    @Override
    public void setEnabledUI(boolean enabled) {
    }

    @Override
    public String getTitle() {
        return null;
    }

    @Override
    public void setTitle(String title) {
    }

    @Override
    public String getId() {
        return null;
    }

    @Override
    public boolean shouldRegisterToFrameChooser() {
        return false;
    }

    @Override
    public void setId(String id) {
    }

    @Override
    public boolean isVisible() {
        return false;
    }

    @Override
    public void setSelected(boolean selected) {
    }

    @Override
    public boolean isClosable() {
        return false;
    }

    @Override
    public boolean isIconifiable() {
        return false;
    }

    @Override
    public boolean isMaximizable() {
        return false;
    }

    @Override
    public boolean isModal() {
        return false;
    }

    @Override
    public boolean isResizable() {
        return false;
    }

    @Override
    public void setClosable(boolean closable) {
    }

    @Override
    public void setIconifiable(boolean iconifiable) {
    }

    @Override
    public void setMaximizable(boolean maximizable) {
    }

    @Override
    public void setResizable(boolean resizable) {
    }

    @Override
    public FrameListenersSupport getListenersSupport() {
        return null;
    }

    @Override
    public FrameParentChildSupport getParentChildSupport() {
        return null;
    }

    @Override
    public Object getFrameIcon() {
        return null;
    }

    @Override
    public void setFrameIcon(Object icon) {
    }

    @Override
    public void setUIDefinition(FrameUIDef uiDef) {
    }

}
