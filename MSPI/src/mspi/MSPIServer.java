/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mspi;

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
public class MSPIServer {

    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException {
        int PORT = 0;
        int POOL_SIZE = 0;
        int MAX_POOL_SIZE = 0;
        int KEEP_ALIVE = 0;
        String dsAddr = null;
        int dsPort = 8888;

        try {
            Properties prop = new Properties();
            try (FileInputStream fis = new FileInputStream("mspi.properties")) {
                prop.load(fis);
                
                PORT = Integer.parseInt(prop.getProperty("port", "6666"));
                POOL_SIZE = Integer.parseInt(prop.getProperty("poosSize", "3"));
                MAX_POOL_SIZE = Integer.parseInt(prop.getProperty("maxPoolSize", "5"));
                KEEP_ALIVE = Integer.parseInt(prop.getProperty("keepAlive", "10"));
                dsAddr = prop.getProperty("dsAddr", "localhost");
                dsPort = Integer.parseInt(prop.getProperty("dsPort", "8888"));
            }
        } catch (IOException ex) {
            Logger.getLogger(MSPIServer.class.getName()).log(Level.SEVERE, null, ex);
        }
        Pool threads = new Pool(POOL_SIZE, MAX_POOL_SIZE, KEEP_ALIVE);

        SSLServerSocket sSock = SSL.getSSLServerSocket(PORT);
        
        Runtime.getRuntime().addShutdownHook(new Thread(){public void run() {
            try {
                System.out.println("MSPI shut down.");
                sSock.close();
            } catch (IOException e)
            {
                System.err.println(e.getMessage());
            }
        }});
        
        while (true) {
            System.out.println("Passage en accept");
            System.out.println("Informations sur le plugin MSPI: ");
            System.out.println("PORT : " + PORT);
            System.out.println("IP : " + sSock.getInetAddress());
            Socket cSock = sSock.accept();
            System.out.println("Client accepté.");
            threads.pool.execute(new MSPITask(cSock, dsAddr, dsPort));
        }
    }
    
}
