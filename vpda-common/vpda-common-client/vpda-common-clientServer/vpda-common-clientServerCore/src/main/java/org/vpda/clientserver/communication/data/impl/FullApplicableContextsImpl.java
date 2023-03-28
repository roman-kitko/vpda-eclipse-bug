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
package org.vpda.clientserver.communication.data.impl;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.vpda.clientserver.communication.data.AbstractExtendedContext;
import org.vpda.clientserver.communication.data.ApplicableContexts;
import org.vpda.clientserver.communication.data.BaseApplicableContexts;
import org.vpda.clientserver.communication.data.FullApplicableContexts;
import org.vpda.common.context.ApplContext;
import org.vpda.common.context.DateContext;

/** All applicable contexts. Holds all contexts user can choose to login */
public final class FullApplicableContextsImpl extends AbstractExtendedContext implements Serializable, FullApplicableContexts {
    private static final long serialVersionUID = 9163832449424894872L;

    private final Map<Long, List<Locale>> localesMap;
    private final Locale lastUsedLocale;

    private final Map<Long, List<DateContext>> dateContextsMap;
    private final DateContext lastUsedDateContext;

    /**
     * Creates all contexts with last used one
     * 
     * @param contexts
     * @param lastContextIndex
     * @param localesMap
     * @param lastUsedLocale
     */
    public FullApplicableContextsImpl(List<ApplContext> contexts, int lastContextIndex, Map<Long, List<Locale>> localesMap, Locale lastUsedLocale, Map<Long, List<DateContext>> dateContextsMap,
            DateContext lastUsedDateContext) {
        super(new BaseApplicableContexts(contexts, lastContextIndex));
        this.localesMap = new HashMap<>(localesMap);
        this.lastUsedLocale = lastUsedLocale;
        this.dateContextsMap = new HashMap<>(dateContextsMap);
        this.lastUsedDateContext = lastUsedDateContext;
    }

    /**
     * @param baseCtx
     * @param localesMap
     */
    public FullApplicableContextsImpl(ApplicableContexts baseCtx, Map<Long, List<Locale>> localesMap, Map<Long, List<DateContext>> dateContextsMap) {
        this(baseCtx, localesMap, null, dateContextsMap, null);
    }

    /**
     * @param baseCtx
     * @param localesMap
     * @param lastUsedLocale
     */
    public FullApplicableContextsImpl(ApplicableContexts baseCtx, Map<Long, List<Locale>> localesMap, Locale lastUsedLocale, Map<Long, List<DateContext>> dateContextsMap,
            DateContext lastUsedDateContext) {
        super(baseCtx);
        this.localesMap = new HashMap<>(localesMap);
        this.lastUsedLocale = lastUsedLocale;
        this.dateContextsMap = new HashMap<>(dateContextsMap);
        this.lastUsedDateContext = lastUsedDateContext;
    }

    @Override
    public List<Locale> getLocalesForContextId(long id) {
        List<Locale> locales = localesMap.get(id);
        return locales != null ? Collections.unmodifiableList(locales) : Collections.emptyList();
    }

    @Override
    public List<Locale> getLocalesForContext(ApplContext ctx) {
        return getLocalesForContextId(ctx.getId());
    }

    @Override
    public Locale getLastLocale() {
        return lastUsedLocale;
    }

    @Override
    public List<DateContext> getDateContextsForApplContextId(long id) {
        List<DateContext> list = dateContextsMap.get(id);
        return list != null ? Collections.unmodifiableList(list) : Collections.emptyList();
    }

    @Override
    public DateContext getLastDateContext() {
        return lastUsedDateContext;
    }

    @Override
    public List<DateContext> getDateContextsForApplContext(ApplContext ctx) {
        return getDateContextsForApplContextId(ctx.getId());
    }
}