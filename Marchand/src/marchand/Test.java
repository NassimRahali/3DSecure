///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package marchand;
//
//import java.sql.Connection;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.sql.Statement;
//import java.util.ArrayList;
//import java.util.List;
//
///**
// *
// * @author nsm
// */
//public class Test {
//    public static void main(String[] args) throws SQLException, ClassNotFoundException {
//        
//      String query = "SELECT * FROM produits";
//      Connection con = new MySQLHelper().getMarchandConnection();
//      Statement stmt = con.createStatement();
//      ResultSet rs = stmt.executeQuery(query);
//      List<Produit> ret = new ArrayList();
//
//      while (rs.next())
//      {
//        ret.add(new Produit(rs.getString(2), rs.getFloat(3)));
//      }
//      
//      ret.forEach( produit -> {
//          System.out.println(produit.getNom() + " " + produit.getPrix());
//      });
//      con.close();
//    }
//}
