/*
 * RAHALI Nassim
 * M18
 * 2014-2015
 */
package marchand;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Nassim
 */
public class MarchandTask implements Runnable {

    private final Socket cSock;
    private ObjectOutputStream oos;
    private ObjectInputStream ois;
    private final Boolean endConnection = false;
    
    private final String mspiAddr;
    private final int mspiPort;

    public MarchandTask(Socket cs, String mspiAddr, int mspiPort) {
        this.cSock = cs;
        this.mspiAddr = mspiAddr;
        this.mspiPort = mspiPort;
    }

    @Override
    public void run() {
        try {
            System.out.println("\tWorking with : " + cSock.getPort());
            oos = new ObjectOutputStream(cSock.getOutputStream());
            ois = new ObjectInputStream(cSock.getInputStream());
            if (oos == null || ois == null) {
                System.err.println("Failed to load streams.");
                System.out.println("System will exit.");
                System.exit(-1);
            }
            System.out.println("\tStreams created.");
            while (!endConnection) {
                System.out.println("\tWaiting for request...");
                ReqMarchand reqm = (ReqMarchand) ois.readObject();
                System.out.println("\tRequest receptionned.");
                ReqMarchand repm;
                switch (reqm.getType()) {
                    case ReqMarchand.PROD:
                        // Se connecte à la BDD et récup les produits + send au client
                        System.out.println("\tRequest is PROD");
                        List<Produit> produits = getProduits();
                        repm = new ReqMarchand(ReqMarchand.PROD);
                        repm.setStatus(1);
                        repm.setProduits(produits);
                        System.out.println("\tSending products...");
                        oos.writeObject(repm);
                        break;
                    case ReqMarchand.CMD:
                        System.out.println("\tRequest is CMD");
                        // On passe la main au MSPI (réponse avec les info MSPI)
                        repm = new ReqMarchand(ReqMarchand.CMD);
                        if(!reqm.getCommande().isEmpty()) {
                            repm.setStatus(1);
                            repm.setMspi(mspiAddr, mspiPort);
                        } else {
                            repm.setStatus(-1);
                        }
                        oos.writeObject(repm);
                        break;
                    case ReqMarchand.ARP:
                        // OTP
                        break;
                    default:
                        break;
                }

            }
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(MarchandTask.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public synchronized List<Produit> getProduits() throws ClassNotFoundException {
        try {
            String query = "SELECT * FROM produits";
            List<Produit> produits;
            try (Connection con = new MySQLHelper().getMarchandConnection()) {
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(query);
                produits = new ArrayList();
                while (rs.next()) {
                    produits.add(new Produit(rs.getString(2), rs.getFloat(3)));
                }
            }
            return produits;
        } catch (SQLException ex) {
            System.err.println(ex);
        }
        return null;
    }

}
