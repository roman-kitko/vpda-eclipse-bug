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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.vpda.internal.common.util.Assert;

/**
 * Basic implementation for {@link FrameParentChildSupport}
 * 
 * @author kitko
 *
 */
public final class FrameParentChildSupportImpl implements FrameParentChildSupport, Serializable {
    private static final long serialVersionUID = 1228859047302548045L;
    private String selectedChildId;
    private final Frame frame;
    private final List<String> children;
    private final String parentId;

    @Override
    public void childClosed(String childId) {
        if (selectedChildId != null && selectedChildId.equals(childId)) {
            selectedChildId = null;
            frame.setEnabledUI(true);
        }
        children.remove(childId);
    }

    @Override
    public void childSelected(String childId, boolean selected) {
        if (selected) {
            this.selectedChildId = childId;
        }
    }

    @Override
    public Frame getFrame() {
        return frame;
    }

    @Override
    public String getSelectedChild() {
        return selectedChildId;
    }

    /**
     * Creates parent-child support for concrete frame
     * 
     * @param frame
     * @param parentId
     */
    public FrameParentChildSupportImpl(Frame frame, String parentId) {
        this.frame = Assert.isNotNull(frame);
        this.parentId = parentId;
        children = new ArrayList<String>(2);
    }

    @Override
    public void addChild(String childId) {
        children.add(childId);
    }

    @Override
    public String getParentId() {
        return parentId;
    }

    @Override
    public List<String> getChildren() {
        return Collections.unmodifiableList(children);
    }

}
