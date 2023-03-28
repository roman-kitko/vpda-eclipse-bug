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
package org.vpda.common.entrypoint;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Listener support for application entry point
 * 
 * @author kitko
 *
 */
public final class AppEntryPointListenerSupport {
    private final List<EntryPointListener> listeners = new CopyOnWriteArrayList<EntryPointListener>();

    /**
     * Register new EntryPointListener
     * 
     * @param listener
     */
    public void registerEntryPointListener(EntryPointListener listener) {
        this.listeners.add(listener);
    }

    /**
     * Unregister EntryPointListener
     * 
     * @param listener
     */
    public void unregisterEntryPointListener(EntryPointListener listener) {
        this.listeners.remove(listener);
    }

    void clear() {
        this.listeners.clear();
    }

    List<EntryPointListener> getListeners() {
        return Collections.unmodifiableList(listeners);
    }

}