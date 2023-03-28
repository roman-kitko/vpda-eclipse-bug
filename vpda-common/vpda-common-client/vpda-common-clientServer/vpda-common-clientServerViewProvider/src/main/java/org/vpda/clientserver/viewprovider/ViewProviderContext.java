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
package org.vpda.clientserver.viewprovider;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;

/**
 * Stack of view provider callers
 * 
 * @author kitko
 *
 */
public final class ViewProviderContext implements Serializable {
    private static final long serialVersionUID = 7667835719638056567L;
    private static ViewProviderContext emptyContext = new ViewProviderContext();
    private final List<ViewProviderCallerItem> callers;

    /**
     * Empty context method creator
     * 
     * @return empty context
     */
    public static ViewProviderContext emptyContext() {
        return emptyContext;
    }

    /**
     * @return all callers
     */
    List<ViewProviderCallerItem> getCallers() {
        return Collections.unmodifiableList(callers);
    }

    /**
     * Add one item to this stack context
     * 
     * @param caller
     */
    public ViewProviderContext(ViewProviderCallerItem caller) {
        callers = new ArrayList<ViewProviderCallerItem>(1);
        callers.add(caller);
    }

    /**
     * Add stack from paren and new id
     * 
     * @param parent
     * @param caller
     */
    public ViewProviderContext(ViewProviderContext parent, ViewProviderCallerItem caller) {
        callers = new ArrayList<>(3);
        if (parent != null) {
            callers.addAll(parent.getCallers());
        }
        callers.add(caller);
    }

    /**
     * Add all items from stack to this context
     * 
     * @param stack
     */
    public ViewProviderContext(List<ViewProviderCallerItem> stack) {
        callers = new ArrayList<ViewProviderCallerItem>(stack);
    }

    /*
     * Constructor for empty context Use emptyContext method creator
     */
    private ViewProviderContext() {
        callers = Collections.emptyList();
    }

    /**
     * @return last caller
     */
    public ViewProviderCallerItem getLastCaller() {
        if (callers.isEmpty()) {
            return null;
        }
        return callers.get(callers.size() - 1);
    }

    /**
     * @return last caller
     */
    public ViewProviderCallerItem getLastButOneCaller() {
        if (callers.size() <= 1) {
            return null;
        }
        return callers.get(callers.size() - 2);
    }

    /**
     * @return first caller
     */
    public ViewProviderCallerItem getFirstCaller() {
        if (callers.isEmpty()) {
            return null;
        }
        return callers.get(0);
    }

    /**
     * @return second caller
     */
    public ViewProviderCallerItem getSecondCaller() {
        if (callers.size() <= 1) {
            return null;
        }
        return callers.get(1);
    }

    /**
     * @return true if empty
     */
    public boolean isEmpty() {
        return callers.isEmpty();
    }

    /**
     * 
     * @param item
     * @return previous item of passed item
     */
    public ViewProviderCallerItem getPreviousItem(ViewProviderCallerItem item) {
        for (ListIterator<ViewProviderCallerItem> it = callers.listIterator(); it.hasNext();) {
            if (item == it.next()) {
                if (it.hasPrevious()) {
                    return it.previous();
                }
            }
        }
        return null;
    }

    /**
     * 
     * @param item
     * @return next item of passed item
     */
    public ViewProviderCallerItem getNextItem(ViewProviderCallerItem item) {
        for (ListIterator<ViewProviderCallerItem> it = callers.listIterator(); it.hasNext();) {
            if (item == it.next()) {
                if (it.hasNext()) {
                    return it.next();
                }
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return "ViewProviderContext [callers=" + callers + "]";
    }

}
