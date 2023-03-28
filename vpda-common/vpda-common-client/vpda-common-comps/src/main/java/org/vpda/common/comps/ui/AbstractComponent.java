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
import java.text.MessageFormat;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.vpda.common.comps.MemberListenerSupportImpl;
import org.vpda.common.comps.MemberListenerSupportWithFireForSingleMember;
import org.vpda.common.comps.loc.AbstractCompLocValue;
import org.vpda.common.context.ApplContext;
import org.vpda.common.context.TenementalContext;
import org.vpda.common.context.localization.LocKey;
import org.vpda.common.service.localization.LocValue;
import org.vpda.common.service.localization.LocValueBuilder;
import org.vpda.common.service.localization.Localizable;
import org.vpda.common.service.localization.LocalizationService;
import org.vpda.common.types.EnabledObject;
import org.vpda.common.util.Visitable;
import org.vpda.common.util.Visitor;
import org.vpda.common.util.exceptions.VPDARuntimeException;
import org.vpda.internal.common.util.Assert;
import org.vpda.internal.common.util.StringUtil;

/**
 * Abstract component. It represents data,localization and basic UI of target
 * component
 * 
 * @author kitko
 * @param <V> - value
 * @param <T> - loc value
 *
 */
public abstract class AbstractComponent<V, T extends AbstractCompLocValue> implements Serializable, Localizable, Visitable<AbstractComponent<V, T>>, ComponentMember, EnabledObject, Component<V, T> {
    /** Id of component */
    protected String id;
    /** Local id */
    protected String localId;
    /** Id of container this component belongs to */
    protected String containerId;
    /** Main value of component */
    protected V value;
    /** Flag whether component is enabled */
    protected boolean enabled;
    /** Flag whether component is visible */
    protected boolean visible;
    /** Component tooltip */
    protected String tooltip;
    /** Localization key */
    protected LocKey locKey;
    /** Logical group */
    protected String groupId;
    /** Component properties */
    protected Map<String, Object> properties;
    /** Horizontal alignment */
    protected HorizontalAlignment horizontalAlignment;
    /** Vertical alignment */
    protected VerticalAlignment verticalAlignment;
    /** Concrete localizer */
    protected ComponentLocalizer localizer;
    /** Id of permission */
    protected transient String permissionId;
    private MemberListenerSupportImpl memberListenerSupport;

    private static final long serialVersionUID = -8746169859794422073L;

    /**
     * Creates component
     * 
     * @param localId
     * 
     */
    protected AbstractComponent(String localId) {
        this.localId = Assert.isNotNullArgument(localId, "localId");
        if (!ComponentUtils.isValidComponentLocalId(localId)) {
            throw new IllegalArgumentException("Invalid localId of component : " + localId);
        }
        this.id = localId;
        visible = true;
        enabled = true;
    }

    @Override
    public MemberListenerSupportWithFireForSingleMember getMemberListenerSupport() {
        if (memberListenerSupport == null) {
            memberListenerSupport = new MemberListenerSupportImpl(this);
        }
        return memberListenerSupport;
    }

