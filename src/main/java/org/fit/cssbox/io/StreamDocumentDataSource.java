/**
 * StreamDocumentSource.java
 *
 * Created on 19.12.2012, 15:07:56 by burgetr
 */
package org.fit.cssbox.io;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * A dummy implementation of the document source for encapsulating an already created input stream
 * and the additional parametres.
 * 
 * @author burgetr
 */
public class StreamDocumentDataSource extends DocumentDataSource
{
    private InputStream is;
    
    /**
     * Creates the document source from the input stream.
     * @param is the input stream
     * @throws IOException
     */
    public StreamDocumentDataSource(InputStream is) throws IOException
    {
        this.is = is;
    }

    @Override
    public InputStream getInputStreamFor(URL url) throws IOException
    {
        return is;
    }

    @Override
    public void close() throws IOException
    {
        is.close();
    }
}
