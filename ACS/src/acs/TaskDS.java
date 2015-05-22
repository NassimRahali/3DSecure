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
            FileInputStream fis = new FileInputStream("ACS.properties");
            prop.load(fis);
            
            PORTACS = Integer.parseInt(prop.getProperty("port", "7373"));
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
            ois = new ObjectInputStream(cSock.getInputStream());            
            if(ois == null || oos == null)
                System.exit(1);
            else
                System.out.println("Flux créés");
            
            VERQ req = (VERQ)ois.readObject();
            VERP rep = new VERP();
            if(DB.db.containsKey(req.getNumCard()))
            {
                rep.setType(VERP.SUCCESS);
                rep.setIPACS(IPACS);
                rep.setPORTACS(PORTACS);
            }
            else
                rep.setType(VERP.FAIL);
            
            oos.writeObject(rep);
            
            oos.close();
            ois.close();
            cSock.close();
        }        
        catch (IOException | ClassNotFoundException ex)
        {
            Logger.getLogger(TaskDS.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

