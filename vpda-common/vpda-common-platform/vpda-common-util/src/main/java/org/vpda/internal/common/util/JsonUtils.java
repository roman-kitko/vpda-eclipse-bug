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

import java.io.IOException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * Json utils
 * 
 * @author kitko
 *
 */
public final class JsonUtils {
    private static final ObjectMapper jsonMapper = new ObjectMapper();

    static {
        jsonMapper.enableDefaultTyping();
    }

    private JsonUtils() {
    }

    /**
     * @return ObjectMapper
     */
    public static ObjectMapper getJacksonMapper() {
        return jsonMapper;
    }

    /**
     * @param o
     * @return json node
     */
    public static ObjectNode toJson(Object o) {
        ObjectNode node = jsonMapper.valueToTree(o);
        return node;
    }

    /**
     * @param o
     * @return json string
     * @throws JsonProcessingException
     */
    public static String toJsonString(Object o) throws JsonProcessingException {
        return jsonMapper.writeValueAsString(o);
    }

    /**
     * Creates type from json node
     * 
     * @param node
     * @param type
     * @return ViewProviderDef
     * @throws JsonProcessingException
     */
    public static <T> T fromJson(ObjectNode node, Class<T> type) throws JsonProcessingException {
        return jsonMapper.treeToValue(node, type);
    }

    /**
     * Creates ListViewProviderSettings from json string
     * 
     * @param jsonString
     * @param type
     * @return ViewProviderDef
     * @throws IOException
     */
    public static <T> T fromJsonString(String jsonString, Class<T> type) throws IOException {
        return jsonMapper.readValue(jsonString, type);
    }

}
