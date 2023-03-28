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
package org.vpda.clientserver.communication.data;

import java.util.List;

import org.vpda.common.context.ApplContext;

/**
 * Applicable contexts for user
 * 
 * @author kitko
 *
 */
public interface ApplicableContexts {

    /**
     * @return the contexts
     */
    List<ApplContext> getContexts();

    /**
     * @return the lastContext index or -1 if no last context
     */
    int getLastContextIndex();

    /**
     * 
     * @return true if no contexts
     */
    boolean isEmpty();

    /**
     * @return preferred context
     */
    ApplContext getPreferedContext();

    /**
     * @param id
     * @return context by id
     */
    ApplContext getContextById(long id);

}