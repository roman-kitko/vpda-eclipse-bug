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
package org.vpda.clientserver.viewprovider.list;

import org.vpda.clientserver.viewprovider.ViewProviderException;

/**
 * DataRetrieve encapsulate reading from some list provider. In this context
 * provider can be some server provider or any data source provider using this
 * understanding we can use this interface for both client server communication
 * and for some server Reading strategy implementations. This interface is
 * statefull, it remembers actuals client state
 * 
 * 
 * @author kitko
 *
 */
public interface ListViewProviderDataRetrieval {

    /**
     * Reads next data from provider
     * 
     * @param count - count of data client wants to fetch
     * @return ListViewResults of read data
     * @throws ViewProviderException when there is some exception when reading data
     */
    public ListViewResults readNext(int count) throws ViewProviderException;

    /**
     * Reads next data from provider
     * 
     * @param move  move numbers of rows (+,-) from current position
     * @param count - count of data client wants to fetch
     * @return ListViewResults of read data
     * @throws ViewProviderException when there is some exception when reading data
     */
    public ListViewResults readNext(int move, int count) throws ViewProviderException;

    /**
     * Reads first data from provider
     * 
     * @param count - count of data client wants to fetch
     * @return ListViewResults of read data
     * @throws ViewProviderException when there is some exception when reading data
     */
    public ListViewResults readFirst(int count) throws ViewProviderException;

    /**
     * Reads last data from provider
     * 
     * @param count - count of data client wants to fetch
     * @return ListViewResults of read data
     * @throws ViewProviderException when there is some exception when reading data
     */
    public ListViewResults readLast(int count) throws ViewProviderException;

    /**
     * Reads data starting at offset
     * 
     * @param count
     * @param offset
     * @return ListViewResults of read data
     * @throws ViewProviderException
     */
    public ListViewResults read(int count, int offset) throws ViewProviderException;

    /**
     * Reads count data, using offset from end
     * 
     * @param count
     * @param offset
     * @return ListViewResults of read data
     * @throws ViewProviderException
     */
    public ListViewResults readLast(int count, int offset) throws ViewProviderException;

    /**
     * Reads data starting at offset with either with offset from start or end
     * 
     * @param count
     * @param offset
     * @param readingFrom
     * @return ListViewResults of read data
     * @throws ViewProviderException
     */
    public ListViewResults read(int count, int offset, ReadingFrom readingFrom) throws ViewProviderException;

}
