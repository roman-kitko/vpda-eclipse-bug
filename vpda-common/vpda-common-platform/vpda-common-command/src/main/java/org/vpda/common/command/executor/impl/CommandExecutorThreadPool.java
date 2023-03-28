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
package org.vpda.common.command.executor.impl;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.vpda.common.command.Command;
import org.vpda.common.command.CommandCallback;
import org.vpda.common.command.CommandEvent;
import org.vpda.common.command.CommandExecutionEnv;
import org.vpda.common.command.CommandExecutorRegistry;
import org.vpda.common.command.CommandWithCallback;
import org.vpda.common.command.FutureCommandExecutor;
import org.vpda.common.command.FutureExecutionResult;
import org.vpda.common.util.Cancellable;
import org.vpda.internal.common.util.Assert;
import org.vpda.internal.common.util.CacheKeyCreator;
import org.vpda.internal.common.util.StaticCache;

/**
 * This executor will invoke execute (
 * {@link ExecutorService#submit(java.util.concurrent.Callable)} using created
 * thread pool.
 * 
 * @author kitko
 *
 */
public final class CommandExecutorThreadPool extends AbstractCommandExecutorBase implements FutureCommandExecutor {
    /**
     * 
     */
    private static final long serialVersionUID = -8904373434865734296L;
    private final ExecutorService tpe;
    private final CommandExecutorRegistry callBackRegistry;
    private final CacheKeyCreator cacheKeyCreatorForCommandSubmit;

    /**
     * Default constructor
     * 
     * @param commandExecutorId
     * @param callBackRegistry
     * @param cacheKeyCreatorForCommandSubmit
     */
    public CommandExecutorThreadPool(String commandExecutorId, CommandExecutorRegistry callBackRegistry, CacheKeyCreator cacheKeyCreatorForCommandSubmit) {
        this(commandExecutorId, Executors.newCachedThreadPool(), callBackRegistry, cacheKeyCreatorForCommandSubmit);
    }

    /**
     * Will create CommandExecutorThreadPool with id and passed pool executor
     * 
     * @param commandExecutorId
     * @param tpe
     * @param callBackRegistry
     * @param cacheKeyCreatorForCommandSubmit
     */
    public CommandExecutorThreadPool(String commandExecutorId, ExecutorService tpe, CommandExecutorRegistry callBackRegistry, CacheKeyCreator cacheKeyCreatorForCommandSubmit) {
        super(commandExecutorId);
        this.tpe = Assert.isNotNullArgument(tpe, "ExecutorService");
        this.callBackRegistry = Assert.isNotNullArgument(callBackRegistry, "callBackRegistry");
        if (callBackRegistry.getRegisteredCommandExecutor(CommandCallback.COMMAND_ITSELF_EXECUTOR_ID) == null) {
            callBackRegistry.registerCommandExecutor(CommandCallback.COMMAND_ITSELF_EXECUTOR_ID, CommandExecutorBase.getDefaultInstance());
        }
        this.cacheKeyCreatorForCommandSubmit = cacheKeyCreatorForCommandSubmit;
    }

    @Override
    public <T> FutureExecutionResult<T> executeCommand(final Command<? extends T> command, final CommandExecutionEnv env, final CommandEvent event) throws Exception {
        Future<T> submit = tpe.submit(new Callable<T>() {
            @Override
            public T call() throws Exception {
                StaticCache.setKeyCreator(cacheKeyCreatorForCommandSubmit);
                T result = command.execute(CommandExecutorThreadPool.this, env, event);
                return result;
            }

        });
        if (command instanceof Cancellable cancellable) {
            return new FutureCancellable<>(submit, cancellable);
        }
        return new FutureExecutionResultBase<>(submit);
    }

    @Override
    public <T> FutureExecutionResult<T> executeCommandWithCallback(Command<? extends T> command, CommandExecutionEnv env, CommandEvent event, CommandCallback<T> callback) throws Exception {
        Future<T> submit = tpe.submit(new Callable<T>() {
            @Override
            public T call() throws Exception {
                CommandWithCallback<T> commandWithCallback = new CommandWithCallback<>(command, callback, callBackRegistry);
                T result = commandWithCallback.execute(CommandExecutorThreadPool.this, env, event);
                return result;
            }
        });
        if (command instanceof Cancellable cancellable) {
            return new FutureCancellable<>(submit, cancellable);
        }
        return new FutureExecutionResultBase<>(submit);
    }

    private static final class FutureCancellable<T> implements FutureExecutionResult<T> {
        private final Future<T> future;
        private final Cancellable cancelable;

        private FutureCancellable(Future<T> future, Cancellable cancelable) {
            this.future = future;
            this.cancelable = cancelable;
        }

        @Override
        public boolean cancel(boolean mayInterruptIfRunning) {
            cancelable.cancel();
            return future.cancel(mayInterruptIfRunning);
        }

        @Override
        public T get() throws InterruptedException, ExecutionException {
            return future.get();
        }

        @Override
        public T get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
            return future.get(timeout, unit);
        }

        @Override
        public boolean isCancelled() {
            return cancelable.isCancelled() || future.isCancelled();
        }

        @Override
        public boolean isDone() {
            return future.isDone();
        }

    }

}
