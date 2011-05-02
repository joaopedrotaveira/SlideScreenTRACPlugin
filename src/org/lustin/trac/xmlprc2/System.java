/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.lustin.trac.xmlprc2;
import org.json.simple.JSONArray;

/**
 *
 * @author lustin
 */
public interface System {

    /**
     * @return a list of strings, one for each (non-system) method supported by the XML-RPC server.
     */
    JSONArray listMethods();
    
    /**
     * Returns a list with three elements. First element is the epoch (0=Trac 0.10, 1=Trac 0.11 or higher). 
     * Second element is the major version number, third is the minor. 
     * Changes to the major version indicate API breaking changes, while minor version changes are simple additions, bug fixes, etc.
     * 
     * @return a list of strings
     */
    JSONArray getAPIVersion();
    JSONArray multicall(JSONArray signatures);
    String methodHelp(String method);
    JSONArray methodSignature(String method);
    
}
