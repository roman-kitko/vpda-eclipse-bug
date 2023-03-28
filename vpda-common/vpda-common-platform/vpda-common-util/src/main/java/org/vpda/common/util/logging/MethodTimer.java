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

/**
 * Representation of timer for method.
 * 
 * @author kitko
 *
 */
public final class MethodTimer {
    /** Name of class we measure */
    final String className;
    /** Name of method we measure */
    final String methodName;
    /** Reference to stop watch */
    final StopWatch stopWatch;
    /** log level of entry to method */
    final Level entryLevel;
    /** Id , that this timer was created for */
    final String createId;
    /** Remember last push id */
    String lastId;

    /**
     * Creates Timer
     * 
     * @param entryLevel
     * @param className
     * @param methodName
     * @param createId
     */
    MethodTimer(Level entryLevel, String className, String methodName, String createId) {
        super();
        this.entryLevel = entryLevel;
        this.className = className;
        this.methodName = methodName;
        this.stopWatch = new StopWatch();
        this.createId = createId;
    }

    /**
     * @return the className
     */
    public String getClassName() {
        return className;
    }

    /**
     * @return the methodName
     */
    public String getMethodName() {
        return methodName;
    }

    /**
     * @return the stopWatch
     */
    StopWatch getStopWatch() {
        return stopWatch;
    }

    /**
     * @return the lastId
     */
    public String getLastId() {
        return lastId;
    }

    /**
     * @param lastId the lastId to set
     */
    public void setLastId(String lastId) {
        this.lastId = lastId;
    }

    /**
     * @return the entryLevel
     */
    public Level getEntryLevel() {
        return entryLevel;
    }

    /**
     * @return the createId
     */
    public String getCreateId() {
        return createId;
    }

}
