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

import java.util.concurrent.ExecutionException;

import org.vpda.common.command.Command;
import org.vpda.common.command.CommandEvent;
import org.vpda.common.command.CommandExecutionEnv;
import org.vpda.common.command.CommandExecutor;
import org.vpda.common.command.ExecutionResult;
import org.vpda.common.command.ResultWithCommands;
import org.vpda.internal.common.util.Assert;

/**
 * @author kitko
 *
 */
final class AfterRequestCommand<T> implements Command<Object> {
    private final AbstractRequestCommand<T> reqCmd;
    private final ExecutionResult<T> requestResult;

    @SuppressWarnings("unchecked")
    @Override
    public Object execute(CommandExecutor executor, CommandExecutionEnv env, CommandEvent event) throws Exception {
        Object res = requestResult.get();
        if (res instanceof ResultWithCommands rq) {
            reqCmd.executeAfterRequest(rq, env, event);
        }
        else {
            T realResult = getRootResult(requestResult);
            reqCmd.executeAfterRequest(realResult, env, event);
        }
        return null;
    }

    AfterRequestCommand(AbstractRequestCommand<T> reqCmd, ExecutionResult<T> requestResult) {
        super();
        this.reqCmd = Assert.isNotNull(reqCmd);
        this.requestResult = requestResult;
    }

    /**
     * Returns root result for commandExecutionResult if it is nested
     * 
     * @param <T>
     * @param commandExecutionResult
     * @return root result
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @SuppressWarnings("unchecked")
    private static <R> R getRootResult(ExecutionResult<R> commandExecutionResult) throws InterruptedException, ExecutionException {
        if (commandExecutionResult == null) {
            return null;
        }
        R result = commandExecutionResult.get();
        if (result instanceof ExecutionResult) {
            return getRootResult((ExecutionResult<R>) result);
        }
        else if (result instanceof ResultWithCommands) {
            return getRootResult((ResultWithCommands<R>) result);
        }
        else {
            return result;
        }
    }

    /**
     * Returns root result for commandExecutionResult if it is nested
     * 
     * @param <R>
     * @param resultWithCommands
     * @return root result
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @SuppressWarnings("unchecked")
    private static <R> R getRootResult(ResultWithCommands<R> resultWithCommands) throws InterruptedException, ExecutionException {
        if (resultWithCommands == null) {
            return null;
        }
        R result = resultWithCommands.getResult();
        if (result instanceof ResultWithCommands) {
            return getRootResult((ResultWithCommands<R>) result);
        }
        else if (result instanceof ExecutionResult) {
            return getRootResult((ExecutionResult<R>) result);
        }
        else {
            return result;
        }
    }

}
