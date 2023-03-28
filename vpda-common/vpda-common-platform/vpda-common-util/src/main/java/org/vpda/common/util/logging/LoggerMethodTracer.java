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
package org.vpda.common.util.logging;

import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import org.vpda.common.util.exceptions.VPDARuntimeException;

/**
 * This is java util.Logger with method tracing support
 * 
 * @author kitko
 *
 */
public final class LoggerMethodTracer extends Logger {

    private static volatile boolean logToSysoutOnly;

    /** How we should log method progress */
    public static enum MethodProgressLogTimes {
        /** Log progress from start and from last id */
        FromStartAndLast,
        /** Log progress just from start */
        FromStartOnly,
        /** Do not log progress */
        NONE;
    }

    /**
     * @return logToSysoutOnly
     */
    public static boolean isLogToSysoutOnly() {
        return logToSysoutOnly;
    }

    /**
     * @param logToSysoutOnly
     */
    public static void setLogToSysoutOnly(boolean logToSysoutOnly) {
        LoggerMethodTracer.logToSysoutOnly = logToSysoutOnly;
    }

    /**
     * Creates new Logger
     * 
     * @param name
     * @param resourceBundleName
     */
    private LoggerMethodTracer(String name, String resourceBundleName) {
        super(name, resourceBundleName);
    }

    /**
     * Gets or creates the logger
     * 
     * @param clazz
     * @return logger
     */
    public static synchronized LoggerMethodTracer getLogger(Class<?> clazz) {
        LogManager manager = LogManager.getLogManager();
        String name = clazz.getName();
        Logger result = manager.getLogger(name);
        if (!(result instanceof LoggerMethodTracer)) {
            result = new LoggerMethodTracer(name, null);
            manager.addLogger(result);
        }
        if (result.getParent() == null) {
            Logger root = manager.getLogger("");
            result.setParent(root);
        }
        return (LoggerMethodTracer) result;
    }

    /**
     * Creates new timer with id
     * 
     * @param level
     * @param createId
     * @return new Timer if level is loggable
     */
    public MethodTimer createTimer(Level level, String createId) {
        return createTimer(level, createId, null);
    }

    /**
     * Creates new timer with id and logs msg
     * 
     * @param level
     * @param createId
     * @param msg
     * @param args
     * @return new Timer if level is loggable
     */
    private MethodTimer createTimer(Level level, String createId, String msg, Object... args) {
        if (!isLoggable(level)) {
            return null;
        }
        LogRecord record = new LogRecord(level, msg);
        record.setParameters(args);
        inferCaller(record);
        if (msg != null) {
            log(record);
        }
        return new MethodTimer(level, record.getSourceClassName(), record.getSourceMethodName(), createId);
    }

    /**
     * Log method entry to logger
     * 
     * @param level
     * @return MethodTimer
     */
    public MethodTimer methodEntry(Level level) {
        return createTimer(level, "Entry", "Entry");
    }

    /**
     * Log method entry to logger
     * 
     * @param level
     * @param msg
     * @param args
     * @return MethodTimer
     */
    public MethodTimer methodEntry(Level level, String msg, Object... args) {
        return createTimer(level, "Entry", msg, args);
    }

    /**
     * @param level
     * @param timer
     * @param progressId
     * @param msg
     * @param args
     * @return MethodTimer
     */
    public MethodTimer methodProgress(Level level, MethodTimer timer, String progressId, String msg, Object... args) {
        return methodProgress(level, timer, MethodProgressLogTimes.FromStartAndLast, progressId, msg, args);
    }

    /**
     * @param level
     * @param timer
     * @param methodProgressLogTimes
     * @param progressId
     * @param msg
     * @param args
     * @return MethodTimer
     */
    public MethodTimer methodProgress(Level level, MethodTimer timer, MethodProgressLogTimes methodProgressLogTimes, String progressId, String msg, Object... args) {
        if (timer == null || !isLoggable(level)) {
            return null;
        }
        long duration = timer.stopWatch.duration();
        long nextDuration = timer.stopWatch.nextDuration();
        StringBuilder builder = new StringBuilder();
        builder.append(progressId).append(" | ");
        if (MethodProgressLogTimes.FromStartAndLast.equals(methodProgressLogTimes) || MethodProgressLogTimes.FromStartOnly.equals(methodProgressLogTimes)) {
            builder.append(timer.getCreateId()).append("->");
            builder.append(progressId);
            builder.append(" (");
            builder.append(duration);
            builder.append(" ms)");
        }
        if (MethodProgressLogTimes.FromStartAndLast.equals(methodProgressLogTimes)) {
            if (timer.lastId != null) {
                builder.append(" , ");
                builder.append(timer.lastId);
                builder.append("->");
                builder.append(progressId);
                builder.append(" (");
                builder.append(nextDuration);
                builder.append(" ms)");
            }
        }
        if (msg != null) {
            builder.append(" | ").append(msg);
        }
        LogRecord record = new LogRecord(level, builder.toString());
        record.setSourceClassName(timer.className);
        record.setSourceMethodName(timer.methodName);
        record.setParameters(args);
        log(record);
        timer.setLastId(progressId);
        return timer;
    }

