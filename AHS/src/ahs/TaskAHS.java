/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package ahs;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.SSLSocket;

/**
 *
 * @author Thibault
 */
public class TaskAHS implements Runnable
{
    
    private Socket cSock;
    private ObjectOutputStream oos;
    private ObjectInputStream ois;
        
    public TaskAHS(SSLSocket s)
    {
        cSock = s;
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
            
            
            
            oos.close();
            ois.close();
            cSock.close();
        }
        catch (IOException ex)
        {
            Logger.getLogger(TaskAHS.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
