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
package org.vpda.common.context;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Locale;
import java.util.Objects;

import org.vpda.internal.common.util.Assert;

public final class TenementalContext implements Serializable {
    private static final long serialVersionUID = 424458570928025849L;
    private final Locale locale;
    private final ApplContext applContext;
    private final DateContext dateContext;

    public TenementalContext(ApplContext applContext, Locale locale, DateContext dateContext) {
        this.applContext = Assert.isNotNullArgument(applContext, "applContext");
        this.locale = Assert.isNotNullArgument(locale, "locale");
        this.dateContext = Assert.isNotNullArgument(dateContext, "dateContext");
    }

    public static TenementalContext create(ApplContext applContext) {
        return new TenementalContext(applContext, new Locale("en", "US"), new DateContext.DateContextBuilder().setYear(LocalDate.now().getYear()).build());
    }

    public static TenementalContext create(ApplContext applContext, Locale locale) {
        return new TenementalContext(applContext, locale, new DateContext.DateContextBuilder().setYear(LocalDate.now().getYear()).build());
    }

    public static TenementalContext create(ApplContext applContext, Locale locale, DateContext dateContext) {
        return new TenementalContext(applContext, locale, dateContext);
    }

    public Locale getLocale() {
        return locale;
    }

    public ApplContext getApplContext() {
        return applContext;
    }

    public DateContext getDateContext() {
        return dateContext;
    }

    @Override
    public int hashCode() {
        return Objects.hash(applContext, dateContext, locale);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        TenementalContext other = (TenementalContext) obj;
        return Objects.equals(applContext, other.applContext) && Objects.equals(dateContext, other.dateContext) && Objects.equals(locale, other.locale);
    }

    public TenementalContext createWithLocale(Locale locale) {
        return new TenementalContext(applContext, locale, dateContext);
    }
}