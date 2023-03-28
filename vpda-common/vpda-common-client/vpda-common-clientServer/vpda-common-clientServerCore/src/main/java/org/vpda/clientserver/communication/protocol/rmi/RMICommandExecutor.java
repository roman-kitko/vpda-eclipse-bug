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

import java.rmi.Remote;
import java.rmi.RemoteException;

import org.vpda.common.command.Command;
import org.vpda.common.command.CommandEvent;
import org.vpda.common.command.CommandExecutor;
import org.vpda.common.command.ExecutionResult;

/**
 * RMI Command executor interface. This is remote wrapper using RMI protocol for
 * {@link CommandExecutor}
 * 
 * @author kitko
 *
 */
public interface RMICommandExecutor extends Remote {
    /**
     * Executes command
     * 
     * @param <T>
     * @param command
     * @param event   Event that fired command invocation
     * @return result of command execution
     * @throws Exception
     * @throws RemoteException
     */
    public <T> ExecutionResult<T> executeCommand(Command<? extends T> command, CommandEvent event) throws Exception, RemoteException;

    /**
     * 
     * @return CommandExecutorId
     * @throws RemoteException
     */
    public String getId() throws RemoteException;

    /**
     * Loads class bytes using class loader(this class or thread class loader)
     * 
     * @param url
     * @return bytes of class
     * @throws RemoteException
     */
    public byte[] loadClassBytes(String url) throws RemoteException;

    /**
     * Ping the server
     * 
     * @throws RemoteException
     */
    public void ping() throws RemoteException;
}
