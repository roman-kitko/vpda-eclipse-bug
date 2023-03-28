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

import java.util.Collection;

/**
 * Manager for Windows on client
 * 
 * @author kitko
 *
 */
public interface WindowManager {
    /** Iconize all frames */
    public void iconizeAllFrames();

    /** Maximize all frames */
    public void maximizeAllFrames();

    /** Tile all frames */
    public void tileAllFrames();

    /** Cascade all frames */
    public void cascadeAllFrames();

    /** dispose all frames */
    public void disposeAllFrames();

    /**
     * Dispose all frames but frame with id
     * 
     * @param frameId
     */
    public void disposeOthers(String frameId);

    /**
     * Dispose frame identified by id
     * 
     * @param frameId
     */
    public void disposeFrame(String frameId);

    /**
     * Get frame by id
     * 
     * @param id
     * @return frame or null
     */
    public Frame getFrame(String id);

    /**
     * Register frame by id
     * 
     * @param frame
     */
    public void registerFrame(Frame frame);

    /**
     * Unregister frame
     * 
     * @param frame
     */
    public void unregisterFrame(Frame frame);

    /**
     * 
     * @return all registered frame ids
     */
    public Collection<String> getAllFramesIds();

    /**
     * Register and open frame
     * 
     * @param frame
     */
    public void registerAndShowFrame(Frame frame);

    /**
     * Creates frame from info, register it in manager and open frame
     * 
     * @param frameInfo
     * @return new created Frame
     */
    public Frame createRegisterAndShowFrame(FrameInfo frameInfo);

    /**
     * Creates new frame, Frame will be just created, it will not be registered in
     * manager and will not be opened
     * 
     * @param frameInfo
     * @return created frame
     */
    public Frame createFrame(FrameInfo frameInfo);

    /**
     * Open new frame
     * 
     * @return - new frame
     */
    public Frame createAndShowNewFrame();

    /**
     * Shows frame
     * 
     * @param frame
     */
    public void showFrame(Frame frame);

    /**
     * @return selected frame id or null if no frame is selected
     */
    public String getSeletectedFrameId();

    /**
     * @return selected frame or null if no frame is selected
     */
    public Frame getSelectedFrame();

    /**
     * Mark frame is selected or not
     * 
     * @param frameId
     * @param selected
     */
    public void frameSelected(String frameId, boolean selected);

    /**
     * @return count of frames
     */
    public int getFramesCount();

    /**
     * Will generate Frameid using prefix Probably this can append count of
     * registered frames to prefix
     * 
     * @param prefix
     * @return new generated frameId
     */
    public String generateFrameIdFromPrefix(String prefix);

    /**
     * Notify that frame was closed and unregister it now
     * 
     * @param frame
     */
    public void frameClosed(Frame frame);

    /**
     * 
     * @return mainFrame
     */
    public MainFrame getMainFrame();

}
