/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.lustin.trac.xmlprc2;

/**
 *
 * @author lustin
 */
import java.util.Hashtable;
import java.util.Map;
import java.util.Vector;

import org.json.simple.JSONArray;

public interface Ticket

{
    public interface TicketProperty{
        JSONArray getAll();
        Map<String, Object> Get(String name);
    }
    public interface Milestone  extends TicketProperty {    }
    public interface Severity   extends TicketProperty {    }
    public interface Type       extends TicketProperty {    }
    public interface Resolution  extends TicketProperty {    }
    public interface Priority   extends TicketProperty {    }
    public interface Component  extends TicketProperty {    }
    public interface Version    extends TicketProperty {    }
    public interface Status     extends TicketProperty {    }

    JSONArray query() throws TracException; // qstr="status!=closed"
    JSONArray query(String qstr) throws TracException;
    
    Integer delete(Integer id);
    
    Integer create( String summary, String description);
    Integer create( String summary, String description, Hashtable<String,Object> attribute);
    Integer create( String summary, String description, Hashtable<String,Object> attribute, Boolean notify);
    
    JSONArray get(Long id);
  
    Vector<Object> update(Integer id, String comment);
    Vector<Object> update(Integer id, String comment, Hashtable<String,Object> attributes);
    Vector<Object> update(Integer id, String comment, Hashtable<String,Object> attributes, Boolean notify);
    
    Hashtable<String,Object> changeLog(Integer id);
    Hashtable<String,Object> changeLog(Integer id, Integer when);
    
    Vector<Object> listAttachments(Integer ticket);
    
    byte[] getAttachment(Integer ticket, String filename);
    
    String putAttachment(Integer ticket, String filename, String description, byte[] data);
    String putAttachment(Integer ticket, String filename, String description, byte[] data, Boolean replace);
    
    Boolean deleteAttachment(Integer ticket, String filename);
    
    Object[] getTicketFields();
}

