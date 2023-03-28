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
package org.vpda.clientserver.communication.protocol.rmi;

import java.rmi.RemoteException;

import org.vpda.common.command.Command;
import org.vpda.common.command.CommandEvent;
import org.vpda.common.command.ExecutionResult;

/**
 * {@link RMICommandExecutor} that will try to repat call on failure using
 * {@link RMICommandExecutorFactory} to create new RMICommandExecutor
 * 
 * @author kitko
 *
 */
public final class RetryingRMICommandExecutor implements RMICommandExecutor {

    private final RMICommandExecutorFactory factory;
    private RMICommandExecutor current;

    /**
     * @param factory
     * @param current
     */
    public RetryingRMICommandExecutor(RMICommandExecutorFactory factory, RMICommandExecutor current) {
        super();
        this.factory = factory;
        this.current = current;
    }

    /**
     * @param factory
     */
    public RetryingRMICommandExecutor(RMICommandExecutorFactory factory) {
        super();
        this.factory = factory;
        this.current = factory.createRMICommandExecutor();
    }

    @Override
    public <T> ExecutionResult<T> executeCommand(Command<? extends T> command, CommandEvent event) throws Exception, RemoteException {
        try {
            return current.executeCommand(command, event);
        }
        catch (RemoteException e) {
            recreateOnFailure(e);
            return current.executeCommand(command, event);
        }
    }

    @Override
    public String getId() throws RemoteException {
        try {
            return current.getId();
        }
        catch (RemoteException e) {
            recreateOnFailure(e);
            return current.getId();
        }
    }

    @Override
    public byte[] loadClassBytes(String url) throws RemoteException {
        try {
            return current.loadClassBytes(url);
        }
        catch (RemoteException e) {
            recreateOnFailure(e);
            return current.loadClassBytes(url);
        }
    }

    @Override
    public void ping() throws RemoteException {
        try {
            current.ping();
            return;
        }
        catch (RemoteException e) {
            recreateOnFailure(e);
            current.ping();
            return;
        }
    }

    private boolean shouldRetryOnThisFailure(Exception e) {
        if (e instanceof java.rmi.NoSuchObjectException || e.getCause() instanceof java.rmi.NoSuchObjectException) {
            return true;
        }
        return false;
    }

    private void recreateOnFailure(RemoteException e) throws RemoteException {
        if (shouldRetryOnThisFailure(e)) {
            current = factory.createRMICommandExecutor();
            current.ping();
        }
        else {
            throw e;
        }
    }

}
