/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.lustin.trac.xmlprc2;

import java.util.Hashtable;
import java.util.Vector;

/**
 *
 * @author lustin
 */
public interface TicketExt {
    Vector<Object> update(Integer id, String author, String comment, Hashtable<String, Object> attributes);
}
