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
package org.vpda.common.util.exceptions;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.UndeclaredThrowableException;
import java.rmi.RemoteException;
import java.util.concurrent.ExecutionException;

/**
 * Utilities related to exceptions
 * 
 * @author kitko
 *
 */
public final class ExceptionUtil {
    private static final class PrivateException extends Throwable {
        private static final long serialVersionUID = 5173931721448985132L;
    }

    /**
     * Traverse exception chains and tries to find root exception. It will stop it
     * it will find exception that is instanceof stopOnException
     * 
     * @param e
     * @param stopOnException
     * @return root exception
     */
    public static Throwable getRootException(Throwable e, Class<?> stopOnException) {
        if (stopOnException.isInstance(e)) {
            return e;
        }
        if (e instanceof InvocationTargetException) {
            return getRootException(e, ((InvocationTargetException) e).getTargetException(), stopOnException);
        }
        else if (e instanceof TracedRuntimeExceptionWrapper) {
            return getRootException(e, ((TracedRuntimeExceptionWrapper) e).getCause(), stopOnException);
        }
        else if (e instanceof ExecutionException) {
            return getRootException(e, ((ExecutionException) e).getCause(), stopOnException);
        }
        else if (e instanceof UndeclaredThrowableException) {
            return getRootException(e, ((UndeclaredThrowableException) e).getUndeclaredThrowable(), stopOnException);
        }
        else if (e instanceof RemoteException) {
            return getRootException(e, ((RemoteException) e).getCause(), stopOnException);
        }
        return e;
    }

    /**
     * Traverse exception chains and tries to find root exception.
     * 
     * @param e
     * @return root exception
     */
    public static Throwable getRootException(Throwable e) {
        return getRootException(e, PrivateException.class);
    }

    private static Throwable getRootException(Throwable original, Throwable child, Class<?> stopOnException) {
        if (child != null) {
            return getRootException(child, stopOnException);
        }
        return original;
    }

}
