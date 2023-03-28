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
/**
 * 
 */
package org.vpda.common.comps.ui;

import java.io.Serializable;

import org.vpda.common.command.CommandEvent;

/**
 * @author kitko
 *
 */
public final class ComponentIdCommandEvent implements CommandEvent {
    private static final long serialVersionUID = -3518352565764067471L;
    private final Object actionId;
    private final Source source;

    /**
     * @param actionId
     * @param source
     */
    public ComponentIdCommandEvent(Object actionId, Source source) {
        super();
        this.actionId = actionId;
        this.source = source;
    }

    /**
     * @param actionId
     * @param uiID
     * @param compId
     */
    public ComponentIdCommandEvent(Object actionId, Object uiID, String compId) {
        this(actionId, new Source(uiID, compId));
    }

    @Override
    public Object getActionId() {
        return actionId;
    }

    @Override
    public Source getSource() {
        return source;
    }

    /**
     * Source for event
     * 
     * @author kitko
     *
     */
    public static final class Source implements Serializable {
        private static final long serialVersionUID = 1555130785760124096L;
        private final Object uiID;
        private final String compId;

        /**
         * @param uiID
         * @param compId
         */
        public Source(Object uiID, String compId) {
            super();
            this.uiID = uiID;
            this.compId = compId;
        }

        /**
         * @return the uiID
         */
        public Object getUiID() {
            return uiID;
        }

        /**
         * @return the comId
         */
        public String getCompId() {
            return compId;
        }

    }

}
