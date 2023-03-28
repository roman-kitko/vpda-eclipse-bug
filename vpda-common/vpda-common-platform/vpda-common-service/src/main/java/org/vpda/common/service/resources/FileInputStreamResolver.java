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

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;

/**
 * This resolver will point to concrete file
 * 
 * @author kitko
 *
 */
public final class FileInputStreamResolver implements InputStreamResolver {
    private static final long serialVersionUID = -2011142324386952295L;
    private final File file;

    @Override
    public Object resolveKey() {
        return file;
    }

    /**
     * @return the file
     */
    public File getFile() {
        return file;
    }

    /**
     * @param file
     */
    public FileInputStreamResolver(File file) {
        super();
        this.file = file;
    }

    @Override
    public InputStream resolveStream() throws IOException {
        try {
            return file.toURI().toURL().openStream();
        }
        catch (MalformedURLException e) {
            throw new IOException(e);
        }
    }

}
