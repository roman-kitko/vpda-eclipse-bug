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
package org.vpda.clientserver.communication.command;

import java.io.Serializable;
import java.lang.reflect.Method;

import org.vpda.clientserver.communication.CommunicationId;
import org.vpda.clientserver.communication.CommunicationStateKind;
import org.vpda.common.command.CommandExecutionEnv;
import org.vpda.common.ioc.objectresolver.ObjectResolver;
import org.vpda.common.util.exceptions.VPDARuntimeException;

/**
 * Command which calls through reflection some method on resolved interface.
 * This command will be always passed and executed on server side of
 * communication.
 * 
 * @author kitko
 *
 */
final class StatefullInvocationCommand extends AbstractInvocationCommand implements Serializable {
    private static final long serialVersionUID = -662725286060148033L;

    StatefullInvocationCommand(CommunicationId communicationId, Class[] ifaces, String methodName, Class[] paramTypes, Object[] args) {
        super(CommunicationStateKind.Statefull, communicationId, ifaces, methodName, paramTypes, args);
    }

    @Override
    @SuppressWarnings("unchecked")
    ObjectMethod determineObjectMethod(CommandExecutionEnv env) throws Exception {
        if (ifaces.length == 1) {
            Object object = env.resolveObject(ifaces[0]);
            if (object == null) {
                throw new VPDARuntimeException("Cannot resolve interface " + ifaces[0] + " from env ");
            }
            Method method = object.getClass().getMethod(methodName, paramTypes);
            return new ObjectMethod(object, method);
        }
        else {
            Object object = null;
            Method method = null;
            for (Class iface : ifaces) {
                object = env.resolveObject(iface);
                if (object != null) {
                    try {
                        method = object.getClass().getMethod(methodName, paramTypes);
                        return new ObjectMethod(object, method);
                    }
                    catch (NoSuchMethodException e) {
                    }
                }
            }
            throw new VPDARuntimeException("Cannot resolve any interface with method " + method + " from env ");
        }
    }

    @Override
    ObjectResolver determineInvocationContext() {
        return null;
    }

}
