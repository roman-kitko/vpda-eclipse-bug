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
package org.vpda.common.launcher.jetty;

import java.util.concurrent.TimeUnit;

import org.eclipse.jetty.server.Server;

/** Holder for jetty server */
public final class JettyServerHolder {
    private static volatile Server server;

    private JettyServerHolder() {
    }

    /**
     * Gets jetty server, fails if no server yet
     * 
     * @return the server
     * 
     */
    public static synchronized Server getRequiredServer() {
        if (server == null) {
            throw new IllegalStateException("No jetty server available yet");
        }
        return server;
    }

    /**
     * @return jetty server
     */
    public static synchronized Server getServer() {
        return server;
    }

    /**
     * Waits for jetty server to be available in timeout duration
     * 
     * @param timeout
     * @param unit
     * @return Server
     * @throws IllegalStateException if jetty is not available
     */
    public static synchronized Server getRequiredServer(long timeout, TimeUnit unit) {
        if (server == null) {
            long remainingTime = unit.toMillis(timeout);
            long waitTime = 300;
            while (true) {
                long startWaitTime = System.currentTimeMillis();
                try {
                    JettyServerHolder.class.wait(waitTime);
                }
                catch (InterruptedException e) {
                    throw new IllegalStateException("No jetty server available yet", e);
                }
                if (server != null) {
                    return server;
                }
                remainingTime = remainingTime - (startWaitTime - System.currentTimeMillis());
                if (remainingTime <= 0) {
                    throw new IllegalStateException("No jetty server available yet after timeout");
                }
            }
        }

        if (server == null) {
            throw new IllegalStateException("No jetty server available yet");
        }
        return server;
    }

    /**
     * @param server the server to set
     */
    public static synchronized void setServer(Server server) {
        JettyServerHolder.server = server;
        JettyServerHolder.class.notifyAll();
    }

}
