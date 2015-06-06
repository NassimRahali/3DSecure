/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package acs;

import VER.VERP;
import VER.VERQ;
import acs.DB.DBStatic;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Thibault
 */
public class TaskDS implements Runnable
{
    
    private Socket cSock;
    private ObjectOutputStream oos;
    private ObjectInputStream ois;    
    
    private DBStatic DB;
    
    private String IPACS;
    private int PORTACS;
    
    public TaskDS(Socket s)
    {
        cSock = s;
        DB = new DBStatic();
        
        try
        {
            Properties prop = new Properties();
            FileInputStream fis = new FileInputStream("ACS2.properties");
            prop.load(fis);
            
            PORTACS = Integer.parseInt(prop.getProperty("portACSCLI", "9666"));
            IPACS = this.cSock.getInetAddress().toString();
            fis.close();
        } catch (IOException ex)
        {
            Logger.getLogger(ACS.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void run()
    {
        try
        {
            oos = new ObjectOutputStream(cSock.getOutputStream());
            oos.flush();
            ois = new ObjectInputStream(cSock.getInputStream());            
            
            if(ois == null || oos == null)
                System.exit(1);
            else
                System.out.println("[AC - DS] Flux créés");
            
            System.out.println("[AC - DS] Waiting for VERQ...");
            VERQ req = (VERQ)ois.readObject();
            
            System.out.println("[AC - DS] VERQ received...");
            VERP rep = new VERP();
            
            // TODO via BDD + cardOwner.
            System.out.println("[AC - DS] Numcard is :" + req.getNumCard());
            
            if(DB.db.containsKey((int)req.getNumCard()))
            {
                rep.setType(VERP.SUCCESS);
                rep.setIPACS(IPACS);
                rep.setPORTACS(PORTACS);
                System.out.println("[AC - DS] IPACS : " + IPACS);
                System.out.println("[AC - DS] PORT AUTH : " + PORTACS);
            }
            else
                rep.setType(VERP.FAIL);
            System.out.println("[AC - DS] Sending VERP... (" + rep.getType() + ") (1 = success, 2 = failure)");
            oos.writeObject(rep);
            System.out.println("[AC - DS] Closing streams.");
            oos.close();
            ois.close();
            System.out.println("[AC - DS] Closing socket.");
            cSock.close();
        }        
        catch (IOException | ClassNotFoundException ex)
        {
            Logger.getLogger(TaskDS.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

