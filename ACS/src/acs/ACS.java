/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package acs;

import SSL.SSL;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLSocket;

public class ACS {

    public static void main(String[] args) throws IOException {
        int PORT = 0;
        int POOL_SIZE = 0;
        int MAX_POOL_SIZE = 0;
        int KEEP_ALIVE = 0;

        // <editor-fold defaultstate="collapsed" desc="Load Properties">
        try {
            Properties prop = new Properties();
            FileInputStream fis = new FileInputStream("ACS.properties");
            prop.load(fis);

            PORT = Integer.parseInt(prop.getProperty("portACSDS", "9999"));
            POOL_SIZE = Integer.parseInt(prop.getProperty("poosSize", "3"));
            MAX_POOL_SIZE = Integer.parseInt(prop.getProperty("maxPoolSize", "5"));
            KEEP_ALIVE = Integer.parseInt(prop.getProperty("keepAlive", "10"));

            fis.close();
        } catch (IOException ex) {
            Logger.getLogger(ACS.class.getName()).log(Level.SEVERE, null, ex);
        }
        // </editor-fold>

        Main2 thread = new Main2();
        thread.start();

        Pool threads = new Pool(POOL_SIZE, MAX_POOL_SIZE, KEEP_ALIVE);

        SSLServerSocket sSock = SSL.getSSLServerSocket(PORT);

        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                try {
                    System.out.println("ACS stop.");
                    sSock.close();
                } catch (IOException e) {
                    System.err.println(e.getMessage());
                }
            }
        });

        while (true) {
            System.out.println("[ACS - DS] Passage en accept");
            System.out.println("[ACS - DS] Informations sur la banque client : ");
            System.out.println("[ACS - DS] PORT : " + PORT);
            System.out.println("[ACS - DS] IP : " + sSock.getInetAddress());
            SSLSocket cSock = (SSLSocket) sSock.accept();
            threads.pool.execute(new TaskDS(cSock));
        }
    }

}

class Main2 extends Thread {

    public Main2() {
    }

    @Override
    public void run() {

        try {
            int PORT = 0;
            int POOL_SIZE = 0;
            int MAX_POOL_SIZE = 0;
            int KEEP_ALIVE = 0;
            String ipSNMP = "";

            // <editor-fold defaultstate="collapsed" desc="Load Properties">
            try {
                Properties prop = new Properties();
                FileInputStream fis = new FileInputStream("ACS2.properties");
                prop.load(fis);

                PORT = Integer.parseInt(prop.getProperty("portACSCLI", "9666"));
                POOL_SIZE = Integer.parseInt(prop.getProperty("poosSize", "3"));
                MAX_POOL_SIZE = Integer.parseInt(prop.getProperty("maxPoolSize", "5"));
                KEEP_ALIVE = Integer.parseInt(prop.getProperty("keepAlive", "10"));
                ipSNMP = prop.getProperty("ipSNMP", "192.168.94.142");

                fis.close();
            } catch (IOException ex) {
                Logger.getLogger(ACS.class.getName()).log(Level.SEVERE, null, ex);
            }
            // </editor-fold>

            Pool threads = new Pool(POOL_SIZE, MAX_POOL_SIZE, KEEP_ALIVE);

            SSLServerSocket sSock = SSL.getSSLServerSocket(PORT);

            Runtime.getRuntime().addShutdownHook(new Thread() {
                public void run() {
                    try {
                        System.out.println("ACS stop.");
                        sSock.close();
                    } catch (IOException e) {
                        System.err.println(e.getMessage());
                    }
                }
            });
            
            while (true) {
                System.out.println("[AUTH] Passage en accept...");
                System.out.println("[AUTH] Informations sur la banque client : ");
                System.out.println("[AUTH] PORT AUTH : " + PORT);
                SSLSocket cSock = (SSLSocket) sSock.accept();
                System.out.println("Connexion de " + cSock.getInetAddress() + ":" + cSock.getPort());
                threads.pool.execute(new TaskClient(cSock, ipSNMP));
            }
        } catch (IOException ex) {
            Logger.getLogger(Main2.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
