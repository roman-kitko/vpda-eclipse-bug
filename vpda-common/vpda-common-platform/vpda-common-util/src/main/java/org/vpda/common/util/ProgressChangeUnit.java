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
package org.vpda.common.util;

/**
 * How we want to express progress change
 * 
 * @author kitko
 *
 */
public enum ProgressChangeUnit {
    /** We say absolute number of progress. That will be new progress */
    ABSOLUTE {
        @Override
        public int computeNewAbsoluteProgress(int progress, int maxProgress, int currentProgress) {
            return progress;
        }

    },
    /**
     * Changing progress in relative manner, e.g from 10 to 15. We say 5 Relative
     */
    RELATIVE {
        @Override
        public int computeNewAbsoluteProgress(int progress, int maxProgress, int currentProgress) {
            return currentProgress + progress;
        }

    },
    /**
     * Change as percentage of max. If MAX is 100 and we say 15, means that progress
     * should change increase 15 points
     */
    PERCENTAGE_INCREASE_FROM_MAX {
        @Override
        public int computeNewAbsoluteProgress(int progress, int maxProgress, int currentProgress) {
            return currentProgress + maxProgress * 100 / progress;
        }

    };

    /**
     * @param progress
     * @param maxProgress
     * @param currentProgress
     * @return new absolute progress
     */
    public abstract int computeNewAbsoluteProgress(int progress, int maxProgress, int currentProgress);
}