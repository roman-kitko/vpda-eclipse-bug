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
import java.util.Locale;

import org.vpda.common.context.ApplContext;
import org.vpda.common.context.DateContext;

/**
 * @author kitko
 *
 */
public interface FullApplicableContexts extends ApplicableContexts {

    /**
     * @param id
     * @return locales for context id
     */
    List<Locale> getLocalesForContextId(long id);

    /**
     * @param ctx
     * @return locales for ctx
     */
    List<Locale> getLocalesForContext(ApplContext ctx);

    List<DateContext> getDateContextsForApplContextId(long id);

    List<DateContext> getDateContextsForApplContext(ApplContext ctx);

    /**
     * 
     * @return last used locale or null if not found
     */
    public Locale getLastLocale();

    public DateContext getLastDateContext();
}