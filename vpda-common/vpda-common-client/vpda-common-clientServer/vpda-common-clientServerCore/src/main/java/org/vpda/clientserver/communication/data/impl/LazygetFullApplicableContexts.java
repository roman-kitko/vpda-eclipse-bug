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

import java.util.Collections;
import java.util.List;
import java.util.Locale;

import org.vpda.clientserver.communication.data.ApplicableContexts;
import org.vpda.clientserver.communication.data.FullApplicableContexts;
import org.vpda.clientserver.communication.data.LoginInfo;
import org.vpda.clientserver.communication.services.LoginException;
import org.vpda.clientserver.communication.services.LoginServer;
import org.vpda.common.context.ApplContext;
import org.vpda.common.context.DateContext;
import org.vpda.common.util.exceptions.VPDARuntimeException;

/**
 * {@link FullApplicableContexts} that resolves locales dynamically from
 * {@link LoginServer}
 * 
 * @author kitko
 *
 */
public final class LazygetFullApplicableContexts implements FullApplicableContexts {
    private final LoginServer loginServer;
    private final LoginInfo loginInfo;
    private ApplicableContexts resolvedCtx;

    /**
     * @param loginServer
     * @param loginInfo
     */
    public LazygetFullApplicableContexts(LoginServer loginServer, LoginInfo loginInfo) {
        this(loginServer, loginInfo, null);
    }

    /**
     * @param loginServer
     * @param loginInfo
     * @param baseApplicableContexts
     */
    public LazygetFullApplicableContexts(LoginServer loginServer, LoginInfo loginInfo, ApplicableContexts baseApplicableContexts) {
        this.loginServer = loginServer;
        this.loginInfo = loginInfo;
        this.resolvedCtx = baseApplicableContexts;
    }

    @Override
    public List<ApplContext> getContexts() {
        return resolveCtx().getContexts();
    }

    private ApplicableContexts resolveCtx() {
        if (resolvedCtx != null) {
            return resolvedCtx;
        }
        try {
            resolvedCtx = loginServer.getBaseApplicableContexts(loginInfo);
            return resolvedCtx;
        }
        catch (LoginException e) {
            throw new VPDARuntimeException("Cannot get appl contexts", e);
        }

    }

    @Override
    public int getLastContextIndex() {
        return resolveCtx().getLastContextIndex();
    }

    @Override
    public boolean isEmpty() {
        return resolveCtx().isEmpty();
    }

    @Override
    public ApplContext getPreferedContext() {
        return resolveCtx().getPreferedContext();
    }

    @Override
    public ApplContext getContextById(long id) {
        return resolveCtx().getContextById(id);
    }

    @Override
    public List<Locale> getLocalesForContextId(long id) {
        ApplContext ctx = resolveCtx().getContextById(id);
        if (ctx == null) {
            return Collections.emptyList();
        }
        return getLocalesForContext(ctx);
    }

    @Override
    public List<Locale> getLocalesForContext(ApplContext ctx) {
        resolveCtx();
        try {
            return loginServer.getLocalesForContext(loginInfo, ctx);
        }
        catch (LoginException e) {
            throw new VPDARuntimeException("Cannot get locales", e);
        }
    }

    @Override
    public Locale getLastLocale() {
        return null;
    }

    @Override
    public List<DateContext> getDateContextsForApplContextId(long id) {
        ApplContext ctx = resolveCtx().getContextById(id);
        if (ctx == null) {
            return Collections.emptyList();
        }
        return getDateContextsForApplContext(ctx);
    }

    @Override
    public List<DateContext> getDateContextsForApplContext(ApplContext ctx) {
        resolveCtx();
        try {
            return loginServer.getDateContextsForContext(loginInfo, ctx);
        }
        catch (LoginException e) {
            throw new VPDARuntimeException("Cannot get date contexts", e);
        }
    }

    @Override
    public DateContext getLastDateContext() {
        return null;
    }

}
