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

/**
 * Stop watch - class for measuring duration of any process
 * 
 * @author kitko
 *
 */
final class StopWatch {
    private long startTime;
    private long splitTime;
    /** Any data assciated with this watch - e.g owner */
    private final Object data;

    /** Constant for time separator in stopWatch */
    private final static String STOP_WATCH_TIME_SEPARATOR = ":";

    /**
     * Constructor for starting stopWatch
     */
    StopWatch() {
        this(null);
    }

    /**
     * Constructor for starting stopWatch with data
     * 
     * @param data
     */
    StopWatch(Object data) {
        this.data = data;
        startTime = System.currentTimeMillis();
        splitTime = startTime;
    }

    /**
     * Returns total duration from start
     * 
     * @return duration in milis from start
     */
    long duration() {
        return System.currentTimeMillis() - startTime;
    }

    /**
     * Returns duration string
     * 
     * @param time
     * @param separator
     * @return duration string
     */
    static String formatDuration(long time, String separator) {
        if (time == 0) {
            return "00" + separator + "00" + separator + "00" + separator + "000";
        }
        long hour = time / 3600000;
        long hourMs = hour * 3600000;
        long min = (time - hourMs) / 60000;
        long minMs = min * 60000 + hourMs;
        long sec = (time - minMs) / 1000;
        long secMs = sec * 1000 + minMs;
        long ms = (time - secMs);
        StringBuilder sb = new StringBuilder(10);
        char zero = '0';
        if (hour < 10) {
            sb.append(zero);
        }
        sb.append(hour);
        sb.append(separator);
        if (min < 10) {
            sb.append(zero);
        }
        sb.append(min);
        sb.append(separator);
        if (sec < 10) {
            sb.append(zero);
        }
        sb.append(sec);
        if (ms < 10) {
            sb.append(zero);
        }
        if (ms < 10) {
            sb.append(zero);
        }
        sb.append(ms);
        return sb.toString();
    }

    /**
     * Returns duration string for current duration
     * 
     * @param separator
     * @return duration string
     */
    String durationString(String separator) {
        return formatDuration(duration(), separator);
    }

    /**
     * 
     * @return duration string with default separator
     */
    String durationString() {
        return durationString(STOP_WATCH_TIME_SEPARATOR);
    }

    /**
     * Getter for startTime
     * 
     * @return startTime
     */
    long getStartTime() {
        return startTime;
    }

    /**
     * Measures split duration
     * 
     * @return split duration
     */
    long nextDuration() {
        long current = System.currentTimeMillis();
        long tmp = current - splitTime;
        splitTime = current;
        return tmp;
    }

    /**
     * Restart watch
     */
    void restart() {
        startTime = System.currentTimeMillis();
        splitTime = startTime;
    }

    /**
     * @return the data
     */
    Object getData() {
        return data;
    }

}
