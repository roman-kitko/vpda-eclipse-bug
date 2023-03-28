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
package org.vpda.abstractclient.viewprovider.ui;

import java.io.Serializable;

import org.vpda.abstractclient.core.ui.FrameInfo;

/**
 * Implementation of {@link DefaultViewProviderUIInfo}
 * 
 * @author kitko
 *
 */
public final class DefaultViewProviderUIInfoImpl implements DefaultViewProviderUIInfo, Serializable {
    private static final long serialVersionUID = -7195018909744662864L;
    private final FrameOpenKind frameOpenKind;
    private final FrameInfo frameInfo;

    @Override
    public FrameOpenKind getFrameOpenKind() {
        return frameOpenKind;
    }

    /**
     * Creates ViewProviderUIInfoImpl
     * 
     * @param frameOpenKind
     */
    public DefaultViewProviderUIInfoImpl(FrameOpenKind frameOpenKind) {
        this.frameOpenKind = frameOpenKind;
        this.frameInfo = null;
    }

    /**
     * Creates ViewProviderUIInfoImpl
     * 
     * @param frameOpenKind
     * @param frameInfo
     */
    public DefaultViewProviderUIInfoImpl(FrameOpenKind frameOpenKind, FrameInfo frameInfo) {
        this.frameOpenKind = frameOpenKind;
        this.frameInfo = frameInfo;
    }

    @Override
    public FrameInfo getFrameInfo() {
        return frameInfo;
    }

    @Override
    public String toString() {
        return "DefaultViewProviderUIInfoImpl [frameOpenKind=" + frameOpenKind + ", frameInfo=" + frameInfo + "]";
    }

}
