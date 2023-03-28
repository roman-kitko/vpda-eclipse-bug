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
package org.vpda.common.comps.ui.resolved;

import org.vpda.common.comps.loc.SimpleLocValue;
import org.vpda.common.comps.ui.AbstractComponent;
import org.vpda.common.comps.ui.Component;

/**
 * 
 * @author kitko
 *
 * @param <E> type of data
 * @param <U> type of created UI
 */
public final class ResolvingUIBasedComponent<E, U> extends AbstractComponent<E, SimpleLocValue> {
    private static final long serialVersionUID = 5491439884108888533L;
    private ComponetUIResolver<E, U> resolver;
    private boolean editable = true;

    /**
     * Creates ResolvingUIBasedComponent
     */
    public ResolvingUIBasedComponent() {
        resolver = null;
    }

    /**
     * Creates ResolvingUIBasedComponent
     * 
     * @param localId
     */
    public ResolvingUIBasedComponent(String localId) {
        this(localId, null);
    }

    /**
     * Creates ResolvingUIBasedComponent
     * 
     * @param localId
     * @param resolver
     */
    public ResolvingUIBasedComponent(String localId, ComponetUIResolver<E, U> resolver) {
        super(localId);
        this.resolver = resolver;
    }

    @Override
    public Class<SimpleLocValue> getLocValueClass() {
        return SimpleLocValue.class;
    }

    @Override
    protected void adjustFromLocValue(SimpleLocValue locValue) {

    }

    @Override
    protected SimpleLocValue createLocValue() {
        return new SimpleLocValue(getTooltip());
    }

    /**
     * 
     * @return resolver for this component
     */
    public ComponetUIResolver<E, U> getResolver() {
        return resolver;
    }

    /**
     * @param resolver the resolver to set
     */
    public void setResolver(ComponetUIResolver<E, U> resolver) {
        this.resolver = resolver;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void assignValues(Component<E, SimpleLocValue> c) {
        super.assignValues(c);
        ResolvingUIBasedComponent rc = (ResolvingUIBasedComponent) c;
        this.resolver = rc.getResolver();
        this.editable = rc.isEditable();
    }

    /**
     * @return the editable
     */
    public boolean isEditable() {
        return editable;
    }

    /**
     * @param editable the editable to set
     */
    public void setEditable(boolean editable) {
        this.editable = editable;
    }

}
