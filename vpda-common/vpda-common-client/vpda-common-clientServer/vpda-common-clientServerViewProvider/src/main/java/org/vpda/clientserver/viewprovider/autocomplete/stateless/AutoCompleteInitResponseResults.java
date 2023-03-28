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
package org.vpda.clientserver.viewprovider.autocomplete.stateless;

import java.io.Serializable;

import org.vpda.clientserver.viewprovider.autocomplete.AutoCompleteViewProviderInitResponse;
import org.vpda.clientserver.viewprovider.autocomplete.AutoCompletionResults;
import org.vpda.internal.common.util.Assert;

/**
 * Init Response with completion results
 * 
 * @author kitko
 *
 */
public final class AutoCompleteInitResponseResults implements Serializable {
    private static final long serialVersionUID = -4469274144550502438L;
    private final AutoCompleteViewProviderInitResponse initResponse;
    private final AutoCompletionResults results;

    /**
     * @param initResponse
     * @param results
     */
    public AutoCompleteInitResponseResults(AutoCompleteViewProviderInitResponse initResponse, AutoCompletionResults results) {
        this.initResponse = Assert.isNotNullArgument(initResponse, "initResponse");
        this.results = Assert.isNotNullArgument(results, "results");
    }

    /**
     * @return the initResponse
     */
    public AutoCompleteViewProviderInitResponse getInitResponse() {
        return initResponse;
    }

    /**
     * @return the results
     */
    public AutoCompletionResults getResults() {
        return results;
    }

}
