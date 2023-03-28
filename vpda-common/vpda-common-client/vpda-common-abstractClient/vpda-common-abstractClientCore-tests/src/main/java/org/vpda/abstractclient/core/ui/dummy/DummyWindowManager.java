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

import java.util.Collection;

import org.vpda.abstractclient.core.ui.Frame;
import org.vpda.abstractclient.core.ui.FrameInfo;
import org.vpda.abstractclient.core.ui.MainFrame;
import org.vpda.abstractclient.core.ui.WindowManager;

class DummyWindowManager implements WindowManager {

    @Override
    public void cascadeAllFrames() {
    }

    @Override
    public void disposeAllFrames() {
    }

    @Override
    public Frame getFrame(String id) {
        return null;
    }

    @Override
    public void iconizeAllFrames() {
    }

    @Override
    public Collection<String> getAllFramesIds() {
        return null;
    }

    @Override
    public Frame createAndShowNewFrame() {
        return null;
    }

    @Override
    public void registerFrame(Frame frame) {
    }

    @Override
    public void tileAllFrames() {
    }

    @Override
    public void unregisterFrame(Frame frame) {
    }

    @Override
    public void registerAndShowFrame(Frame frame) {
    }

    @Override
    public Frame createFrame(FrameInfo frameInfo) {
        return null;
    }

    @Override
    public Frame createRegisterAndShowFrame(FrameInfo frameInfo) {
        return null;
    }

    @Override
    public void showFrame(Frame frame) {
    }

    @Override
    public String getSeletectedFrameId() {
        return null;
    }

    @Override
    public void frameSelected(String frameId, boolean selected) {
    }

    @Override
    public int getFramesCount() {
        return 0;
    }

    @Override
    public Frame getSelectedFrame() {
        return null;
    }

    @Override
    public String generateFrameIdFromPrefix(String prefix) {
        return null;
    }

    @Override
    public void frameClosed(Frame frame) {
    }

    @Override
    public MainFrame getMainFrame() {
        return null;
    }

    @Override
    public void disposeOthers(String frameId) {
    }

    @Override
    public void maximizeAllFrames() {
    }

    @Override
    public void disposeFrame(String frameId) {
    }

}
