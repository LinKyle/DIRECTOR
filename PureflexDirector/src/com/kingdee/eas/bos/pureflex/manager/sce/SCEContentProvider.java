/* 
 * Licensed Materials - Property of IBM 
 * 
 * OCO Source Materials 
 * 
 * (C) Copyright IBM Corp. 2013 All Rights Reserved 
 * 
 * The source code for this program is not published or other- 
 * wise divested of its trade secrets, irrespective of what has 
 * been deposited with the U.S. Copyright Office. 
 */ 
package com.kingdee.eas.bos.pureflex.manager.sce;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

import org.apache.http.entity.ContentProducer;

/**
 * @author Jun Hong Li
 *
 */
public class SCEContentProvider implements ContentProducer {

    private String content;
    
    /**
     * @param content
     */
    public SCEContentProvider(String content) {
        this.content = content;
    }
    
    /* (non-Javadoc)
     * @see org.apache.http.entity.ContentProducer#writeTo(java.io.OutputStream)
     */
    public void writeTo(OutputStream outstream) throws IOException {
        Writer writer = new OutputStreamWriter(outstream, "UTF-8"); //$NON-NLS-1$
        writer.write(content);
        writer.flush();
    }
}