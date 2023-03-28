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
 * Storage helper using {@link XStream}
 * 
 * @author kitko
 *
 */
public final class XStreamLoginProfilesStorageHelper {
    private final XStream xStream;

    /**
     * Creates helper
     */
    public XStreamLoginProfilesStorageHelper() {
        this(new XStream());
    }

    /**
     * Creates helper
     * 
     * @param xStream
     */
    public XStreamLoginProfilesStorageHelper(XStream xStream) {
        this.xStream = xStream;
        this.xStream.allowTypes(new Class[] { org.vpda.abstractclient.core.profile.LoginProfilesStorage.LoginProfilesStorageValue.class });
        this.xStream.allowTypeHierarchy(AbstractLoginProfile.class);
    }

    /**
     * Loads value from stream
     * 
     * @param stream
     * @return LoginProfilesStorageValue
     * @throws IOException
     */
    public LoginProfilesStorage.LoginProfilesStorageValue loadFromStream(InputStream stream) throws IOException {
        LoginProfilesStorage.LoginProfilesStorageValue value = (LoginProfilesStorage.LoginProfilesStorageValue) xStream.fromXML(stream);
        return value;
    }

    /**
     * Store value to stream
     * 
     * @param value
     * @param out
     */
    public void storeToStream(LoginProfilesStorage.LoginProfilesStorageValue value, OutputStream out) {
        xStream.toXML(value, out);
    }

}
