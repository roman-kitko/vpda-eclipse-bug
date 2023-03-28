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

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.vpda.internal.common.util.JsonUtils;

import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * @author kitko
 *
 */
public class ViewProviderDefTest {

    /**
     * Test
     */
    @Test
    public void testViewProviderDef() {
        ViewProviderDef def = new ViewProviderDef.Builder().setKind(ViewProviderKind.GenericViewProvider).setImplClassName(ViewProvider.class.getName()).build();
        assertEquals(ViewProviderKind.GenericViewProvider, def.getKind());
        assertEquals(ViewProvider.class.getName(), def.getImplClassName());
    }

    /**
     * test json
     * 
     * @throws IOException
     */
    @Test
    public void testJSON() throws IOException {
        ViewProviderDef def = new ViewProviderDef.Builder().setKind(ViewProviderKind.GenericViewProvider).setImplClassName(ViewProvider.class.getName()).setCode("myCode").setSpiClassName("spiClass")
                .build();
        String jsonString = JsonUtils.toJsonString(def);
        assertEquals("{\"kind\":\"GenericViewProvider\",\"code\":\"myCode\",\"implClassName\":\"org.vpda.clientserver.viewprovider.ViewProvider\",\"spiClassName\":\"spiClass\"}", jsonString);
        ViewProviderDef def1 = JsonUtils.fromJsonString(jsonString, ViewProviderDef.class);
        assertEquals(def, def1);
        ObjectNode json = JsonUtils.toJson(def);
        ViewProviderDef def2 = JsonUtils.fromJson(json, ViewProviderDef.class);
        assertEquals(def, def2);
    }
}
