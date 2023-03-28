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
package org.vpda.common.comps.loc;

import java.io.Serializable;

import org.vpda.common.service.localization.LocValueBuilder;
import org.vpda.common.service.localization.LocValueBuilderFactory;

/**
 * Localization data builder for push buttons
 * 
 * @author kitko
 *
 */
public final class PushButtonLocValueBuilder extends AbstractButtonLocValueBuilder<PushButtonLocValue> implements LocValueBuilder<PushButtonLocValue> {
    private static final long serialVersionUID = 3336587960353312671L;
    private static PushButtonLocValueBuilder instance = new PushButtonLocValueBuilder();

    /**
     * Singleton pattern instance creator
     * 
     * @return instance
     */
    protected static PushButtonLocValueBuilder getInstance() {
        return instance;
    }

    /**
     * Default constructor
     */
    protected PushButtonLocValueBuilder() {
    }

    @Override
    protected PushButtonLocValue.Builder createButtonBuilder() {
        return new PushButtonLocValue.Builder();
    }

    /**
     * Factory
     * 
     * @author kitko
     *
     */
    public static final class PushButtonLocValueBuilderFactory implements LocValueBuilderFactory.OneLocValueBuilderFactory<PushButtonLocValue, PushButtonLocValueBuilder>, Serializable {
        private static final long serialVersionUID = 8963676912306713686L;

        @Override
        public PushButtonLocValueBuilder createBuilder() {
            return getInstance();
        }

        @Override
        public Class<PushButtonLocValueBuilder> getBuilderClass() {
            return PushButtonLocValueBuilder.class;
        }

        @Override
        public Class<PushButtonLocValue> getLocValueClass() {
            return PushButtonLocValue.class;
        }
    }

    @Override
    public Class<PushButtonLocValue> getLocValueClass() {
        return PushButtonLocValue.class;
    }

}
