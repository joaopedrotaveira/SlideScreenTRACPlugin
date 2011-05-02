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
public interface Search {
    JSONArray getSearchFilters();
    JSONArray performSearch(String query);
    JSONArray performSearch(String query, JSONArray filters);
}
