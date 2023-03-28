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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.vpda.common.comps.MemberContainer;
import org.vpda.common.comps.loc.AbstractCompLocValue;
import org.vpda.common.context.TenementalContext;
import org.vpda.common.service.localization.LocalizationService;

/**
 * Container of one component
 * 
 * @author kitko
 * @param <V>
 * @param <L>
 *
 */
public abstract class SingleComponentContainerAC<V, L extends AbstractCompLocValue> extends AbstractComponent<V, L> implements Container<V, L> {
    private static final long serialVersionUID = 3917412815509450666L;

    /**
     * Creates SingleComponentContainerAC
     */
    public SingleComponentContainerAC() {
    }

    /**
     * Creates SingleComponentContainerAC
     * 
     * @param localId
     * @param content
     */
    public SingleComponentContainerAC(String localId, AbstractComponent content) {
        super(localId);
        setContent(content);
    }

    private Component content;

    /**
     * 
     * @return content
     */
    public Component getContent() {
        return content;
    }

    /**
     * @param content
     * @return this
     */
    public SingleComponentContainerAC setContent(AbstractComponent content) {
        this.content = content;
        return this;
    }

    @Override
    public void adjustFromLocValue(L locValue) {
        if (locValue == null) {
            return;
        }
        setTooltip(locValue.getTooltip());
    }

    @Override
    protected L createLocValue() {
        throw new UnsupportedOperationException("createLocValue not supported for " + getClass());
    }

    @Override
    public List<Component> getMembers() {
        return content != null ? Collections.singletonList(content) : Collections.emptyList();
    }

    @Override
    public List<Component<?, ?>> getAllMembers() {
        if(content == null) {
            return Collections.emptyList();
        }
        if(content instanceof MemberContainer cont) {
            List<Component<?,?>> all = new ArrayList<>();
            all.add(content);
            all.addAll(cont.getAllMembers());
        }
        return Collections.singletonList(content);
    }

    @Override
    public Map<String, ? extends Component> getMembersMapping() {
        return content != null ? Collections.singletonMap(content.getId(), content) : Collections.emptyMap();
    }

    @Override
    public Component getMember(String id) {
        return content != null && (id.equals(content.getId()) || id.equals(content.getLocalId())) ? content : null;
    }

    @Override
    public List<String> getMembersLocalIds() {
        return content != null ? Collections.singletonList(content.getLocalId()) : Collections.emptyList();
    }

    @Override
    public List<String> getMembersIds() {
        return content != null ? Collections.singletonList(content.getId()) : Collections.emptyList();
    }

    @Override
    public Component getComponent(String childId) {
        return getMember(childId);
    }

    @Override
    public <X extends Component> X getComponent(String childId, Class<X> targetType) {
        Object o = getComponent(childId);
        return targetType.cast(o);
    }

    @Override
    public int getComponentsCount() {
        return content != null ? 1 : 0;
    }

    @SuppressWarnings({ "unchecked", "hiding" })
    @Override
    public <V, T extends AbstractCompLocValue> Component<V, T> getComponent(int i) {
        return content != null && i == 0 ? content : null;
    }

    @Override
    public List<org.vpda.common.comps.ui.Component<?, ?>> getComponents() {
        return content != null ? Collections.singletonList(content) : Collections.emptyList();
    }

    @Override
    public void localize(LocalizationService localizationService, TenementalContext context) {
        super.localize(localizationService, context);
        if (content != null) {
            content.localize(localizationService, context);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public void assignValues(Component c) {
        super.assignValues(c);
        if (c instanceof SingleComponentContainerAC && ((SingleComponentContainerAC) c).getContent() != null) {
            this.content = ((SingleComponentContainerAC) c).clone();
        }
    }

}
