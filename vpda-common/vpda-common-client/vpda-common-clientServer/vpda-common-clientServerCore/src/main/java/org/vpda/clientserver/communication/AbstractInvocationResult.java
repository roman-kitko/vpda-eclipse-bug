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
package org.vpda.clientserver.communication;

import java.io.Serializable;

/**
 * Result of invocation.
 * 
 * @author kitko
 *
 */
public abstract class AbstractInvocationResult implements InvocationResult, Serializable {
    private static final long serialVersionUID = 3834842890839559770L;
    private final InvocationResultType resultType;
    private final Object result;

    /**
     * @param resultType
     * @param result
     */
    protected AbstractInvocationResult(InvocationResultType resultType, Object result) {
        this.resultType = resultType;
        this.result = result;
    }

    /**
     * @return the resultType
     */
    @Override
    public InvocationResultType getResultType() {
        return resultType;
    }

    /**
     * @return the result
     */
    @Override
    public Object getResult() {
        return result;
    }

    @Override
    public String toString() {
        return "AbstractInvocationResult [resultType=" + resultType + ", result=" + result + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((this.result == null) ? 0 : this.result.hashCode());
        result = prime * result + ((resultType == null) ? 0 : resultType.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        AbstractInvocationResult other = (AbstractInvocationResult) obj;
        if (result == null) {
            if (other.result != null) {
                return false;
            }
        }
        else if (!result.equals(other.result)) {
            return false;
        }
        if (resultType != other.resultType) {
            return false;
        }
        return true;
    }

}