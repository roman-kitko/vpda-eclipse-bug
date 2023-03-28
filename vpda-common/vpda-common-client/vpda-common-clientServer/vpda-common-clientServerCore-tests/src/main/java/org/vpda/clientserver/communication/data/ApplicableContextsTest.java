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
/**
 * 
 */
package org.vpda.clientserver.communication.data;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.vpda.common.context.ApplContext;
import org.vpda.common.context.ApplContextBuilder;
import org.vpda.common.context.ContextItem;
import org.vpda.common.context.ContextItemKey;
import org.vpda.common.context.DefaultContextItemTypes;
import org.vpda.common.context.Organization;

/**
 * @author kitko
 *
 */
public class ApplicableContextsTest {

    private List<ApplContext> createContexts() {
        org.vpda.common.context.System system = new org.vpda.common.context.System(1L, UUID.randomUUID(), "sys-prod", "System1", "Production");
        Organization org = new Organization(1L, UUID.randomUUID(), system, "org1", "MyOrg", "Org desc");
        ApplContext ctx1 = new ApplContextBuilder().setId(1L).addItem(new ContextItem<org.vpda.common.context.System>(new ContextItemKey(DefaultContextItemTypes.SYSTEM), system))
                .addItem(new ContextItem<Organization>(new ContextItemKey(DefaultContextItemTypes.ORGANIZATION), org)).build();
        ApplContext ctx2 = new ApplContextBuilder().setId(1l).setParent(ctx1).build();
        return Arrays.asList(ctx1, ctx2);

    }

    /**
     * Test method for
     * {@link org.vpda.clientserver.communication.data.BaseApplicableContexts#getContexts()}.
     */
    @Test
    public void testGetContexts() {
        List<ApplContext> contexts = createContexts();
        ApplicableContexts applContexts = new BaseApplicableContexts(contexts, 1);
        assertEquals(contexts, applContexts.getContexts());
    }

    /**
     * Test method for
     * {@link org.vpda.clientserver.communication.data.BaseApplicableContexts#isEmpty()}.
     */
    @Test
    public void testIsEmpty() {
        assertFalse(new BaseApplicableContexts(createContexts(), 1).isEmpty());
        assertTrue(new BaseApplicableContexts(Collections.<ApplContext>emptyList(), -1).isEmpty());
    }

    /**
     * Test method for
     * {@link org.vpda.clientserver.communication.data.BaseApplicableContexts#getPreferedContext()}.
     */
    @Test
    public void testGetPreferedContext() {
        List<ApplContext> contexts = createContexts();
        ApplicableContexts applContexts = new BaseApplicableContexts(contexts, 0);
        assertEquals(contexts.get(0), applContexts.getPreferedContext());
        applContexts = new BaseApplicableContexts(contexts, 1);
        assertEquals(contexts.get(1), applContexts.getPreferedContext());
        applContexts = new BaseApplicableContexts(contexts, -1);
        assertEquals(contexts.get(0), applContexts.getPreferedContext());
        applContexts = new BaseApplicableContexts(Collections.<ApplContext>emptyList(), -1);
        assertNull(applContexts.getPreferedContext());
    }

    /**
     * Test method for
     * {@link org.vpda.clientserver.communication.data.BaseApplicableContexts#getLastContextIndex()}.
     */
    @Test
    public void testGetLastContextIndex() {
        List<ApplContext> contexts = createContexts();
        ApplicableContexts applContexts = new BaseApplicableContexts(contexts, 0);
        assertEquals(0, applContexts.getLastContextIndex());
        applContexts = new BaseApplicableContexts(contexts, 1);
        assertEquals(1, applContexts.getLastContextIndex());
        applContexts = new BaseApplicableContexts(contexts, -1);
        assertEquals(-1, applContexts.getLastContextIndex());
        applContexts = new BaseApplicableContexts(Collections.<ApplContext>emptyList(), -1);
        assertEquals(-1, applContexts.getLastContextIndex());
        applContexts = new BaseApplicableContexts(Collections.<ApplContext>emptyList());
        assertEquals(-1, applContexts.getLastContextIndex());
        applContexts = new BaseApplicableContexts(contexts, -10);
        assertEquals(-1, applContexts.getLastContextIndex());

        try {
            new BaseApplicableContexts(contexts, 2);
            fail();
        }
        catch (IllegalArgumentException e) {
        }
        try {
            new BaseApplicableContexts(Collections.<ApplContext>emptyList(), 0);
            fail();
        }
        catch (IllegalArgumentException e) {
        }

    }

}
