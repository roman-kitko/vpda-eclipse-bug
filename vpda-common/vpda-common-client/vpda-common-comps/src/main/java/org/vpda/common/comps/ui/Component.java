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
package org.vpda.common.comps.ui;

import java.io.Serializable;
import java.util.Collection;

import org.vpda.common.comps.MemberListenerSupportWithFireForSingleMember;
import org.vpda.common.comps.loc.AbstractCompLocValue;
import org.vpda.common.context.localization.LocKey;
import org.vpda.common.service.localization.Localizable;
import org.vpda.common.types.EnabledObject;
import org.vpda.common.util.Visitable;

/**
 * Abstract component
 * 
 * @author kitko
 *
 * @param <V>
 * @param <T>
 */
public interface Component<V, T extends AbstractCompLocValue> extends Serializable, Localizable, Visitable<AbstractComponent<V, T>>, ComponentMember, EnabledObject {

    /**
     * @return value
     */
    V getValue();

    /**
     * Sets value
     * 
     * @param value
     */
    void setValue(V value);

    /**
     * @return true if visible
     */
    boolean isVisible();

    /**
     * Sets visible
     * 
     * @param isVisible
     */
    void setVisible(boolean isVisible);

    /**
     * @return tooltip
     */
    String getTooltip();

    /**
     * Gets ui caption
     * 
     * @return caption that most identifies the component for user
     */
    String getCaption();

    /**
     * Sets caption
     * 
     * @param caption
     */
    void setCaption(String caption);

    /**
     * Sets tooltip
     * 
     * @param tooltip
     */
    void setTooltip(String tooltip);

    /**
     * @return localization value class
     */
    Class<T> getLocValueClass();

    /**
     * @return localization key
     */
    LocKey getLocKey();

    /**
     * @return id of container component belongs to
     */
    String getContainerId();

    /**
     * @return id of group
     */
    String getGroupId();

    /**
     * Sets id of group
     * 
     * @param groupId
     */
    void setGroupId(String groupId);

    /**
     * Sets local id
     * 
     * @param localId
     */
    void setLocalId(String localId);

    /**
     * Gets custom property
     * 
     * @param key
     * @return custom property
     */
    Object getProperty(String key);

    /**
     * Gets properties keys
     * 
     * @return properties keys
     */
    Collection<String> getPropertiesKeys();

    /**
     * Sets custom property
     * 
     * @param key
     * @param value
     */
    void setProperty(String key, Object value);

    /**
     * @return the horizontalAlignment
     */
    HorizontalAlignment getHorizontalAlignment();

    /**
     * @param horizontalAlignment the horizontalAlignment to set
     */
    void setHorizontalAlignment(HorizontalAlignment horizontalAlignment);

    /**
     * @return the verticalAlignment
     */
    VerticalAlignment getVerticalAlignment();

    /**
     * @param verticalAlignment the verticalAlignment to set
     */
    void setVerticalAlignment(VerticalAlignment verticalAlignment);

    /**
     * @return the localizer
     */
    ComponentLocalizer getLocalizer();

    /**
     * @param localizer the localizer to set
     */
    void setLocalizer(ComponentLocalizer localizer);

    /**
     * @return the permissionId
     */
    String getPermissionId();

    /**
     * @param permissionId the permissionId to set
     */
    void setPermissionId(String permissionId);

    /**
     * @param key
     */
    public void setLocKey(LocKey key);

    @Override
    public MemberListenerSupportWithFireForSingleMember getMemberListenerSupport();

    /**
     * @param object
     */
    void setContainerId(String object);

    /**
     * @param comp1
     */
    public void assignValues(Component<V, T> comp1);

    /**
     * @return clonse
     */
    public Component clone();

}