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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.vpda.common.context.ApplContext;

/** All applicable contexts. Holds all contexts user can choose to login */
public final class BaseApplicableContexts implements Serializable, ApplicableContexts {
    private static final long serialVersionUID = 9163832449424894872L;

    private final List<ApplContext> contexts;
    private final int lastContextIndex;

    /**
     * Creates all contexts with last used one
     * 
     * @param contexts
     * @param lastContextIndex
     */
    public BaseApplicableContexts(List<ApplContext> contexts, int lastContextIndex) {
        this.contexts = new ArrayList<ApplContext>(contexts);
        if (lastContextIndex >= contexts.size()) {
            throw new IllegalArgumentException("Last context index = " + lastContextIndex + " Size = " + contexts.size());
        }
        if (lastContextIndex < -1) {
            lastContextIndex = -1;
        }
        this.lastContextIndex = lastContextIndex;
    }

    /**
     * Creates contexts with -1 as last used one
     * 
     * @param contexts
     */
    public BaseApplicableContexts(List<ApplContext> contexts) {
        this(contexts, -1);
    }

    @Override
    public List<ApplContext> getContexts() {
        return Collections.unmodifiableList(contexts);
    }

    @Override
    public int getLastContextIndex() {
        return lastContextIndex;
    }

    @Override
    public boolean isEmpty() {
        return contexts.isEmpty();
    }

    @Override
    public ApplContext getPreferedContext() {
        if (isEmpty()) {
            return null;
        }
        if (lastContextIndex != -1) {
            return contexts.get(lastContextIndex);
        }
        return contexts.get(0);
    }

    @Override
    public ApplContext getContextById(long id) {
        for (ApplContext ctx : contexts) {
            if (ctx.getId() == id) {
                return ctx;
            }
        }
        return null;
    }

}
