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
package org.vpda.common.listeners;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import jakarta.servlet.http.HttpSessionEvent;
import jakarta.servlet.http.HttpSessionListener;

/** Common HttpSessionListener */
public final class VPDAHttpSessionListener implements HttpSessionListener {

    private static List<HttpSessionListener> delegates = new CopyOnWriteArrayList<>();

    /**
     * Sets our delegate
     * 
     * @param delegate
     */
    public static void addDelegate(HttpSessionListener delegate) {
        delegates.add(delegate);
    }

    /**
     * @return all delegates
     */
    public static List<HttpSessionListener> getDelegates() {
        return Collections.unmodifiableList(delegates);
    }

    @Override
    public void sessionCreated(HttpSessionEvent sessionEvent) {
        for (HttpSessionListener delegate : delegates) {
            delegate.sessionCreated(sessionEvent);
        }
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent sessionEvent) {
        for (HttpSessionListener delegate : delegates) {
            delegate.sessionDestroyed(sessionEvent);
        }
    }
}
