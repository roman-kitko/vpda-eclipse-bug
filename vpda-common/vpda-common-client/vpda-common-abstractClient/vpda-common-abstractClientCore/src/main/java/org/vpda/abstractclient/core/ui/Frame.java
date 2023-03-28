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

import org.vpda.clientserver.communication.data.FrameUIDef;
import org.vpda.common.util.Initializee;

/**
 * Frame with displaying progress support
 * 
 * @author kitko
 *
 */
public interface Frame extends Initializee, UIDisposable {
    /**
     * Sets Frame to visible(true/false)
     * 
     * @param visible
     */
    public void setVisible(boolean visible);

    /**
     * 
     * @return true if visible
     */
    public boolean isVisible();

    /**
     * Dispose frame
     *
     */
    @Override
    public void dispose();

    /**
     * Enable ui - components ...
     * 
     * @param enabled
     */
    public void setEnabledUI(boolean enabled);

    /**
     * 
     * @return title of frame
     */
    public String getTitle();

    /**
     * 
     * @return current icon
     */
    public Object getFrameIcon();

    /**
     * sets icon
     * 
     * @param icon
     */
    public void setFrameIcon(Object icon);

    /**
     * Sets tiltle of frame
     * 
     * @param title
     */
    public void setTitle(String title);

    /**
     * 
     * @return id of frame to be used in window manager
     */
    public String getId();

    /**
     * Sets is
     * 
     * @param id
     */
    public void setId(String id);

    /**
     * 
     * @return true if this frame should be registered to frame chooser
     */
    public boolean shouldRegisterToFrameChooser();

    /**
     * Selectes or deselect frame
     * 
     * @param selected
     *
     */
    public void setSelected(boolean selected);

    /**
     * @return true if frame is closeable
     */
    public boolean isClosable();

    /**
     * @param closable the closable to set
     */
    public void setClosable(boolean closable);

    /**
     * @return the iconifiable
     */
    public boolean isIconifiable();

    /**
     * @param iconifiable the iconifiable to set
     */
    public void setIconifiable(boolean iconifiable);

    /**
     * @return the isModal
     */
    public boolean isModal();

    /**
     * @return the maximizable
     */
    public boolean isMaximizable();

    /**
     * @param maximizable the maximizable to set
     */
    public void setMaximizable(boolean maximizable);

    /**
     * @return the resizable
     */
    public boolean isResizable();

    /**
     * @param resizable the resizable to set
     */
    public void setResizable(boolean resizable);

    /**
     * @return listeners support for this frame
     */
    public FrameListenersSupport getListenersSupport();

    /**
     * @return optional support for parent-child frame support
     */
    public FrameParentChildSupport getParentChildSupport();

    /**
     * Sets new ui def
     * 
     * @param uiDef
     */
    public void setUIDefinition(FrameUIDef uiDef);
}
