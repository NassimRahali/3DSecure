/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ds;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author nsm
 */
public class MySQLHelper {

    Connection conn = null;

    public MySQLHelper() throws ClassNotFoundException {
    
    }

    public Connection getDSConnection() throws ClassNotFoundException {
        try {
            String url = "jdbc:mysql://localhost:3306/DB_DS";

            conn = DriverManager.getConnection(url, "root", "");
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
        return conn;
    }

}
