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
    public HashMap<Integer, Integer> db;
    
    public DBStatic()
    {
        db = new HashMap<>();
        db.put(123456789, 123456789);
    }
}
