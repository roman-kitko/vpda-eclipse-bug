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

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

import org.eclipse.jetty.http.spi.HttpSpiContextHandler;
import org.eclipse.jetty.http.spi.JettyHttpServer;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.AbstractHandlerContainer;
import org.vpda.common.util.exceptions.VPDARuntimeException;
import org.vpda.common.util.logging.LoggerMethodTracer;

import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpsServer;
import com.sun.net.httpserver.spi.HttpServerProvider;

/** Http Server provider */
public final class JettyHttpServerProvider extends HttpServerProvider {

    private static final LoggerMethodTracer logger = LoggerMethodTracer.getLogger(JettyLauncher.class);
    private static final CountDownLatch WAIT_FOR_SERVER = new CountDownLatch(1);

    @Override
    public HttpServer createHttpServer(InetSocketAddress addr, int backlog) throws IOException {
        final int waitTime = 12000;
        if (server == null) {
            System.out.println("Waiting for jetty to start");
            try {
                WAIT_FOR_SERVER.await(waitTime, TimeUnit.SECONDS);
            }
            catch (InterruptedException e) {
                throw new IOException("Thread interrputed", e);
            }
        }
        if (server == null) {
            throw new IOException("Jetty server not started even after " + waitTime + " seconds");
        }
        org.eclipse.jetty.http.spi.JettyHttpServerProvider.setServer(server);
        JettyHttpServer jettyHttpServer = new JettyHttpServer(server, true) {
            @Override
            public void bind(InetSocketAddress addr, int backlog) throws IOException {
                logger.info("Will not bind JettyHttpServer again to " + addr);
            }

            @Override
            public void setExecutor(Executor executor) {
            }

            @Override
            public HttpContext createContext(String path, HttpHandler httpHandler) {
                HttpContext ctx = super.createContext(path, httpHandler);
                for (org.eclipse.jetty.server.Handler handler : ((AbstractHandlerContainer) server.getHandler()).getChildHandlers()) {
                    if (handler instanceof HttpSpiContextHandler) {
                        HttpSpiContextHandler spiHandler = (HttpSpiContextHandler) handler;
                        try {
                            // We need to explicitly start HttpSpiContextHandler, cause server was already
                            // started and will not start this handler
                            spiHandler.start();
                        }
                        catch (Exception e) {
                            throw new VPDARuntimeException("Error starting HttpSpiContextHandler", e);
                        }
                    }
                }
                return ctx;
            }
        };
        jettyHttpServer.bind(addr, backlog);
        return jettyHttpServer;
    }

    @Override
    public HttpsServer createHttpsServer(InetSocketAddress arg0, int arg1) throws IOException {
        throw new UnsupportedOperationException();
    }

    private static Server server;

    static void setServer(Server server) {
        JettyHttpServerProvider.server = server;
        WAIT_FOR_SERVER.countDown();
    }

    /**
     * 
     */
    public JettyHttpServerProvider() {
        super();
    }

}
