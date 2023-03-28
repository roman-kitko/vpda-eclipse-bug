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

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;

import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.vpda.common.service.resources.InputStreamResolver;
import org.vpda.common.service.resources.LoadResourceRequest;
import org.vpda.common.service.resources.ResourceServiceImpl;
import org.vpda.common.service.resources.SimpleURLInputStreamResolver;

/**
 * @author kitko
 *
 */
public class ResourceServiceImplTest {

    /**
     * Test method for {@link ResourceServiceImpl#loadResource(LoadResourceRequest)}
     * with caching
     * 
     * @throws IOException
     */
    @Test
    public void testLoadResourceCached() throws IOException {
        ResourceServiceImpl resourceService = createTestee();
        InputStreamResolver inputStreamResolver = new SimpleURLInputStreamResolver(ResourceServiceImplTest.class.getResource(ResourceServiceImplTest.class.getSimpleName() + ".class"));
        LoadResourceRequest loadResourceRequest = new LoadResourceRequest(inputStreamResolver, true);
        byte[] bytes1 = resourceService.loadResource(loadResourceRequest);
        assertNotNull(bytes1);
        byte[] bytes2 = resourceService.loadResource(loadResourceRequest);
        assertNotNull(bytes2);
        assertSame(bytes1, bytes2);
        resourceService.clearData();
        bytes2 = resourceService.loadResource(loadResourceRequest);
        assertNotNull(bytes2);
        assertNotSame(bytes1, bytes2);
    }

    /**
     * Test method for {@link ResourceServiceImpl#loadResource(LoadResourceRequest)}
     * without caching
     * 
     * @throws IOException
     */
    @Test
    public void testLoadResourceUnCached() throws IOException {
        ResourceServiceImpl resourceService = createTestee();
        InputStreamResolver inputStreamResolver = new SimpleURLInputStreamResolver(ResourceServiceImplTest.class.getResource(ResourceServiceImplTest.class.getSimpleName() + ".class"));
        LoadResourceRequest loadResourceRequest = new LoadResourceRequest(inputStreamResolver, false);
        byte[] bytes1 = resourceService.loadResource(loadResourceRequest);
        assertNotNull(bytes1);
        byte[] bytes2 = resourceService.loadResource(loadResourceRequest);
        assertNotNull(bytes2);
        assertNotSame(bytes1, bytes2);
        resourceService.clearData();
        bytes2 = resourceService.loadResource(loadResourceRequest);
        assertNotNull(bytes2);
        assertNotSame(bytes1, bytes2);
    }

    private ResourceServiceImpl createTestee() {
        return new ResourceServiceImpl();
    }

}
