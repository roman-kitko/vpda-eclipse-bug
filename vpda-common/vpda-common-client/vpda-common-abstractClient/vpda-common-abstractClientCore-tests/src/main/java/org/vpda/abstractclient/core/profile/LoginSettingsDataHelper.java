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
package org.vpda.abstractclient.core.profile;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.thoughtworks.xstream.XStream;

/**
 * Helper for storing data
 * 
 * @author kitko
 *
 */
public class LoginSettingsDataHelper {

    /**
     * Store to stream using {@link XStream}
     * 
     * @param loginSettingsData
     * @param stream
     * @throws IOException
     */
    public void storeToStream(AbstractLoginProfile loginSettingsData, OutputStream stream) throws IOException {
        XStream xStream = new XStream();
        xStream.allowTypes(new Class[] { org.vpda.abstractclient.core.profile.LoginProfilesStorage.LoginProfilesStorageValue.class });
        xStream.allowTypeHierarchy(AbstractLoginProfile.class);
        xStream.toXML(loginSettingsData, stream);
    }

    /**
     * Load from stream using {@link XStream}
     * 
     * @param stream
     * @return LoginSettingsData
     * @throws IOException
     */
    public AbstractLoginProfile loadFromStream(InputStream stream) throws IOException {
        XStream xStream = new XStream();
        xStream.allowTypes(new Class[] { org.vpda.abstractclient.core.profile.LoginProfilesStorage.LoginProfilesStorageValue.class });
        xStream.allowTypeHierarchy(AbstractLoginProfile.class);
        AbstractLoginProfile loginSettingsData = (AbstractLoginProfile) xStream.fromXML(stream);
        return loginSettingsData;
    }
}