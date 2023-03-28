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

import java.util.logging.Filter;
import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.LogRecord;
import java.util.logging.SimpleFormatter;
import java.util.logging.StreamHandler;

/**
 * Handler to System.out. It is referenced from logging.properties configuration
 * file
 * 
 * @author kitko
 *
 */
public final class SystemOutHandler extends StreamHandler {

    private void configure() {
        LogManager manager = LogManager.getLogManager();
        String cname = getClass().getName();

        setLevel(getLevelProperty(manager, cname + ".level", Level.INFO));
        setFilter(getFilterProperty(manager, cname + ".filter", null));
        setFormatter(getFormatterProperty(manager, cname + ".formatter", new SimpleFormatter()));
        try {
            setEncoding(getStringProperty(manager, cname + ".encoding", null));
        }
        catch (Exception ex) {
            try {
                setEncoding(null);
            }
            catch (Exception ex2) {
                // doing a setEncoding with null should always work.
                // assert false;
            }
        }
    }

    /**
     * Creates handler
     */
    public SystemOutHandler() {
        configure();
        setOutputStream(System.out);

    }

    private String getStringProperty(LogManager manager, String name, String defaultValue) {
        String val = manager.getProperty(name);
        if (val == null) {
            return defaultValue;
        }
        return val.trim();
    }

    private Level getLevelProperty(LogManager manager, String name, Level defaultValue) {
        String val = manager.getProperty(name);
        if (val == null) {
            return defaultValue;
        }
        try {
            return Level.parse(val.trim());
        }
        catch (Exception ex) {
            return defaultValue;
        }
    }

    private Filter getFilterProperty(LogManager manager, String name, Filter defaultValue) {
        String val = manager.getProperty(name);
        try {
            if (val != null) {
                Class clz = ClassLoader.getSystemClassLoader().loadClass(val);
                return (Filter) clz.newInstance();
            }
        }
        catch (Exception ex) {
            // We got one of a variety of exceptions in creating the
            // class or creating an instance.
            // Drop through.
        }
        // We got an exception. Return the defaultValue.
        return defaultValue;
    }

    private Formatter getFormatterProperty(LogManager manager, String name, Formatter defaultValue) {
        String val = manager.getProperty(name);
        try {
            if (val != null) {
                Class clz = ClassLoader.getSystemClassLoader().loadClass(val);
                return (Formatter) clz.newInstance();
            }
        }
        catch (Exception ex) {
            // We got one of a variety of exceptions in creating the
            // class or creating an instance.
            // Drop through.
        }
        // We got an exception. Return the defaultValue.
        return defaultValue;
    }

    @Override
    public synchronized void publish(LogRecord record) {
        super.publish(record);
        flush();
    }

}
