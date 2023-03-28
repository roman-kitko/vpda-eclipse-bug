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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.TemporalAccessor;
import java.util.ArrayList;
import java.util.List;

import org.vpda.common.util.ScmCommitInfo;
import org.vpda.common.util.exceptions.VPDARuntimeException;

final class ScmLogFileUtil {

    private static final DateTimeFormatterBuilder formatterBuilder = new DateTimeFormatterBuilder().appendPattern("yyyy-MM-dd HH:mm:ss XXXX");
    private static final DateTimeFormatter formatter = formatterBuilder.toFormatter();

    private ScmLogFileUtil() {
    }

    /**
     * @param url
     * @return last commit
     */
    static ScmCommitInfo getLastCommitFromScmLogFile(URL url) {
        try (InputStream stream = url.openStream()) {
            BufferedReader bfr = new BufferedReader(new InputStreamReader(stream));
            String line = bfr.readLine();
            if (line == null) {
                return null;
            }
            ScmCommitInfo.ScmCommitInfoBuilder builder = new ScmCommitInfo.ScmCommitInfoBuilder();
            fillBuilder(builder, line);
            ScmCommitInfo info = builder.build();
            return info;

        }
        catch (IOException e) {
            throw new VPDARuntimeException("Cannot load commit log file", e);
        }
    }

    /**
     * @param url
     * @return last commits
     */
    static List<ScmCommitInfo> getLastCommitsFromSCMLogFile(URL url) {
        List<ScmCommitInfo> result = new ArrayList<ScmCommitInfo>(50);
        try (InputStream stream = url.openStream()) {
            BufferedReader bfr = new BufferedReader(new InputStreamReader(stream));
            String line = null;
            ScmCommitInfo.ScmCommitInfoBuilder builder = new ScmCommitInfo.ScmCommitInfoBuilder();
            while ((line = bfr.readLine()) != null) {
                fillBuilder(builder, line);
                ScmCommitInfo info = builder.build();
                result.add(info);
            }
            return result;
        }
        catch (IOException e) {
            throw new VPDARuntimeException("Cannot load commit log file", e);
        }
    }

    private static void fillBuilder(ScmCommitInfo.ScmCommitInfoBuilder builder, String line) {
        String[] parts = line.split("\\|");
        TemporalAccessor parsed = formatter.parse(parts[2]);
        ZonedDateTime dt = ZonedDateTime.from(parsed);
        builder.setId(parts[0]).setAuthor(parts[1]).setTimestamp(dt).setMsg(parts[3]);
    }

}
