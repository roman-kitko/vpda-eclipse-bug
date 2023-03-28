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
import java.util.Set;

import org.vpda.common.annotations.Immutable;

/**
 * This is holder of information to create frame
 * 
 * @author kitko
 *
 */
@Immutable
public final class FrameInfo implements Serializable {
    private static final long serialVersionUID = 8965826263306730699L;
    private final String title;
    private final boolean resizable;
    private final boolean closable;
    private final boolean maximizable;
    private final boolean iconifiable;
    private final boolean canRegisterToFrameChooser;
    private final String id;
    private final String parentId;
    private final boolean modal;

    /**
     * @return the closable
     */
    public boolean isClosable() {
        return closable;
    }

    /**
     * @return the iconifiable
     */
    public boolean isIconifiable() {
        return iconifiable;
    }

    /**
     * @return the isModal
     */
    public boolean isModal() {
        return modal;
    }

    /**
     * @return the isViewProviderFrame
     */
    public boolean canRegisterToFrameChooser() {
        return canRegisterToFrameChooser;
    }

    /**
     * @return the maximizable
     */
    public boolean isMaximizable() {
        return maximizable;
    }

    /**
     * @return the parentId
     */
    public String getParentId() {
        return parentId;
    }

    /**
     * @return the resizable
     */
    public boolean isResizable() {
        return resizable;
    }

    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    private FrameInfo(FrameInfoBuilder builder) {
        this.closable = builder.closable;
        this.iconifiable = builder.iconifiable;
        this.id = builder.id;
        this.modal = builder.modal;
        this.maximizable = builder.maximizable;
        this.parentId = builder.parentId;
        this.resizable = builder.resizable;
        this.canRegisterToFrameChooser = builder.canRegisterToFrameChooser;
        this.title = builder.title;
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * Builder for FrameInfo
     * 
     * @author kitko
     *
     */
    public static final class FrameInfoBuilder implements org.vpda.common.util.Builder {
        private String title;
        private boolean resizable;
        private boolean closable;
        private boolean maximizable;
        private boolean iconifiable;
        private boolean canRegisterToFrameChooser;
        private String id;
        private String parentId;
        private boolean modal;

        /**
         * Flags for frameInfo
         * 
         * @author kitko
         *
         */
        public static enum FrameInfoFlags {
            /** */
            RESIZABLE,
            /** */
            CLOSEABLE,
            /** */
            MAXIMIZABLE,
            /** */
            ICONFIABLE,
            /** */
            CAN_REGISTER_TO_FRAME_CHOOSER,
            /** */
            MODAL
        };

        /**
         * Sets flags in set to true. Other flags will remain unchanged
         * 
         * @param flags that should be set to true
         * @return this
         */
        public FrameInfoBuilder addFlags(Set<FrameInfoFlags> flags) {
            resizable = flags.contains(FrameInfoFlags.RESIZABLE) ? true : resizable;
            closable = flags.contains(FrameInfoFlags.CLOSEABLE) ? true : closable;
            maximizable = flags.contains(FrameInfoFlags.MAXIMIZABLE) ? true : maximizable;
            iconifiable = flags.contains(FrameInfoFlags.ICONFIABLE) ? true : iconifiable;
            canRegisterToFrameChooser = flags.contains(FrameInfoFlags.CAN_REGISTER_TO_FRAME_CHOOSER) ? true : canRegisterToFrameChooser;
            modal = flags.contains(FrameInfoFlags.MODAL) ? true : modal;
            return this;
        }

        /**
         * Sets flags in set in false. Other flags will remain unchanged
         * 
         * @param flags that should be set to false
         * @return this
         */
        public FrameInfoBuilder removeFlags(Set<FrameInfoFlags> flags) {
            resizable = flags.contains(FrameInfoFlags.RESIZABLE) ? false : resizable;
            closable = flags.contains(FrameInfoFlags.CLOSEABLE) ? false : closable;
            maximizable = flags.contains(FrameInfoFlags.MAXIMIZABLE) ? false : maximizable;
            iconifiable = flags.contains(FrameInfoFlags.ICONFIABLE) ? false : iconifiable;
            canRegisterToFrameChooser = flags.contains(FrameInfoFlags.CAN_REGISTER_TO_FRAME_CHOOSER) ? false : canRegisterToFrameChooser;
            modal = flags.contains(FrameInfoFlags.MODAL) ? false : modal;
            return this;
        }

        /**
         * Creates builder
         */
        public FrameInfoBuilder() {
        }

        /**
         * Sets values from FrameInfo
         * 
         * @param info
         * @return this
         */
        public FrameInfoBuilder setValues(FrameInfo info) {
            this.closable = info.closable;
            this.iconifiable = info.iconifiable;
            this.id = info.id;
            this.modal = info.modal;
            this.maximizable = info.maximizable;
            this.parentId = info.parentId;
            this.resizable = info.resizable;
            this.canRegisterToFrameChooser = info.canRegisterToFrameChooser;
            this.title = info.title;
            return this;
        }

        /**
         * @return the title
         */
        public String getTitle() {
            return title;
        }

        /**
         * @param title the title to set
         * @return this
         */
        public FrameInfoBuilder setTitle(String title) {
            this.title = title;
            return this;
        }

        /**
         * @return the resizable
         */
        public boolean isResizable() {
            return resizable;
        }

        /**
         * @param resizable the resizable to set
         * @return this
         */
        public FrameInfoBuilder setResizable(boolean resizable) {
            this.resizable = resizable;
            return this;
        }

        /**
         * @return the closable
         */
        public boolean isClosable() {
            return closable;
        }

        /**
         * @param closable the closable to set
         * @return this
         */
        public FrameInfoBuilder setClosable(boolean closable) {
            this.closable = closable;
            return this;
        }

        /**
         * @return the maximizable
         */
        public boolean isMaximizable() {
            return maximizable;
        }

        /**
         * @param maximizable the maximizable to set
         * @return this
         */
        public FrameInfoBuilder setMaximizable(boolean maximizable) {
            this.maximizable = maximizable;
            return this;
        }

        /**
         * @return the iconifiable
         */
        public boolean isIconifiable() {
            return iconifiable;
        }

        /**
         * @param iconifiable the iconifiable to set
         * @return this
         */
        public FrameInfoBuilder setIconifiable(boolean iconifiable) {
            this.iconifiable = iconifiable;
            return this;
        }

        /**
         * @return the shouldRegisterToFrameChooser
         */
        public boolean isShouldRegisterToFrameChooser() {
            return canRegisterToFrameChooser;
        }

        /**
         * @param shouldRegisterToFrameChooser the shouldRegisterToFrameChooser to set
         * @return this
         */
        public FrameInfoBuilder setShouldRegisterToFrameChooser(boolean shouldRegisterToFrameChooser) {
            this.canRegisterToFrameChooser = shouldRegisterToFrameChooser;
            return this;
        }

        /**
         * @return the id
         */
        public String getId() {
            return id;
        }

        /**
         * @param id the id to set
         * @return this
         */
        public FrameInfoBuilder setId(String id) {
            this.id = id;
            return this;
        }

        /**
         * @return the parentId
         */
        public String getParentId() {
            return parentId;
        }

        /**
         * @param parentId the parentId to set
         * @return this
         */
        public FrameInfoBuilder setParentId(String parentId) {
            this.parentId = parentId;
            return this;
        }

        /**
         * @return the isModal
         */
        public boolean isModal() {
            return modal;
        }

        /**
         * @param isModal the isModal to set
         * @return this
         */
        public FrameInfoBuilder setModal(boolean isModal) {
            this.modal = isModal;
            return this;
        }

        /**
         * @return creates FrameInfo from builder
         */
        @Override
        public FrameInfo build() {
            return new FrameInfo(this);
        }

        @Override
        public Class getTargetClass() {
            return FrameInfo.class;
        }

    }

}
