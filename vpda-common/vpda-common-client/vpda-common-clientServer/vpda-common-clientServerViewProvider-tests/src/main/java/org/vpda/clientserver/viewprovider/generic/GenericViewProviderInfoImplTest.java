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
package org.vpda.clientserver.viewprovider.generic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.vpda.clientserver.viewprovider.ViewProviderDef;
import org.vpda.clientserver.viewprovider.ViewProviderID;
import org.vpda.clientserver.viewprovider.ViewProviderKind;
import org.vpda.common.comps.ui.def.ComponentsGroupsDef;

/**
 * @author kitko
 *
 */
public class GenericViewProviderInfoImplTest {

    /**
     * Test method for
     * {@link org.vpda.clientserver.viewprovider.ViewProviderInfo#getViewProviderID()}.
     */
    @Test
    public void testGetViewProviderID() {
        GenericViewProviderInfo detailViewProviderInfo = createTestee();
        assertNotNull(detailViewProviderInfo.getViewProviderID());
        assertEquals(ViewProviderKind.GenericViewProvider, detailViewProviderInfo.getViewProviderID().getDef().getKind());
    }

    private GenericViewProviderInfo createTestee() {
        ViewProviderDef def = new ViewProviderDef.Builder().setKind(ViewProviderKind.GenericViewProvider).setImplClassName(GenericViewProvider.class.getName()).build();
        ViewProviderID viewProviderID = new ViewProviderID("0", def);
        GenericViewProviderInfo.GenericViewProviderInfoBuilder builder = new GenericViewProviderInfo.GenericViewProviderInfoBuilder();
        builder.setDetailCompsDef(new ComponentsGroupsDef());
        builder.setViewProviderID(viewProviderID);
        GenericViewProviderInfo detailViewProviderInfo = builder.build();
        return detailViewProviderInfo;
    }

}
