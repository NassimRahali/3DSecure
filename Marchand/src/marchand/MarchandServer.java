/*
 * RAHALI Nassim
 * M18
 * 2014-2015
 */
package marchand;

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
import SSL.SSL;
import ds.Pool;

/**
 *
 * @author Nassim
 */
public class MarchandServer {

    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException {
        int PORT = 0;
        int POOL_SIZE = 0;
        int MAX_POOL_SIZE = 0;
        int KEEP_ALIVE = 0;
        String mspiAddr = null;
        int mspiPort = 0;

        try {
            Properties prop = new Properties();
            try (FileInputStream fis = new FileInputStream("marchand.properties")) {
                prop.load(fis);
                
                PORT = Integer.parseInt(prop.getProperty("port", "6666"));
                POOL_SIZE = Integer.parseInt(prop.getProperty("poosSize", "3"));
                MAX_POOL_SIZE = Integer.parseInt(prop.getProperty("maxPoolSize", "5"));
                KEEP_ALIVE = Integer.parseInt(prop.getProperty("keepAlive", "10"));
                mspiAddr = prop.getProperty("mspiAddr", "127.0.0.1");
                mspiPort = Integer.parseInt(prop.getProperty("mspiPort"));
            }
        } catch (IOException ex) {
            Logger.getLogger(MarchandServer.class.getName()).log(Level.SEVERE, null, ex);
        }
        Pool threads = new Pool(POOL_SIZE, MAX_POOL_SIZE, KEEP_ALIVE);
        
        SSLServerSocket sSock = SSL.getSSLServerSocket(PORT);
        
        Runtime.getRuntime().addShutdownHook(new Thread(){public void run() {
            try {
                sSock.close();
                System.out.println("Marchand Server shut down.");
            } catch (IOException e)
            {
                /* Failed */
            }
        }});

        while (true) {
            System.out.println("Passage en accept");
            System.out.println("Informations sur le serveur marchand: ");
            System.out.println("PORT : " + PORT);
            System.out.println("IP : " + sSock.getInetAddress());
            Socket cSock = sSock.accept();
            System.out.println("Nouveau client accepté.");
            threads.pool.execute(new MarchandTask(cSock, mspiAddr, mspiPort));
        }
    }

}
