/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ds;

import DS.ReqDS;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import SSL.SSL;
import VER.VERP;
import VER.VERQ;
import java.sql.Connection;
import javax.net.ssl.SSLSocket;

/**
 *
 * @author nsm
 */
public class DSTask implements Runnable{
    
    private final Socket cSock;
    private SSLSocket bankSock;
    private ObjectOutputStream oos;
    private ObjectInputStream ois;
    private final Boolean endConnection = false;
    private String bankAddr;
    private int bankPort;
    private String bankName;
    
    public DSTask(Socket cs) {
        this.cSock = cs;
    }

    @Override
    public void run() {
        try {
            oos = new ObjectOutputStream(cSock.getOutputStream());
            oos.flush();
            ois = new ObjectInputStream(cSock.getInputStream());
            if (oos == null || ois == null) {
                System.err.println("Failed to load streams.");
                System.out.println("System will exit.");
                System.exit(-1);
            }
            System.out.println("Streams created.");
            while (!endConnection) {
                ReqDS reqds = (ReqDS) ois.readObject();
                ReqDS repds;
                switch (reqds.getType()) {
                    case ReqDS.VERIF:
                        boolean cardOk = verifyCard(reqds);
                        if(cardOk) {
                            contactBank(reqds, this.bankAddr, this.bankPort);
                        }
                        else {
                            repds = new ReqDS();
                            repds.setStatus(-1);
                            oos.writeObject(repds);
                        }
                        break;
                    default:
                        break;
                }

            }
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(ReqDS.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(DSTask.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private boolean verifyCard(ReqDS reqds) throws ClassNotFoundException, SQLException {
        long number = reqds.getCardNumber();
        boolean cardOk = false;
        String query = "SELECT * FROM banques b, cartes c " + 
          " WHERE b.bank_id = c.bank_fk and c.card_id = " + number;
        
        int cpt;
        try (Connection con = new MySQLHelper().getDSConnection()) {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            cpt = 0;
            while (rs.next())
            {
                System.out.println(rs.getInt(1) + " | "
                        + rs.getString(2) + " | "
                        + rs.getString(3) + " | "
                        + rs.getString(4) + " | ");
                this.bankName = rs.getString(2);
                this.bankAddr = rs.getString(3);
                this.bankPort = rs.getInt(4);
                cpt++;
            }
        }
        cardOk = cpt == 1;
        if(cardOk) {
            System.out.println("DS server was able to find the card... OK");
        } else {
            System.out.println("DS server was not able to find the card... NOT OK");
        }
        return cardOk;
    }

    private void contactBank(ReqDS reqds, String bankAddr, int bankPort) throws IOException {
        System.out.println("Attempting to contact bank " + this.bankName);
        
        bankSock = SSL.getSSLSocket(bankAddr, bankPort);
        ObjectInputStream ois_bank = new ObjectInputStream(bankSock.getInputStream());
        ObjectOutputStream oos_bank = new ObjectOutputStream(bankSock.getOutputStream());
        
        System.out.println("Streams created.");
        System.out.println("Creating a VERQ with ");
        System.out.println("\tCard number = " + reqds.getCardNumber());
        System.out.println("\tCard owner = " + reqds.getCardOwner());
        
        VERQ bankVERQ = new VERQ(reqds.getCardNumber(), reqds.getCardOwner());
        
        oos_bank.writeObject(bankVERQ);
        
        System.out.println("VERQ sent...");
        
        VERP bankVERP = null;
        try {
            System.out.println("Waiting for VERP...");
            bankVERP = (VERP) ois_bank.readObject();
            System.out.println("VERP received...");
            forwardToMSPI(bankVERP);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DSTask.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void forwardToMSPI(VERP bankVERP) {
        try {
            System.out.println("Forwarding VERP to MSPI...");
            this.oos.writeObject(bankVERP);
        } catch (IOException ex) {
            Logger.getLogger(DSTask.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
