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

import java.io.IOException;
import java.time.Instant;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.LogCommand;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.vpda.common.util.ScmCommitInfo;
import org.vpda.common.util.exceptions.VPDARuntimeException;

final class SCMJGitUtil {

    private static final ZoneId UTC_ZONE = ZoneId.of("UTC");

    private SCMJGitUtil() {
    }

    /**
     * 
     * @return last one commit from git
     */
    static ScmCommitInfo getLastCommitFromJGit() {
        try (Repository repo = getRepository()) {
            try (Git git = new Git(repo)) {
                LogCommand logCmd = git.log();
                try {
                    Iterator<RevCommit> commits = logCmd.setMaxCount(1).call().iterator();
                    if (commits.hasNext()) {
                        RevCommit gitCommit = commits.next();
                        return fromRevCommitToScmCommitInfo(gitCommit);
                    }
                }
                catch (GitAPIException e) {
                    throw new VPDARuntimeException("Cannot get git log", e);
                }
            }
        }
        catch (IOException e) {
            throw new VPDARuntimeException("Cannot get git log", e);
        }
        return null;
    }

    /**
     * @param size
     * @return last n commits from git
     */
    static List<ScmCommitInfo> getLastCommitsFromJGit(int size) {
        try (Repository repo = getRepository()) {
            try (Git git = new Git(repo)) {
                LogCommand logCmd = git.log();
                try {
                    List<ScmCommitInfo> infos = new ArrayList<>();
                    for (Iterator<RevCommit> commits = logCmd.setMaxCount(size).call().iterator(); commits.hasNext();) {
                        RevCommit gitCommit = commits.next();
                        ScmCommitInfo info = fromRevCommitToScmCommitInfo(gitCommit);
                        infos.add(info);
                    }
                    return infos;
                }
                catch (GitAPIException e) {
                    throw new VPDARuntimeException("Cannot get git log", e);
                }
            }
        }
        catch (IOException e) {
            throw new VPDARuntimeException("Cannot get git log", e);
        }
    }

    private static Repository getRepository() throws IOException {
        Repository repo = new FileRepositoryBuilder().readEnvironment().findGitDir().build();

        return repo;
    }

    private static ScmCommitInfo fromRevCommitToScmCommitInfo(RevCommit gitCommit) {
        ScmCommitInfo.ScmCommitInfoBuilder builder = new ScmCommitInfo.ScmCommitInfoBuilder();
        builder.setAuthor(gitCommit.getAuthorIdent().getName());
        builder.setId(gitCommit.getId().name());
        builder.setMsg(gitCommit.getShortMessage());
        Instant instant = Instant.ofEpochSecond(gitCommit.getCommitTime());
        builder.setTimestamp(instant.atZone(UTC_ZONE));
        return builder.build();
    }

}