    /**
     * @param timer
     * @param progressId
     * @param msg
     * @param args
     * @return MethodTimer
     */
    public MethodTimer methodProgress(MethodTimer timer, String progressId, String msg, Object... args) {
        if (timer == null) {
            return null;
        }
        return methodProgress(timer.entryLevel, timer, MethodProgressLogTimes.FromStartAndLast, progressId, msg, args);
    }

    /**
     * Trace method progress with same level like entry
     * 
     * @param timer
     * @param methodProgressLogTimes
     * @param progressId
     * @param msg
     * @param args
     * @return MethodTimer
     */
    public MethodTimer methodProgress(MethodTimer timer, MethodProgressLogTimes methodProgressLogTimes, String progressId, String msg, Object... args) {
        if (timer == null) {
            return null;
        }
        return methodProgress(timer.entryLevel, timer, methodProgressLogTimes, progressId, msg, args);
    }

    /**
     * Trace method exit with same level like entry
     * 
     * @param timer
     * @return MethodTimer
     */
    public MethodTimer methodExit(MethodTimer timer) {
        if (timer == null) {
            return null;
        }
        return methodProgress(timer.entryLevel, timer, "Exit", null);
    }

    /**
     * Trace method exit with same level like entry with msg and args
     * 
     * @param timer
     * @param msg
     * @param args
     * @return MethodTimer
     */
    public MethodTimer methodExit(MethodTimer timer, String msg, Object... args) {
        if (timer == null) {
            return null;
        }
        return methodProgress(timer.entryLevel, timer, "Exit", msg, args);
    }

    private void inferCaller(LogRecord record) {
        String loggerName = getClass().getName();
        // Get the stack trace.
        StackTraceElement stack[] = Thread.currentThread().getStackTrace();
        // First, search back to a method in the Logger class.
        int ix = 0;
        while (ix < stack.length) {
            StackTraceElement frame = stack[ix];
            String cname = frame.getClassName();
            if (cname.equals(loggerName)) {
                break;
            }
            ix++;
        }
        // Now search for the first frame before the "Logger" class.
        while (ix < stack.length) {
            StackTraceElement frame = stack[ix];
            String cname = frame.getClassName();
            if (!cname.equals(loggerName)) {
                // We've found the relevant frame.
                record.setSourceClassName(cname);
                record.setSourceMethodName(frame.getMethodName());
                return;
            }
            ix++;
        }
        // We haven't found a suitable frame, so just punt. This is
        // OK as we are only committed to making a "best effort" here.
    }

    /**
     * Logs and throw exception
     * 
     * @param level
     * @param msg
     * @param throwable
     */
    public void logAndThrowRuntimeException(Level level, String msg, Throwable throwable) {
        super.log(level, msg, throwable);
        if (throwable instanceof RuntimeException) {
            throw (RuntimeException) throwable;
        }
        else {
            throw new VPDARuntimeException(msg, throwable);
        }
    }

    /**
     * Logs and throw exception
     * 
     * @param level
     * @param msg
     * @param throwable
     * @param re
     */
    public void logAndThrowRuntimeException(Level level, String msg, Throwable throwable, RuntimeException re) {
        super.log(level, msg, throwable);
        if (re != null) {
            throw re;
        }
        if (throwable instanceof RuntimeException) {
            throw (RuntimeException) throwable;
        }
        else {
            throw new VPDARuntimeException(msg, throwable);
        }
    }

    @Override
    public void log(LogRecord record) {
        if (!isLoggable(record.getLevel())) {
            return;
        }
        if (logToSysoutOnly) {
            String msg = PlainFormatter.getInstance().format(record);
            System.out.println(msg);
            return;
        }
        super.log(record);
    }

}
