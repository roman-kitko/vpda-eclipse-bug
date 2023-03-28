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
package org.vpda.common.launcher.jetty;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.logging.Level;

import org.eclipse.jetty.util.log.Logger;

/**
 * Logger wrapper for jetty that delegates all calls to
 * {@link java.util.logging.Logger}. It must be public, referenced from system
 * property when launching jetty
 * 
 * @author kitko
 *
 */
public final class JavaLogger implements Logger {

    private final java.util.logging.Logger realLogger;
    private boolean debugEnabled;

    private static ConcurrentMap<String, Logger> allLoggers = new ConcurrentHashMap<String, Logger>(10);

    private final String name;

    /**
     * Creates logger wrapper around {@link java.util.logging.Logger} retrieved
     * using <code>
     * java.util.logging.Logger.getLogger(name)
     * </code>
     * 
     * @param name
     */
    JavaLogger(String name) {
        this.name = name;
        this.realLogger = java.util.logging.Logger.getLogger(name);
        allLoggers.put(name, this);
    }

    private JavaLogger(String name, JavaLogger parent) {
        this.name = name;
        this.realLogger = java.util.logging.Logger.getLogger(name);
        this.realLogger.setParent(parent.realLogger);
    }

    @Override
    public void debug(String msg, Throwable th) {
        realLogger.log(Level.FINEST, msg, th);
    }

    @Override
    public synchronized Logger getLogger(String name) {
        Logger logger = allLoggers.get(name);
        if (logger != null) {
            return logger;
        }
        logger = new JavaLogger(name, this);
        Logger storedLogger = allLoggers.putIfAbsent(name, logger);
        return storedLogger != null ? storedLogger : logger;
    }

    @Override
    public boolean isDebugEnabled() {
        return debugEnabled;
    }

    @Override
    public void setDebugEnabled(boolean enabled) {
        debugEnabled = enabled;
    }

    @Override
    public void warn(String msg, Throwable th) {
        realLogger.log(Level.WARNING, msg, th);
    }

    @Override
    public String toString() {
        return "JAVALOGGER " + name;
    }

    private String formatMsg(String msg) {
        StringBuilder builder = new StringBuilder();
        int counter = 0;
        for (int i = 0; i < msg.length(); i++) {
            char c = msg.charAt(i);
            if (c == '{') {
                if (i < msg.length() - 1) {
                    char cc = msg.charAt(i + 1);
                    if (cc == '}') {
                        builder.append('{');
                        builder.append(counter++);
                        builder.append('}');
                        i++;
                        continue;
                    }
                }
            }
            builder.append(c);
        }
        return builder.toString();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void debug(Throwable ex) {
        realLogger.log(Level.SEVERE, null, ex);

    }

    @Override
    public void debug(String msg, Object... params) {
        if (msg != null) {
            msg = formatMsg(msg);
            realLogger.log(Level.FINE, msg, params);
        }

    }

    @Override
    public void ignore(Throwable arg0) {
    }

    @Override
    public void info(Throwable ex) {
        realLogger.log(Level.INFO, null, ex);

    }

    @Override
    public void info(String msg, Object... params) {
        if (msg != null) {
            msg = formatMsg(msg);
            realLogger.log(Level.INFO, msg, params);
        }

    }

    @Override
    public void info(String msg, Throwable ex) {
        if (msg != null) {
            msg = formatMsg(msg);
        }
        realLogger.log(Level.INFO, msg, ex);
    }

    @Override
    public void warn(Throwable ex) {
        realLogger.log(Level.WARNING, null, ex);
    }

    @Override
    public void warn(String msg, Object... arg1) {
        if (msg != null) {
            msg = formatMsg(msg);
        }

    }

    @Override
    public void debug(String msg, long value) {
        if (msg != null) {
            msg = formatMsg(msg);
            realLogger.log(Level.FINE, msg, new Object[] { value });
        }
    }
}
