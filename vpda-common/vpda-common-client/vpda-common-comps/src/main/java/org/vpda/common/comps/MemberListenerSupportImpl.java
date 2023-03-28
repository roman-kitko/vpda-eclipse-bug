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
package org.vpda.common.comps;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.vpda.internal.common.util.Assert;

/**
 * Implementation of MemberListenerSupport
 * 
 * @author kitko
 *
 */
public final class MemberListenerSupportImpl implements MemberListenerSupportWithFireForSingleMember, Serializable {
    private static final long serialVersionUID = -1966256433736375328L;
    private List<MemberListener<?>> listeners;
    private final Member member;

    /**
     * Creates MemberListenerSupportImpl
     * 
     * @param member
     */
    public MemberListenerSupportImpl(Member member) {
        this.member = Assert.isNotNullArgument(member, "member");
    }

    @Override
    public <T extends Member> void addMemberListener(MemberListener<T> listener) {
        if (listeners == null) {
            listeners = new CopyOnWriteArrayList<>();
        }
        listeners.add(listener);
    }

    @Override
    public <T extends Member> void removeMemberListener(MemberListener<T> listener) {
        if (listeners == null || listeners.isEmpty()) {
            return;
        }
        for (Iterator<MemberListener<?>> l = listeners.iterator(); l.hasNext();) {
            if (l.next() == listener) {
                l.remove();
            }
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends Member> List<MemberListener<T>> getMemberListeners() {
        return (List<MemberListener<T>>) (listeners == null || listeners.isEmpty() ? Collections.<MemberListener<?>>emptyList() : new ArrayList<MemberListener>(listeners));
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends Member> void fireEnvironmentDataChanged(EnvironmentDataChangeEvent changeEvent) {
        if (listeners == null || listeners.isEmpty()) {
            return;
        }
        for (MemberListener ml : listeners) {
            ml.environmentDataChanged(member, changeEvent);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends Member> void fireEnvironmentInitialized(EnvironmentInitEvent initEvent) {
        if (listeners == null || listeners.isEmpty()) {
            return;
        }
        for (MemberListener ml : listeners) {
            ml.environmentInitialized(member, initEvent);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends Member> void fireEnvironmentStructureChanged(EnvironmentStructureChangeEvent event) {
        if (listeners == null || listeners.isEmpty()) {
            return;
        }
        for (MemberListener ml : listeners) {
            ml.environmentStructureChanged(member, event);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends Member> void fireEnvironmentLayoutChanged(EnvironmentLayoutChangeEvent event) {
        if (listeners == null || listeners.isEmpty()) {
            return;
        }
        for (MemberListener ml : listeners) {
            ml.environmentLayoutChanged(member, event);
        }
    }

}
