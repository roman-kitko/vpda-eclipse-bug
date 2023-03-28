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
package org.vpda.common.service.resources;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * This resolver for resolving will return passed url
 * 
 * @author kitko
 *
 */
public final class SimpleURLInputStreamResolver implements InputStreamResolver {

    private static final long serialVersionUID = -7259461121098207021L;
    private final URL url;

    @Override
    public URL resolveKey() {
        return url;
    }

    /**
     * @param url
     */
    public SimpleURLInputStreamResolver(URL url) {
        super();
        if (url == null) {
            throw new IllegalArgumentException("URL argument cannot be null");
        }
        this.url = url;
    }

    @Override
    public InputStream resolveStream() throws IOException {
        return url.openStream();
    }

}
