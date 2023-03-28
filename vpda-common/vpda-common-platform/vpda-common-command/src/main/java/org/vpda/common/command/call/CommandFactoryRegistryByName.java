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
package org.vpda.common.command.call;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.vpda.common.command.Command;
import org.vpda.common.util.exceptions.VPDARuntimeException;

/**
 * Creates Command by registered names of {@link CommandCall} calls
 * 
 * @author kitko
 *
 */
public final class CommandFactoryRegistryByName implements CommandFactory {
    private final Map<String, Command<?>> registry;

    @Override
    public Command<?> createCommand(Call call) {
        Command<?> cmd = registry.get(call.getName());
        if (cmd != null) {
            for (String paramName : call.getParamNames()) {
                Object paramValue = call.getParamValue(paramName);
                if (paramValue == null) {
                    continue;
                }
                Method method = findSetterMethod(cmd, paramName, paramValue);
                try {
                    method.setAccessible(true);
                    method.invoke(cmd, paramValue);
                }
                catch (Exception e) {
                    throw new VPDARuntimeException("Error setting arguments to command", e);
                }
            }
        }
        return cmd;
    }

    private Method findSetterMethod(Command<?> cmd, String paramName, Object paramValue) {
        String setterName = "set" + Character.toUpperCase(paramName.charAt(0)) + paramName.substring(1);
        for (Method method : cmd.getClass().getMethods()) {
            if (setterName.equals(method.getName()) && method.getParameterTypes().length == 1) {
                if (method.getParameterTypes()[0].isInstance(paramValue)) {
                    return method;
                }
            }
        }
        throw new IllegalArgumentException("Cannot find setter method : " + paramName + " for command " + cmd);
    }

    /**
     * Register commands by call name
     * 
     * @param name
     * @param command
     */
    public void registerCommand(String name, Command<?> command) {
        registry.put(name, command);
    }

    /**
     * Creates CommandFactoryRegistryByName
     */
    public CommandFactoryRegistryByName() {
        registry = new HashMap<>(5);
    }

}
