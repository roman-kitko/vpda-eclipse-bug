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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.junit.jupiter.api.Test;
import org.vpda.common.service.resources.FileInputStreamResolver;

/**
 * @author kitko
 *
 */
public class FileInputStreamResolverTest {

    /**
     * Test method for
     * {@link org.vpda.common.service.resources.FileInputStreamResolver#resolveKey()}
     * 
     * @throws IOException
     */
    @Test
    public void testResolveKey() throws IOException {
        FileInputStreamResolver testee = createTestee();
        Object key1 = testee.resolveKey();
        Object key2 = testee.resolveKey();
        assertNotNull(key1);
        assertNotNull(key2);
        assertEquals(key1, key2);
    }

    /**
     * Test method for
     * {@link org.vpda.common.service.resources.FileInputStreamResolver#getFile()}.
     * 
     * @throws IOException
     */
    @Test
    public void testGetFile() throws IOException {
        assertNotNull(createTestee().getFile());
    }

    /**
     * Test method for
     * {@link org.vpda.common.service.resources.FileInputStreamResolver#resolveStream()}
     * 
     * @throws IOException
     */
    @Test
    public void testResolveStream() throws IOException {
        FileInputStreamResolver testee = createTestee();
        InputStream stream = testee.resolveStream();
        assertNotNull(stream);
        stream.close();
    }

    private FileInputStreamResolver createTestee() throws IOException {
        return new FileInputStreamResolver(File.createTempFile("test", "txt"));
    }

}
