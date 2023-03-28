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
package org.vpda.clientserver.communication.compression;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.InflaterInputStream;

import org.apache.commons.io.IOUtils;

/**
 * Compression socket
 * 
 * @author kitko
 *
 */
class CompressionSocket extends AbstractDelegateSocket {
    private InputStream in;
    private OutputStream out;

    /**
     * Create socket
     * 
     * @param delegate
     */
    public CompressionSocket(Socket delegate) {
        super(delegate);
    }

    @Override
    public InputStream getInputStream() throws IOException {
        if (in == null) {
            in = new InflaterInputStream(delegate.getInputStream());
        }
        return in;
    }

    @Override
    public OutputStream getOutputStream() throws IOException {
        if (out == null) {
            out = new DeflaterOutputStream(delegate.getOutputStream(), true);
        }
        return out;
    }

    @Override
    public void close() throws IOException {
        IOUtils.closeQuietly(in);
        in = null;
        IOUtils.closeQuietly(out);
        out = null;
        delegate.close();
    }
}
