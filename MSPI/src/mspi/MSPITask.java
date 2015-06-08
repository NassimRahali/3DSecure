/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mspi;

import DS.ReqDS;
import SSL.SSL;
import VER.VERP;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.SSLSocket;
import marchand.ReqMarchand;

/**
 *
 * @author nsm
 */
public class MSPITask implements Runnable {

    private Socket cSock;
    private SSLSocket dsSock;
    private ObjectOutputStream oos;
    private ObjectInputStream ois;
    private final Boolean endConnection = false;
    private String dsAddr = null;
    private int dsPort;
    
    public MSPITask(Socket cs, String dsAddr, int dsPort) {
        this.cSock = cs;
        this.dsAddr = dsAddr;
        this.dsPort = dsPort;
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
                ReqMSPI reqmsp = (ReqMSPI) ois.readObject();
                switch (reqmsp.getType()) {
                    case ReqMSPI.VERIF:
                        VERP bankInfos = verifierCarte(reqmsp);
                        oos.writeObject(bankInfos);
                        break;
                    default:
                        break;
                }

            }
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(MSPITask.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private VERP verifierCarte(ReqMSPI reqmsp) throws IOException {
        boolean cardOk = false;
        
        ReqDS reqds = new ReqDS();
        
        reqds.setCardOwner(reqmsp.getCardOwner());
        reqds.setCardNumber(reqmsp.getCardNumber());
        reqds.setType(ReqDS.VERIF);
        
        System.out.println("--- Verifying with DS");
        System.out.println("--- " + reqmsp.getCardOwner());
        System.out.println("--- " + reqmsp.getCardNumber());
        
        dsSock = SSL.getSSLSocket(dsAddr, dsPort);
        
        ObjectInputStream ois_ds = new ObjectInputStream(dsSock.getInputStream());
        ObjectOutputStream oos_ds = new ObjectOutputStream(dsSock.getOutputStream());
        
        oos_ds.writeObject(reqds);
        
        VERP fromds = null;
        try {
            fromds = (VERP) ois_ds.readObject();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(MSPITask.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        switch(fromds.getType()) {
            case VERP.SUCCESS:
                cardOk = true;
                System.out.println("--- !! Bank said OK !!");
                System.out.println("IP : " + fromds.getIPACS());
                System.out.println("PORT : " + fromds.getPORTACS());
                break;
            default:
                cardOk = false;
                System.out.println("--- !! Bank said NOT OK !!");
        }
        return fromds;
    }

}
