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

import org.vpda.clientserver.viewprovider.ViewProviderException;
import org.vpda.clientserver.viewprovider.autocomplete.AutoCompleteViewProviderInitResponse;
import org.vpda.clientserver.viewprovider.autocomplete.AutoCompletionResults;
import org.vpda.clientserver.viewprovider.stateless.InitStatelessRequest;
import org.vpda.clientserver.viewprovider.stateless.StatelessViewProviderServices;

/**
 * Stateless for AutoComplete
 * 
 * @author kitko
 *
 */
public interface StatelessAutoCompleteViewProviderServices extends StatelessViewProviderServices {

    /**
     * @param request
     * @return results
     * @throws ViewProviderException
     */
    public AutoCompletionResults completeQuery(CompleteQueryRequest request) throws ViewProviderException;

    /**
     * @param request
     * @return AutoCompleteInitResponseResults
     * @throws ViewProviderException
     */
    public AutoCompleteInitResponseResults initAndCompleteQuery(InitAndCompleteQueryRequest request) throws ViewProviderException;

    @Override
    public AutoCompleteViewProviderInitResponse getProviderInfo(InitStatelessRequest request) throws ViewProviderException;

}
