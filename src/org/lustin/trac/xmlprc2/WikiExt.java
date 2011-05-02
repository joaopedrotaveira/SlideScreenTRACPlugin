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
public interface WikiExt {
    Wiki      getPageVersions(String pagename); //Return an array of page versions info  	WIKI_VIEW
    Boolean   hasChildren(String pagename); 	//Returns true if the page has children. 	WIKI_VIEW
    Vector<Object>    getChildren(String pagename); 	//Returns a list of all pages. The result is an array of utf8 pagenames. 	WIKI_VIEW
    Hashtable<String,Object> getMacros();                      //Return the list of registered wiki macros 	WIKI_VIEW
}
