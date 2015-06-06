/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ds;

import SSL.SSL;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.Socket;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.NoSuchPaddingException;
import javax.net.ssl.SSLServerSocket;

/**
 *
 * @author nsm
 */
public class DSServer {

    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     * @throws java.security.NoSuchAlgorithmException
     * @throws javax.crypto.NoSuchPaddingException
     * @throws java.security.InvalidKeyException
     */
    public static void main(String[] args) throws IOException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException {
        int PORT = 0;
        int POOL_SIZE = 0;
        int MAX_POOL_SIZE = 0;
        int KEEP_ALIVE = 0;

        try {
            Properties prop = new Properties();
            try (FileInputStream fis = new FileInputStream("ds.properties")) {
                prop.load(fis);
                PORT = Integer.parseInt(prop.getProperty("port", "8888"));
                POOL_SIZE = Integer.parseInt(prop.getProperty("poosSize", "3"));
                MAX_POOL_SIZE = Integer.parseInt(prop.getProperty("maxPoolSize", "5"));
                KEEP_ALIVE = Integer.parseInt(prop.getProperty("keepAlive", "10"));
            }
        } catch (IOException ex) {
            Logger.getLogger(DSServer.class.getName()).log(Level.SEVERE, null, ex);
        }
        Pool threads = new Pool(POOL_SIZE, MAX_POOL_SIZE, KEEP_ALIVE);

        SSLServerSocket sSock = SSL.getSSLServerSocket(PORT);

        Runtime.getRuntime().addShutdownHook(new Thread(){public void run() {
            try {
                System.out.println("DS stop.");
                sSock.close();
            } catch (IOException e)
            {
                System.err.println(e.getMessage());
            }
        }});
        
        while (true) {
            System.out.println("Passage en accept");
            System.out.println("Informations sur le DS : ");
            System.out.println("PORT : " + PORT);
            System.out.println("IP : " + sSock.getInetAddress());
            Socket cSock = sSock.accept();
            System.out.println("Connexion de " + cSock.getInetAddress() + ":" + cSock.getPort());
            threads.pool.execute(new DSTask(cSock));
        }
    }

}
