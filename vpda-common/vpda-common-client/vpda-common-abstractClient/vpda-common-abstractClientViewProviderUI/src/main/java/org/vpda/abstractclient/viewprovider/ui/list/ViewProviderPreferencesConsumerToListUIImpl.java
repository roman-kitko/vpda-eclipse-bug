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
package org.vpda.abstractclient.viewprovider.ui.list;

import java.io.Serializable;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

import org.vpda.clientserver.viewprovider.list.ListViewProviderInfo;
import org.vpda.clientserver.viewprovider.preferences.ListViewProviderPreferences;
import org.vpda.clientserver.viewprovider.preferences.ListViewProviderPreferencesService;
import org.vpda.clientserver.viewprovider.preferences.ViewProviderPreferencesConsumer;
import org.vpda.internal.common.util.Assert;

/**
 * Adapter from {@link ListViewProviderUI} into
 * ViewProviderPreferencesConsumer<ListViewProviderInfo,
 * ListViewProviderPreferences>
 * 
 * @author kitko
 *
 */
public final class ViewProviderPreferencesConsumerToListUIImpl implements ViewProviderPreferencesConsumer<ListViewProviderInfo, ListViewProviderPreferences>, Serializable {
    private static final long serialVersionUID = 4516512994486763325L;
    private final ListViewProviderUI ui;

    /**
     * Creates ViewProviderPreferencesConsumerToListUIImpl with
     * {@link ListViewProviderUI}
     * 
     * @param ui
     */
    public ViewProviderPreferencesConsumerToListUIImpl(ListViewProviderUI ui) {
        this.ui = Assert.isNotNullArgument(ui, "ui");
    }

    @Override
    public ListViewProviderInfo getViewProviderInfo() {
        return ui.getViewProviderInfo();
    }

    @Override
    public ListViewProviderPreferences getCurrentPreferences() {
        return ui.getViewProviderPreferences();
    }

    @Override
    public void applyCurrentPreferences(ListViewProviderPreferences preferences) {
        ui.applyViewProviderPreferences(preferences);
        if (!preferences.isTemporal()) {
            ListViewProviderPreferencesService service = createService();
            service.setLastUsedPreferencesForCurrentUser(getViewProviderInfo().getViewProviderID().getDef(), preferences.getUuid());
        }
    }

    @Override
    public ListViewProviderPreferences storePreferences(ListViewProviderPreferences preferences) {
        ListViewProviderPreferencesService service = createService();
        boolean createCopy = false;
        if (preferences.isTemporal()) {
            String name = DateTimeFormatter.ofPattern("yyyyMMdd-HH:mm:ss").withZone(ZoneId.systemDefault()).format(Instant.now());
            preferences = preferences.createBuilderWithSameValues().setName(name).setTemporal(false).build();
            createCopy = true;
        }
        ListViewProviderPreferences pref = service.storeSettingsForCurrentUser(preferences);
        if (createCopy) {
            String copyName = pref.getName() + "-copy";
            ListViewProviderPreferences copy = pref.createBuilderWithSameValues().setName(copyName).setUuid(UUID.randomUUID()).build();
            service.storeSettingsForCurrentUser(copy);
        }
        return pref;
    }

    @Override
    public List<ListViewProviderPreferences> findAvailablePreferencesForCurrentUser() {
        ListViewProviderPreferencesService service = createService();
        List<ListViewProviderPreferences> findAvailablePreferencesForCurrentUser = service.findAvailablePreferencesForCurrentUser(ui.getViewProviderID().getDef());
        return findAvailablePreferencesForCurrentUser;
    }

    private ListViewProviderPreferencesService createService() {
        return ui.getClient().getServiceRegistry().getService(ListViewProviderPreferencesService.class);
    }

}
