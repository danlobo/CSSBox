/*
 * DefaultDocumentSource.java
 * Copyright (c) 2005-2012 Radek Burget
 *
 * CSSBox is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *  
 * CSSBox is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 *  
 * You should have received a copy of the GNU Lesser General Public License
 * along with CSSBox. If not, see <http://www.gnu.org/licenses/>.
 *
 * Created on 23.11.2012, 9:28:33 by burgetr
 */
package org.fit.cssbox.io;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * This class implements the document source with the standard {@link java.net.URLConnection}
 * with an additional support for the data: URLs.
 *  
 * @author burgetr
 */
public class DefaultDocumentDataSource extends DocumentDataSource
{
    /** The user-agent string used for HTTP connection */
    private static String USER_AGENT = "Mozilla/5.0 (compatible; BoxBrowserTest/4.x; Linux) CSSBox/4.x (like Gecko)";
    /**
     * Creates a network data source
     */
    public DefaultDocumentDataSource()
    {
    }

    /**
     * Obtains the InputStream for the given URL
     * @param url the URL
     * @return the URL data stream
     * @throws IOException
     */
    @Override
    public InputStream getInputStreamFor(URL url) throws IOException
    {
        URLConnection con = url.openConnection();
        con.setRequestProperty("User-Agent", USER_AGENT);
        return con.getInputStream();
    }
    
    /**
     * Obtains the current User-agent string used for new connections.
     * @return the user-agent string.
     */
    public static String getUserAgent()
    {
        return USER_AGENT;
    }

    /**
     * Sets the user agent string that will be used for new connections.
     * @param userAgent the user-agent string
     */
    public static void setUserAgent(String userAgent)
    {
        USER_AGENT = userAgent;
    }

    @Override
    public void close() throws IOException
    {
    }
}
