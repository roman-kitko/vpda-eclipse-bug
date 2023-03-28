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
package org.vpda.common.criteria;

import org.vpda.common.context.TenementalContext;
import org.vpda.common.context.localization.BasicLocalizationService;
import org.vpda.common.context.localization.LocKey;

/**
 * Enum of operators
 * 
 * @author kitko
 *
 */
public enum Operator implements Criterion {
    /** logical and operator */
    AND("and", 2),
    /** logical or operator */
    OR("or", 2),
    /** Equals operator */
    EQ("=", 2),
    /** Not equals */
    NOT_EQ("!=", 2),
    /** is null operator */
    IS_NULL("is null", 1),
    /** is not null operator */
    IS_NOT_NULL("is not null", 1),
    /** NOT operator */
    NOT("not", 1),
    /** Greater than operator */
    GT(">", 2),
    /** Greater or equal */
    GT_EQ(">=", 2),
    /** Less than operator */
    LT("<", 2),
    /** less or equal */
    LT_EQ("<=", 2),
    /** Starts with */
    STARTS_WITH("startWith", 2),
    /** End with */
    ENDS_WITH("endWith", 2),
    /** Contains */
    CONTAINS("contains", 2),
    /** In operator */
    IN("in", 2);

    Operator(String token, int argumentsCount) {
        this.token = token;
        this.argumentsCount = argumentsCount;
    }

    private String token;
    private int argumentsCount;

    /**
     * @return token
     */
    public String getToken() {
        return token;
    }

    /**
     * @return the argumentsCount
     */
    public int getArgumentsCount() {
        return argumentsCount;
    }

    @Override
    public String toString() {
        return token;
    }

    private static final String LOC_PATH = "common/criteriaOperators";

    /**
     * Localize operator name
     * 
     * @param localizationService
     * @param context
     * @return localized name
     */
    public String localize(BasicLocalizationService localizationService, TenementalContext context) {
        LocKey key = LocKey.pathAndKey(LOC_PATH, name());
        return localizationService.localizeString(key, context, name());
    }

}
