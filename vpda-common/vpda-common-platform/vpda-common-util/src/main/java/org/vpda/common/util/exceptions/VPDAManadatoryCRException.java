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
package org.vpda.common.util.exceptions;

/**
 * Exception thrown when some mandatory configuration element is missing
 * 
 * @author kitko
 *
 */
public class VPDAManadatoryCRException extends VPDAConfigurationRuntimeException {
    private static final long serialVersionUID = 6343726308902781499L;
    private String dataId;

    /**
     * @param dataId - missing data identifikator
     * 
     */
    public VPDAManadatoryCRException(String dataId) {
        this("Mandatory data are missing : " + dataId, dataId);
    }

    /**
     * @param dataId  - missing data identifikator
     * @param message
     */
    public VPDAManadatoryCRException(String message, String dataId) {
        super(message);
        this.dataId = dataId;
    }

    /**
     * @return dataId
     */
    public String getDataId() {
        return dataId;
    }

}
