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
package org.vpda.abstractclient.viewprovider.ui.commands.generic;

import org.vpda.common.command.call.ReflexiveCall;
import org.vpda.common.comps.ui.def.ComponentsGroupsDef;

/**
 * Call that will fire fetch
 * 
 * @author kitko
 *
 */
public final class FireFetchCall extends ReflexiveCall {
    private static final long serialVersionUID = 4925618025609410038L;
    private final String compId;
    private final ComponentsGroupsDef compsDef;
    private final String fetchId;

    /**
     * Creates FireFetchCall
     * 
     * @param compId
     * @param compsDef
     * @param fetchId
     */
    public FireFetchCall(ComponentsGroupsDef compsDef, String compId, String fetchId) {
        super(DetailCallsConstants.FIRE_FETCH);
        this.compId = compId;
        this.compsDef = compsDef;
        this.fetchId = fetchId;
    }

    /**
     * @return the compId
     */
    public String getCompId() {
        return compId;
    }

    /**
     * @return the compsDef
     */
    public ComponentsGroupsDef getCompsDef() {
        return compsDef;
    }

    /**
     * @return the fetchId
     */
    public String getFetchId() {
        return fetchId;
    }

}
