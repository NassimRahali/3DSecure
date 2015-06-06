/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ds;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author nsm
 */
public class Test {

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        String query = "SELECT * FROM banques b, cartes c "
                + " WHERE b.bank_id = c.bank_fk and c.card_id = 123456789";
        Statement stmt = new MySQLHelper().getDSConnection().createStatement();
        ResultSet rs = stmt.executeQuery(query);

        while (rs.next()) {
            System.out.println(rs.getInt(1) + " | "
                    + rs.getString(2) + " | "
                    + rs.getString(3) + " | "
                    + rs.getString(4) + " | "
                    + rs.getString(5));
        }
        stmt.getConnection().close();
    }
}
