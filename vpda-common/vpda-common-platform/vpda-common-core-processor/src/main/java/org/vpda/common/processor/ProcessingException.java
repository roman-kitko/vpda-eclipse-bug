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
/**
 * 
 */
package org.vpda.common.processor;

import org.vpda.common.util.exceptions.AbstractTracedRuntimeException;

/**
 * Exception thrown when processing the class
 * 
 * @author kitko
 *
 */
public class ProcessingException extends AbstractTracedRuntimeException {

    private static final long serialVersionUID = 937656968630046143L;

    /**
     * @param message
     * @param cause
     */
    public ProcessingException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * @param message
     */
    public ProcessingException(String message) {
        super(message);
    }

    /**
     * @param cause
     */
    public ProcessingException(Throwable cause) {
        super(cause);
    }

}
