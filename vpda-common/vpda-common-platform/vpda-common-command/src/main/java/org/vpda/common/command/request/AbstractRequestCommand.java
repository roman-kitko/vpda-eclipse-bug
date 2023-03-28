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
package org.vpda.common.command.request;

import org.vpda.common.command.AbstractProgressCommand;
import org.vpda.common.command.Command;
import org.vpda.common.command.CommandEvent;
import org.vpda.common.command.CommandExecutionEnv;
import org.vpda.common.command.CommandExecutor;
import org.vpda.common.command.CommandExecutorRegistry;
import org.vpda.common.command.ExecutionResult;
import org.vpda.common.command.ResultWithCommands;
import org.vpda.common.util.exceptions.DefaultLoggingExceptionHandler;
import org.vpda.common.util.exceptions.ExceptionHandler;
import org.vpda.internal.common.util.Assert;

/**
 * Command for ClientServer communication. This is base idea of push protocol
 * between client and server. We will have client API which is basically about
 * UI handling. Server API is about services,authorization ,UI providing... And
 * this commands makes interaction between client and server. They are executed
 * first at client side, then passed to server and executed there. After again
 * executed on server with result from server
 * 
 * @author kitko
 * @param <T>
 *
 */
public abstract class AbstractRequestCommand<T> extends AbstractProgressCommand<T> {
    private transient CommandExecutorRegistry registry;
    private transient CommandExecutor requestCommandExecutor;
    private transient CommandExecutor beforeRequestExecutor;
    private transient CommandExecutor afterRequestExecutor;
    private transient CommandExecutor excHandlingExecutor;

    /**
     * This method is executed before request is made
     * 
     * @param env
     * @param event
     * @return true if we can continue - so call server execution
     */
    protected boolean executeBeforeRequest(CommandExecutionEnv env, CommandEvent event) {
        return true;
    }

    /**
     * This method performs request logic. It decides what should be executed later
     * on server side. Returned command is executed on return from request. This
     * command can be simply returned to requestor. We can return another command to
     * execute. Be careful, this method will be executed on server side, so some
     * resources may not be not available
     * 
     * @param executor
     * @param env
     * @param event
     * @return command as result from server request call
     * @throws Exception
     */
    protected T executeRequest(CommandExecutor executor, CommandExecutionEnv env, CommandEvent event) throws Exception {
        return null;
    }

    /**
     * This method is executed after request. Usually we want same command to
     * execute also afterRequestLogic In this situation, we will simply return this
     * command
     * 
     * @param requestResult
     * @param env
     * @param event
     */
    protected void executeAfterRequest(T requestResult, CommandExecutionEnv env, CommandEvent event) {

    }

    final void executeAfterRequest(ResultWithCommands<T> resWithCommands, CommandExecutionEnv env, CommandEvent event) throws Exception {
        executeAfterRequest(resWithCommands, requestCommandExecutor, env, event);
    }

    /**
     * Executes after result of request command when result is ResultWithCommands
     * 
     * @param resWithCommands
     * @param requestCommandExecutor
     * @param env
     * @param event
     * @throws Exception
     */
    final void executeAfterRequest(ResultWithCommands<T> resWithCommands, CommandExecutor requestCommandExecutor, CommandExecutionEnv env, CommandEvent event) throws Exception {
        if (resWithCommands.isExecuteCommandsOnReturn()) {
            for (Command<?> cmd : resWithCommands.getCommands()) {
                requestCommandExecutor.executeCommand(cmd, env, event);
            }
        }
        executeAfterRequest(resWithCommands.getResult(), env, event);
    }

    /**
     * Runs when exception occurs
     * 
     * @param e
     * @param env
     * @param event
     * @return policy for exception handling
     */
    protected RequestCommandExceptionExecutionPolicy executeExceptionHandling(Exception e, CommandExecutionEnv env, CommandEvent event) {
        ExceptionHandler handler = env.resolveObject(ExceptionHandler.class);
        if (handler == null) {
            handler = DefaultLoggingExceptionHandler.getInstance();
        }
        handler.handleException(e);
        return RequestCommandExceptionExecutionPolicy.ABORT;
    }

