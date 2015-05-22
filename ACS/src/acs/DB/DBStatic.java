/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package acs.DB;

import java.util.HashMap;

/**
 *
 * @author Thibault
 */
public class DBStatic
{
    public HashMap<String, Integer> db;
    
    public DBStatic()
    {
        db = new HashMap<>();
        db.put("cisco", "cisco".hashCode());
        db.put("cisco1", "cisco1".hashCode());
        db.put("cisco2", "cisco2".hashCode());
    }
}
