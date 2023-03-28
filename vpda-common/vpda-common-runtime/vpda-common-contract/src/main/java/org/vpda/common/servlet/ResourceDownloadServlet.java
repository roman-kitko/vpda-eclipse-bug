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
/**
 * 
 */
package org.vpda.common.servlet;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.logging.Level;

import org.vpda.common.util.logging.LoggerMethodTracer;

import jakarta.servlet.ServletException;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet that can download resources
 * 
 * @author kitko
 *
 */
public class ResourceDownloadServlet extends HttpServlet {
    private static final long serialVersionUID = -3271752285055231962L;

    private static final LoggerMethodTracer logger = LoggerMethodTracer.getLogger(ResourceDownloadServlet.class);

    private final ConcurrentMap<String, byte[]> cache;

    private static final byte[] NOT_EXISTS = new byte[0];

    /** Creates a servlet */
    public ResourceDownloadServlet() {
        this.cache = new ConcurrentHashMap<String, byte[]>(1000);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uri = req.getRequestURI();
        if (logger.isLoggable(Level.FINE)) {
            logger.fine("Gettings resource for uri : " + uri);
        }
        String contextPath = req.getContextPath();
        String servletPath = req.getServletPath();
        if ((contextPath + servletPath).equals(uri)) {
            throw new ServletException("No resource requested");
        }
        String resourceName = uri.substring((contextPath + servletPath).length() + 1);
        resourceName = resourceName.replace('\\', '/');
        byte[] bytes = cache.get(resourceName);
        if (bytes == NOT_EXISTS) {
            return;
        }
        if (bytes != null) {
            new ByteArrayInputStream(bytes).transferTo(resp.getOutputStream());
            return;
        }
        URL url = Thread.currentThread().getContextClassLoader().getResource(resourceName);
        if (url != null) {
            InputStream inputStream = url.openStream();
            ByteArrayOutputStream bos = new ByteArrayOutputStream(2048);
            inputStream.transferTo(bos);
            inputStream.close();
            bytes = bos.toByteArray();
            ServletOutputStream outputStream = resp.getOutputStream();
            new ByteArrayInputStream(bytes).transferTo(outputStream);
            cache.putIfAbsent(resourceName, bytes);
            resp.setStatus(HttpServletResponse.SC_OK);
        }
        else {
            logger.warning("Cannot find URL resource for uri : " + uri);
            cache.putIfAbsent(resourceName, NOT_EXISTS);
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }

}