    /**
     * Creates RequestComamndBase
     * 
     * @param registry - this is registry where we will find executors for
     *                 RequestCommandBaseConst.BEFORE_REQUEST_COMMAND_EXECUTOR
     *                 RequestCommandBaseConst.REQUEST_COMMAND_EXECUTOR
     *                 RequestCommandBaseConst.AFTER_REQUEST_COMMAND_EXECUTOR
     *                 RequestCommandBaseConst.EXCEPTION_HANDLING_COMMAND_EXECUTOR
     */
    protected AbstractRequestCommand(CommandExecutorRegistry registry) {
        this(registry, (CommandExecutor) null);
    }

    /**
     * /** Creates RequestComamndBase
     * 
     * @param registry               - this is registry where we will find executors
     *                               for
     *                               RequestCommandBaseConst.BEFORE_REQUEST_COMMAND_EXECUTOR
     *                               RequestCommandBaseConst.REQUEST_COMMAND_EXECUTOR
     *                               RequestCommandBaseConst.AFTER_REQUEST_COMMAND_EXECUTOR
     *                               RequestCommandBaseConst.EXCEPTION_HANDLING_COMMAND_EXECUTOR
     * @param requestCommandExecutor
     */
    protected AbstractRequestCommand(CommandExecutorRegistry registry, CommandExecutor requestCommandExecutor) {
        super();
        this.registry = Assert.isNotNull(registry, "Registry argument is null");
        this.requestCommandExecutor = requestCommandExecutor;
        if (this.requestCommandExecutor == null) {
            this.requestCommandExecutor = registry.getRegisteredCommandExecutor(RequestCommandBaseConst.REQUEST_COMMAND_EXECUTOR);
        }
        if (this.requestCommandExecutor == null) {
            throw new IllegalArgumentException("RequestCommandExecutor not registered in CommandExecutorRegistry");
        }
        beforeRequestExecutor = registry.getRegisteredCommandExecutor(RequestCommandBaseConst.BEFORE_REQUEST_COMMAND_EXECUTOR);
        if (beforeRequestExecutor == null) {
            beforeRequestExecutor = this.requestCommandExecutor;
        }
        afterRequestExecutor = registry.getRegisteredCommandExecutor(RequestCommandBaseConst.AFTER_REQUEST_COMMAND_EXECUTOR);
        if (afterRequestExecutor == null) {
            afterRequestExecutor = this.requestCommandExecutor;
        }
        excHandlingExecutor = registry.getRegisteredCommandExecutor(RequestCommandBaseConst.EXCEPTION_HANDLING_COMMAND_EXECUTOR);
        if (excHandlingExecutor == null) {
            excHandlingExecutor = this.requestCommandExecutor;
        }
    }

    @Override
    protected final T doExecute(CommandExecutor executor, CommandExecutionEnv env, CommandEvent event) throws Exception {
        try {
            if (!beforeRequestExecutor.executeCommand(new BeforeRequestCommand<T>(this), env, event).get()) {
                return null;
            }
        }
        catch (Exception e) {
            RequestCommandExceptionExecutionPolicy policy = excHandlingExecutor.executeCommand(new ExceptionHandlingCommand<T>(this, e), env, event).get();
            if (policy == RequestCommandExceptionExecutionPolicy.ABORT) {
                return null;
            }
        }
        ExecutionResult<T> requestResult = null;
        try {
            requestResult = requestCommandExecutor.executeCommand(new RequestCommandInternal<T>(this), env, event);
        }
        catch (Exception e) {
            RequestCommandExceptionExecutionPolicy policy = excHandlingExecutor.executeCommand(new ExceptionHandlingCommand<T>(this, e), env, event).get();
            if (policy == null) {
                return null;
            }
            if (policy == RequestCommandExceptionExecutionPolicy.ABORT) {
                return null;
            }
        }
        try {
            ExecutionResult<Object> er = afterRequestExecutor.executeCommand(new AfterRequestCommand<T>(this, requestResult), env, event);
            er.get();
        }
        catch (Exception e) {
            RequestCommandExceptionExecutionPolicy policy = excHandlingExecutor.executeCommand(new ExceptionHandlingCommand<T>(this, e), env, event).get();
            if (policy == RequestCommandExceptionExecutionPolicy.ABORT) {
                return null;
            }
        }
        return requestResult.get();
    }

    @Override
    protected boolean canNotifyOnExecutionFinished() {
        return false;
    }

    /**
     * @return the registry
     */
    protected CommandExecutorRegistry getRegistry() {
        return registry;
    }

    /**
     * @return the requestCommandExecutor
     */
    protected CommandExecutor getRequestCommandExecutor() {
        return requestCommandExecutor;
    }
}
