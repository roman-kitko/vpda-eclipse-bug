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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;

import java.util.logging.Level;

import org.junit.jupiter.api.Test;

/**
 * @author kitko
 *
 */
public class LoggerMethodTracerTest {
    private static LoggerMethodTracer staticLogger = LoggerMethodTracer.getLogger(LoggerMethodTracerTest.class);

    /**
     * Test method for
     * {@link org.vpda.common.util.logging.LoggerMethodTracer#getLogger(java.lang.Class)}.
     */
    @Test
    public void testGetLoggerClass() {
        LoggerMethodTracer logger = LoggerMethodTracer.getLogger(LoggerMethodTracerTest.class);
        assertNotNull(logger);
        assertSame(logger, staticLogger);
    }

    /**
     * Test method for
     * {@link org.vpda.common.util.logging.LoggerMethodTracer#methodEntry(java.util.logging.Level)}.
     */
    @Test
    public void testMethodEntry() {
        staticLogger.setLevel(Level.INFO);
        MethodTimer timer = staticLogger.methodEntry(Level.FINER);
        assertNull(timer);
        staticLogger.setLevel(Level.FINER);
        timer = staticLogger.methodEntry(Level.FINER);
        assertNotNull(timer);
        assertEquals("testMethodEntry", timer.getMethodName());
        assertEquals(getClass().getName(), timer.getClassName());
    }

    /**
     * Test
     */
    @Test
    public void testMethodProgress() {
        staticLogger.setLevel(Level.INFO);
        MethodTimer timer = staticLogger.methodEntry(Level.INFO);
        staticLogger.methodProgress(Level.INFO, timer, "stamp1", null);
        staticLogger.methodProgress(Level.INFO, timer, "stamp2", null);
        staticLogger.methodExit(timer);

    }

    /**
     * Test simple tracing
     */
    @Test
    public void testMethodTraceSimple() {
        staticLogger.setLevel(Level.INFO);
        MethodTimer timer = staticLogger.methodEntry(Level.INFO);
        staticLogger.methodProgress(timer, "stamp1", null);
        staticLogger.methodProgress(timer, "stamp2", null);
        staticLogger.methodExit(timer);

    }

}
