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
package org.vpda.internal.common.util;

/**
 * Helper pair of two object. Many times can be usefull in results or keys to
 * map
 * 
 * @author kitko
 *
 * @param <F>
 * @param <S>
 */
public final class Pair<F, S> {
    private final F first;
    private final S second;

    /**
     * @param first
     * @param second
     */
    public Pair(F first, S second) {
        super();
        this.first = first;
        this.second = second;
    }

    /**
     * Static creator
     * 
     * @param first
     * @param second
     * @return pair
     */
    public static <F, S> Pair<F, S> create(F first, S second) {
        return new Pair<F, S>(first, second);
    }

    /**
     * @return the first
     */
    public F getFirst() {
        return first;
    }

    /**
     * @return the second
     */
    public S getSecond() {
        return second;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((first == null) ? 0 : first.hashCode());
        result = prime * result + ((second == null) ? 0 : second.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Pair other = (Pair) obj;
        if (first == null) {
            if (other.first != null)
                return false;
        }
        else if (!first.equals(other.first))
            return false;
        if (second == null) {
            if (other.second != null)
                return false;
        }
        else if (!second.equals(other.second))
            return false;
        return true;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Pair [first=");
        builder.append(first);
        builder.append(", second=");
        builder.append(second);
        builder.append("]");
        return builder.toString();
    }

}