    /**
     * Creates a component with set of id
     */
    protected AbstractComponent() {
        visible = true;
        enabled = true;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public V getValue() {
        return value;
    }

    @Override
    public void setValue(V value) {
        this.value = value;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    /**
     * @return true if component is empty
     */
    public boolean isEmpty() {
        return value == null;
    }

    /**
     * @return true if component is not empty
     */
    public boolean isNotEmpty() {
        return !isEmpty();
    }

    @Override
    public boolean isVisible() {
        return visible;
    }

    @Override
    public void setEnabled(boolean isEnabled) {
        this.enabled = isEnabled;
    }

    @Override
    public void setVisible(boolean isVisible) {
        this.visible = isVisible;
    }

    @Override
    public String getTooltip() {
        return tooltip;
    }

    @Override
    public String getCaption() {
        return null;
    }

    @Override
    public void setCaption(String caption) {
    }

    @Override
    public void setTooltip(String tooltip) {
        this.tooltip = tooltip;
    }

    @Override
    public boolean isLocalized() {
        return true;
    }

    @Override
    public void localize(LocalizationService localizationService, TenementalContext context) {
        if (localizer != null) {
            localizer.localize(this, localizationService, context);
        }
        else {
            DefaultLocalizer.getInstance().localize(this, localizationService, context);
        }
    }

    /**
     * Default localization for this component type
     * 
     * @param localizationService
     * @param context
     */
    protected final void defaultLocalize(LocalizationService localizationService, TenementalContext context) {
        if (locKey != null) {
            LocValueBuilder<T> builder = localizationService.getLocValueBuilderFactory().createBuilderByLocValueClass(getLocValueClass());
            if (builder == null) {
                throw new VPDARuntimeException("No localization builder found for " + getLocValueClass());
            }
            T locValue = localizationService.localizeData(locKey, context, builder, null, null);
            adjustFromLocValue(locValue);
        }
    }

    @Override
    public abstract Class<T> getLocValueClass();

    /**
     * Adjust properties from localization value
     * 
     * @param locValue
     */
    protected abstract void adjustFromLocValue(T locValue);

    /**
     * @return localization value
     */
    abstract protected T createLocValue();

    @Override
    public void resetLocalized() {
    }

    @Override
    public LocKey getLocKey() {
        return locKey;
    }

    /**
     * Sets localization key
     * 
     * @param key
     */
    @Override
    public void setLocKey(LocKey key) {
        this.locKey = key;
    }

    @Override
    public String toString() {
        return id + " = " + value;
    }

    @Override
    public String getLocalId() {
        return localId;
    }

    @Override
    public String getContainerId() {
        return containerId;
    }

    /**
     * Sets id of container
     * 
     * @param containerId
     */
    @Override
    public void setContainerId(String containerId) {
        if (containerId == null) {
            this.containerId = null;
        }
        else {
            if (this.containerId != null) {
                if (this.containerId.equals(containerId)) {
                    return;
                }
                throw new IllegalArgumentException("Component is already part of container, first remove it");
            }
            this.containerId = containerId;
        }
    }

    @Override
    public String getGroupId() {
        return groupId;
    }

    @Override
    public void setGroupId(String groupId) {
        if (groupId == null) {
            this.groupId = null;
            this.id = localId;
        }
        else {
            if (this.groupId != null) {
                if (this.groupId.equals(groupId)) {
                    return;
                }
                throw new IllegalArgumentException("Component is already part of groupId, first remove it");
            }
            this.groupId = groupId;
            this.id = ComponentUtils.getComponentId(groupId, localId);
        }
    }

    @Override
    public void setLocalId(String localId) {
        this.localId = Assert.isNotEmptyArgument(localId, "localId");
        this.id = this.groupId != null ? ComponentUtils.getComponentId(groupId, localId) : localId;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final AbstractComponent other = (AbstractComponent) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        }
        else if (!id.equals(other.id))
            return false;
        return true;
    }

    @Override
    public void accept(Visitor<AbstractComponent<V, T>> visitor) {
        visitor.visit(this);
    }

    @Override
    public Object getProperty(String key) {
        if (properties == null) {
            return null;
        }
        return properties.get(key);
    }

    @Override
    public Collection<String> getPropertiesKeys() {
        return properties != null ? Collections.<String>unmodifiableCollection(properties.keySet()) : Collections.<String>emptyList();
    }

    @Override
    public void setProperty(String key, Object value) {
        if (properties == null) {
            properties = new HashMap<String, Object>(2);
        }
        properties.put(key, value);
    }

    @Override
    public HorizontalAlignment getHorizontalAlignment() {
        return horizontalAlignment;
    }

    @Override
    public void setHorizontalAlignment(HorizontalAlignment horizontalAlignment) {
        this.horizontalAlignment = horizontalAlignment;
    }

    @Override
    public VerticalAlignment getVerticalAlignment() {
        return verticalAlignment;
    }

    @Override
    public void setVerticalAlignment(VerticalAlignment verticalAlignment) {
        this.verticalAlignment = verticalAlignment;
    }

    @Override
    public String getParentId() {
        return getGroupId();
    }

    @Override
    public ComponentLocalizer getLocalizer() {
        return localizer;
    }

    @Override
    public void setLocalizer(ComponentLocalizer localizer) {
        this.localizer = localizer;
    }

    /**
     * Assign values from another component
     * 
     * @param c
     */

    @Override
    public void assignValues(org.vpda.common.comps.ui.Component<V, T> c) {
        if (!getClass().isInstance(c)) {
            throw new IllegalArgumentException(MessageFormat.format("Cannot assign values from [{0}] to {1}", c.getClass().getName(), getClass().getName()));
        }
        setGroupId(c.getGroupId());
        setLocalId(c.getLocalId());
        setEnabled(c.isEnabled());
        setHorizontalAlignment(c.getHorizontalAlignment());
        setLocKey(c.getLocKey());
        for (String key : c.getPropertiesKeys()) {
            setProperty(key, c.getProperty(key));
        }
        setTooltip(c.getTooltip());
        setValue(c.getValue());
        setVerticalAlignment(c.getVerticalAlignment());
        setVisible(c.isVisible());
        setLocalizer(c.getLocalizer());
    }

    @SuppressWarnings("unchecked")
    @Override
    public AbstractComponent clone() {
        AbstractComponent copy;
        try {
            copy = getClass().newInstance();
        }
        catch (Exception e) {
            throw new RuntimeException("Cannot create component copy", e);
        }
        copy.assignValues(this);
        return copy;
    }

    @Override
    public String getPermissionId() {
        return permissionId;
    }

    @Override
    public void setPermissionId(String permissionId) {
        this.permissionId = permissionId;
    }

    /** Localizer of column */
    public static final class DefaultLocalizer implements ComponentLocalizer, Serializable {
        private static final long serialVersionUID = -5273795134329205981L;
        private static final DefaultLocalizer INSTANCE = new DefaultLocalizer();

        /**
         * @return instance
         */
        public static DefaultLocalizer getInstance() {
            return INSTANCE;
        }

        /**
         * Creates DefaultLocalizer
         */
        public DefaultLocalizer() {
        }

        @Override
        public AbstractComponent localize(AbstractComponent t, LocalizationService localizationService, TenementalContext context) {
            t.defaultLocalize(localizationService, context);
            return t;
        }
    }

    /** Localizer of component with prefix */
    public static final class PrefixLocalizer implements ComponentLocalizer, Serializable {
        private static final long serialVersionUID = -3276183634030465673L;
        private final LocKey prefixKey;
        private final String separator;

        /**
         * Creates localizer with prefix and default separator " - "
         * 
         * @param prefixKey
         */
        public PrefixLocalizer(LocKey prefixKey) {
            this.prefixKey = prefixKey;
            this.separator = " - ";
        }

        /**
         * Creates localizer with prefix and passed separator
         * 
         * @param prefixKey
         * @param separator
         */
        public PrefixLocalizer(LocKey prefixKey, String separator) {
            this.prefixKey = prefixKey;
            this.separator = separator;
        }

        /**
         * @return the prefixKey
         */
        public LocKey getPrefixKey() {
            return prefixKey;
        }

        /**
         * @return the separator
         */
        public String getSeparator() {
            return separator;
        }

        @SuppressWarnings("unchecked")
        @Override
        public AbstractComponent localize(AbstractComponent t, LocalizationService localizationService, TenementalContext context) {
            if (t.getLocKey() != null) {
                LocValueBuilder builder = localizationService.getLocValueBuilderFactory().createBuilderByLocValueClass(t.getLocValueClass());
                if (builder == null) {
                    throw new VPDARuntimeException("No localization builder found for " + t.getLocValueClass());
                }
                LocValue locValue = localizationService.localizeData(t.getLocKey(), context, builder, null, null);
                String prefMsg = localizationService.localizeMessage(prefixKey, context);
                t.adjustFromLocValue((AbstractCompLocValue) locValue);
                if (StringUtil.isNotEmpty(t.getCaption())) {
                    t.setCaption(prefMsg + separator + t.getCaption());
                }
            }
            return t;
        }
    }

}
