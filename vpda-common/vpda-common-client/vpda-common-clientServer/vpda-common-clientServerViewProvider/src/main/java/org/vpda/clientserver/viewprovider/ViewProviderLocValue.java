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
package org.vpda.clientserver.viewprovider;

import java.io.Serializable;

import org.vpda.common.annotations.Immutable;
import org.vpda.common.comps.loc.AbstractCompLocValue;

/**
 * Data holder for {@link ViewProvider} localization data
 * 
 * @author kitko
 *
 */
@Immutable
public final class ViewProviderLocValue extends AbstractCompLocValue implements Serializable {
    private static final long serialVersionUID = 8609501079196882641L;
    private final String description;
    private final String title;

    /**
     * @return description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @return title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title
     * @param tooltip
     * @param description
     */
    public ViewProviderLocValue(String title, String tooltip, String description) {
        super(tooltip);
        this.title = title;
        this.description = description;
    }

}
