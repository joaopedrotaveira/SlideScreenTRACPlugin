/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.lustin.trac.xmlprc2;

import java.util.Date;
import java.util.Hashtable;
import java.util.Vector;

/**
 *
 * @author lustin
 */
public interface Wiki {
    Hashtable<String,Object> getRecentChanges(Date since);
    
    Integer getRPCVersionSupported();
    
    String getPage(String pagename);
    String getPage(String pagename, Integer version);
    
    String getPageVersion(String pagename);
    String getPageVersion(String pagename, Integer version);
    
    String getPageHTML(String pagename);
    String getPageHTML(String pagename, Integer version);
    
    String getPageHTMLVersion(String pagename);
    String getPageHTMLVersion(String pagename, Integer version);
            
    Vector<Object> getAllPages();
    
    Hashtable<String,Object> getPageInfo(String pagename);
    Hashtable<String,Object> getPageInfo(String pagename, Integer version);
    
    Hashtable<String,Object> getPageInfoVersion(String pagename);
    Hashtable<String,Object> getPageInfoVersion(String pagename, Integer version);
            
    Boolean putPage(String pagename, String content, Hashtable<String,Object> attributes);
    
    Hashtable<String,Object> istAttachments(String pagename);
    
    byte[] getAttachment(String path);
    
    Boolean putAttachment(String path, byte[] data);
    
    Boolean putAttachmentEx(String pagename, String filename, String description, byte[] data);
    Boolean putAttachmentEx(String pagename, String filename, String description, byte[] data, boolean replace);
    
    Boolean deletePage(String name);
    Boolean deletePage(String name, int version);
    
    Boolean deleteAttachment(String path);
    
    Vector<Object> listLinks(String pagename);
    String wikiToHtml(String text);
    
}
