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
package org.vpda.clientserver.communication.protocol.http;

import java.io.InputStream;
import java.io.Serializable;
import java.util.concurrent.Callable;

import org.apache.hc.core5.http.ClassicHttpResponse;
import org.apache.hc.core5.http.io.HttpClientResponseHandler;
import org.vpda.common.command.Command;
import org.vpda.common.command.CommandEvent;
import org.vpda.common.command.CommandExecutionEnv;
import org.vpda.common.command.CommandExecutor;
import org.vpda.common.command.ExecutionResult;
import org.vpda.common.util.exceptions.VPDARuntimeException;
import org.vpda.internal.common.util.Assert;
import org.vpda.internal.common.util.JavaSerializationUtil;

/**
 * Executes command using HTTP protocol.
 * 
 * First it is constructed by client to connect. Then it is constructed by
 * server when we need to export remote service
 * 
 * @author kitko
 *
 */
public final class HTTPCommandExecutorWrapper implements CommandExecutor, Serializable {
    private static final long serialVersionUID = 6128064262156543420L;
    private final String id;
    private final ExecutorIdentifier identifier;
    private transient final HTTPConnectSettings settings;
    private transient final org.apache.hc.client5.http.classic.HttpClient httpClient;
    private transient final Callable<ClassLoader> resolveClassLoader;
    private transient static ClassLoader classLoader;
    private transient static boolean classLoaderResolved;

    /**
     * Creates HTTPCommandExecutorWrapper with id
     * 
     * @param identifier
     * @param httpClient
     * @param settings
     * @param resolveClassLoader
     */
    public HTTPCommandExecutorWrapper(ExecutorIdentifier identifier, org.apache.hc.client5.http.classic.HttpClient httpClient, HTTPConnectSettings settings, Callable<ClassLoader> resolveClassLoader) {
        this.identifier = Assert.isNotNullArgument(identifier, "identifier");
        this.id = identifier.getCommandExecutorId();
        this.httpClient = Assert.isNotNullArgument(httpClient, "httpClient");
        this.settings = Assert.isNotNullArgument(settings, "settings");
        this.resolveClassLoader = Assert.isNotNullArgument(resolveClassLoader, "resolveClassLoader");
    }

    /**
     * Creates HTTPCommandExecutorWrapper
     * 
     * @param identifier
     */
    public HTTPCommandExecutorWrapper(ExecutorIdentifier identifier) {
        this.identifier = Assert.isNotNullArgument(identifier, "identifier");
        this.id = identifier.getCommandExecutorId();
        this.httpClient = null;
        this.settings = null;
        this.resolveClassLoader = null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> ExecutionResult<T> executeCommand(Command<? extends T> command, CommandExecutionEnv env, CommandEvent event) throws Exception {
        if (classLoader == null) {
            classLoader = resolveClassLoader();
        }
        org.apache.hc.client5.http.classic.HttpClient httpClient = resolveClient(env);
        HTTPConnectSettings settings = resolveSettings(env);
        org.apache.hc.core5.http.HttpHost host = new org.apache.hc.core5.http.HttpHost(settings.getScheme(), settings.getHost(), settings.getPort());
        org.apache.hc.client5.http.classic.methods.HttpPost request = new org.apache.hc.client5.http.classic.methods.HttpPost(settings.getRelativeURI());
        byte[] bytes = JavaSerializationUtil.serializeObjectToByteArray(new HTTPCommandHolder(command, event, identifier));
        request.setEntity(new org.apache.hc.core5.http.io.entity.ByteArrayEntity(bytes, org.apache.hc.core5.http.ContentType.APPLICATION_OCTET_STREAM));
        HttpClientResponseHandler<Object> handler = (ClassicHttpResponse response) -> {
            if (response.getCode() != 200) {
                throw new VPDARuntimeException("Wrong response " + response.getReasonPhrase());
            }
            try (InputStream stream = response.getEntity().getContent()) {
                Object result = null;
                try {
                    if (classLoader == null) {
                        result = JavaSerializationUtil.readObjectFromStream(stream);
                    }
                    else {
                        result = JavaSerializationUtil.readObjectFromStream(stream, classLoader);
                    }
                }
                catch (ClassNotFoundException e) {
                    throw new VPDARuntimeException("Error while loading object result class", e);
                }
                return result;
            }
        };
        Object result = httpClient.execute(host, request, handler);
        if (result instanceof Exception) {
            throw (Exception) result;
        }
        else if (result instanceof ExecutionResult) {
            ExecutionResult<T> comResult = (ExecutionResult<T>) result;
            return comResult;
        }
        else {
            throw new VPDARuntimeException("Invalid result from http command execution " + result);
        }
    }

    private ClassLoader resolveClassLoader() throws Exception {
        if (resolveClassLoader != null) {
            if (!classLoaderResolved) {
                classLoaderResolved = true;
                return resolveClassLoader.call();
            }
        }
        return null;
    }

    private HTTPConnectSettings resolveSettings(CommandExecutionEnv env) {
        if (this.settings != null) {
            return this.settings;
        }
        HTTPConnectSettings settings = env.resolveObject(HTTPConnectSettings.class);
        if (settings == null) {
            throw new IllegalArgumentException("Cannot resolve settings from env");
        }
        return settings;
    }

    private org.apache.hc.client5.http.classic.HttpClient resolveClient(CommandExecutionEnv env) {
        if (this.httpClient != null) {
            return this.httpClient;
        }
        org.apache.hc.client5.http.classic.HttpClient httpClient = env.resolveObject(org.apache.hc.client5.http.classic.HttpClient.class);
        if (httpClient == null) {
            throw new IllegalArgumentException("Cannot resolve httpClient from env");
        }
        return httpClient;
    }

    @Override
    public String getExecutorId() {
        return id;
    }

    @Override
    public CommandExecutor getCommandExecutor() {
        return this;
    }

    @Override
    public void unreferenced() {
    }

    /**
     * @return identifier
     */
    public ExecutorIdentifier getExecutorIdentifier() {
        return identifier;
    }

}
