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

import java.io.Serializable;
import java.util.List;

import org.vpda.common.context.ApplContext;
import org.vpda.internal.common.util.Assert;

/**
 * Abstraction of context that extends base one
 * 
 * @author kitko
 *
 */
public abstract class AbstractExtendedContext implements Serializable, ApplicableContexts {
    private static final long serialVersionUID = -7416099928458977100L;

    /** Base ctx */
    protected final ApplicableContexts baseCtx;

    /**
     * @param baseCtx
     */
    protected AbstractExtendedContext(ApplicableContexts baseCtx) {
        this.baseCtx = Assert.isNotNullArgument(baseCtx, "baseCtx");
    }

    @Override
    public List<ApplContext> getContexts() {
        return baseCtx.getContexts();
    }

    @Override
    public int getLastContextIndex() {
        return baseCtx.getLastContextIndex();
    }

    @Override
    public boolean isEmpty() {
        return baseCtx.isEmpty();
    }

    @Override
    public ApplContext getPreferedContext() {
        return baseCtx.getPreferedContext();
    }

    @Override
    public ApplContext getContextById(long id) {
        return baseCtx.getContextById(id);
    }

}
