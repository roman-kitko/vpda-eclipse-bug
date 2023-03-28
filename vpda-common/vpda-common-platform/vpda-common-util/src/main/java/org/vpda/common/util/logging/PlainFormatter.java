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

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.LogRecord;

/**
 * Plain simple formatter. It is referenced from logging.properties
 * configuration file
 * 
 * @author kitko
 *
 */
public final class PlainFormatter extends java.util.logging.Formatter {

    private static final PlainFormatter INSTANCE = new PlainFormatter();
    private static ThreadLocal<DateFormat> dateFormatter = new ThreadLocal<DateFormat>() {

        @Override
        protected DateFormat initialValue() {
            return new SimpleDateFormat("yyyy.MM.dd HH:mm:ss,SSS");
        }
    };

    /**
     * @return instance
     */
    public static PlainFormatter getInstance() {
        return INSTANCE;
    }

    /**
     * Creates formatter. Need that for configuring
     */
    public PlainFormatter() {
    }

    @Override
    public String format(LogRecord record) {
        StringBuilder msg = new StringBuilder();
        msg.append(dateFormatter.get().format(new Date()));
        msg.append(" ");
        msg.append(record.getLevel());
        msg.append(" ");
        msg.append(record.getLongThreadID());
        msg.append(" ");
        msg.append(record.getSourceClassName());
        msg.append('.');
        msg.append(record.getSourceMethodName());
        msg.append(": ");
        if (record.getMessage() == null) {
            record.setMessage("");
        }
        msg.append(formatMessage(record));
        msg.append("\n");
        if (record.getThrown() != null)
            msg.append(getStackTrace(record.getThrown()));

        return msg.toString();
    }

    /**
     * @param theThrown
     * @return String stackTrace
     */
    private String getStackTrace(Throwable theThrown) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        theThrown.printStackTrace(pw);
        pw.close();
        return sw.getBuffer().toString();
    }

}
