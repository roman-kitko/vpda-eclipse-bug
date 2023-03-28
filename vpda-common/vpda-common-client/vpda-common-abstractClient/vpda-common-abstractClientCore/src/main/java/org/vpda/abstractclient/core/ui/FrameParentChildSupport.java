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
 * Notify method from child or parent to frame
 * 
 * @author kitko
 *
 */
public interface FrameParentChildSupport {
    /**
     * Inform parent that child was selected
     * 
     * @param childId
     * @param selected
     */
    public void childSelected(String childId, boolean selected);

    /**
     * Child was closed
     * 
     * @param childId
     */
    public void childClosed(String childId);

    /**
     * @return current frame for this support
     */
    public Frame getFrame();

    /**
     * @return id of selected child or null if no child selected
     */
    public String getSelectedChild();

    /**
     * 
     * @return parentId or null if frame has no parent
     */
    public String getParentId();

    /**
     * Add child
     * 
     * @param childId
     */
    public void addChild(String childId);

    /**
     * @return list of all children
     */
    public List<String> getChildren();
}
