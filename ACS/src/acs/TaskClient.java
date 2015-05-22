/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package acs;

import Authentication.A;
import Authentication.ARP;
import Authentication.ARQ;
import acs.DB.DBStatic;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.SecureRandom;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.SSLSocket;

/**
 *
 * @author Thibault
 */
public class TaskClient implements Runnable
{
    
    private SSLSocket cSock;
    private ObjectOutputStream oos;
    private ObjectInputStream ois;
    
    private DBStatic DB;
    
    public TaskClient(SSLSocket s)
    {
        cSock = s;
        DB = new DBStatic();
    }
    
    @Override
    public void run()
    {
        try
        {
            oos = new ObjectOutputStream(cSock.getOutputStream());
            ois = new ObjectInputStream(cSock.getInputStream());
            if(ois == null || oos == null)
                System.exit(1);
            else
                System.out.println("Flux créés");
            
            ARQ req = (ARQ)ois.readObject();
            String numCard = req.getNumCarte();
            Integer hash = DB.db.get(numCard);
            
            SecureRandom sr = new SecureRandom();
            int challenge = sr.nextInt();
            
            A a = new A(challenge);
            oos.writeObject(a);
            
            a = (A)ois.readObject();
            Integer reponse = a.getNbre();
            
            Random r = new Random(hash*challenge);
            Integer cible = r.nextInt();
            
            ARP arp = new ARP();
            if(reponse == cible)            
                arp.setType(ARP.SUCCESS);
            else
                arp.setType(ARP.FAIL);
            
            oos.writeObject(arp);
            
            // Envoi vers AHS
            
            oos.close();
            ois.close();
            cSock.close();
        }
        catch (IOException | ClassNotFoundException ex)
        {
            Logger.getLogger(TaskClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
