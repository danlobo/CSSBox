/**
 * DOMSource.java
 * Copyright (c) 2005-2007 Radek Burget
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
 * Created on 22.11.2012, 19:01:10 by burgetr
 */

package org.fit.cssbox.io;

import java.io.IOException;
import java.net.URL;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

/**
 * An abstraction of a parser that is able to obtain a DOM.
 * 
 * @author burgetr
 */
public abstract class DOMSource
{
    protected DocumentDataSource src;
    protected String charset;

    public DOMSource(DocumentDataSource src)
    {
        this.src = src;
//TODO see where it fits
//        setContentType(src.getContentType());
    }

    public DocumentDataSource getDocumentSource()
    {
        return src;
    }

    public String getCharset()
    {
        return charset;
    }

    public void setContentType(String type)
    {
        if (type != null)
        {
            String t = type.toLowerCase();

            //extract the charset if specified
            int strt = t.indexOf("charset=");
            if (strt >= 0)
            {
                strt += "charset=".length();
                int stop = t.indexOf(';', strt);
                if (stop == -1)
                    stop = t.length();
                charset = t.substring(strt, stop).trim();
                charset = charset.replaceAll("^\"|\"$|^\'|\'$", "").trim();
            }
        }
    }

    abstract public Document parse(URL url) throws SAXException, IOException;
    
}
