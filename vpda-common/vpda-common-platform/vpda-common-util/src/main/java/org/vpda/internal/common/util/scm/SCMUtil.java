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
package org.vpda.internal.common.util.scm;

import java.net.URL;
import java.util.List;

import org.vpda.common.util.ScmCommitInfo;

/**
 * Will get last commits
 * 
 * @author kitko
 *
 */
public final class SCMUtil {

    private SCMUtil() {
    }

    /**
     * @return last commits
     */
    public static List<ScmCommitInfo> getLastCommits() {
        URL url = Thread.currentThread().getContextClassLoader().getResource("META-INF/scmLog.txt");
        if (url != null) {
            return ScmLogFileUtil.getLastCommitsFromSCMLogFile(url);
        }
        return SCMJGitUtil.getLastCommitsFromJGit(100);
    }

    /**
     * @return last commit info
     */
    public static ScmCommitInfo getLastCommit() {
        URL url = Thread.currentThread().getContextClassLoader().getResource("META-INF/scmLog.txt");
        if (url != null) {
            return ScmLogFileUtil.getLastCommitFromScmLogFile(url);
        }
        return SCMJGitUtil.getLastCommitFromJGit();
    }

}
