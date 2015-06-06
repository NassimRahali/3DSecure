/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package acs.DB;

/**
 *
 * @author nsm
 */
public class Test {
    public static void main(String[] args) {
        DBStatic DB = new DBStatic();
        System.out.println(DB.db.containsKey(123456789));
    }
}
