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
package org.vpda.internal.common.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;

import org.junit.jupiter.api.Test;

/** Test */
public class OrderedMapTest {

    /**
     * Test
     * 
     * @throws IOException
     * @throws ClassNotFoundException
     */
    @Test
    public void testSerialize() throws ClassNotFoundException, IOException {
        OrderedMap<String, String> map = new OrderedMap<>();
        map.put("k1", "v1");
        OrderedMap<String, String> map2 = JavaSerializationUtil.copyObjectUsingSerialization(map);
        assertEquals("v1", map2.get("k1"));
    }
}
