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
package org.vpda.clientserver.communication;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.net.URL;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.picocontainer.DefaultPicoContainer;
import org.picocontainer.MutablePicoContainer;
import org.picocontainer.script.xml.XMLContainerBuilder;

/** */
public class CommunicationIdTest {

    /** Test create CommunicationId from xml container */
    @SuppressWarnings("unchecked")
    @Test
    public void testCreateCommunicationId() {
        URL url = getClass().getResource("communicationId.xml");
        XMLContainerBuilder builder = new XMLContainerBuilder(url, getClass().getClassLoader());
        MutablePicoContainer container = new DefaultPicoContainer();
        builder.populateContainer(container);
        CommunicationId commId = (CommunicationId) container.getComponent("myCommunicationId");
        assertNotNull(commId);
        List<CommunicationId> ids = (List<CommunicationId>) container.getComponent("myCommunicationIds");
        assertNotNull(ids);
        assertTrue(!ids.isEmpty());

    }

}
