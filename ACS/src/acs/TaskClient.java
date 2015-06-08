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
import static com.oracle.jrockit.jfr.ContentType.Address;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.security.SecureRandom;
import java.util.Random;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import static javax.naming.ldap.SortControl.OID;
import javax.net.ssl.SSLSocket;
import org.snmp4j.CommunityTarget;
import org.snmp4j.PDU;
import org.snmp4j.Snmp;
import org.snmp4j.TransportMapping;
import org.snmp4j.event.ResponseEvent;
import org.snmp4j.mp.SnmpConstants;
import org.snmp4j.smi.Address;
import org.snmp4j.smi.OID;
import org.snmp4j.smi.OctetString;
import org.snmp4j.smi.UdpAddress;
import org.snmp4j.smi.VariableBinding;
import org.snmp4j.transport.DefaultUdpTransportMapping;

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
    
    private String ipSNMP;
    
    public TaskClient(SSLSocket s, String ipsnmp)
    {
        cSock = s;
        DB = new DBStatic();
        
        ipSNMP = ipsnmp;
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
            
            
            // <editor-fold>
            InetAddress inet = InetAddress.getByName(ipSNMP);
            
            System.out.println("Sending Ping Request to " + ipSNMP);
            System.out.println(inet.isReachable(5000) ? "Host is reachable" : "Host is NOT reachable");
            
            
            TransportMapping transport=null; try
            {
                transport = new DefaultUdpTransportMapping();
                transport.listen();
            }
            catch (IOException ex)
            {
                Logger.getLogger(TaskClient.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            CommunityTarget target = new CommunityTarget();
            target.setVersion(SnmpConstants.version1);
            target.setCommunity(new OctetString("public"));
            Address targetAddress = new UdpAddress(ipSNMP + "/161");
            target.setAddress(targetAddress);
            target.setRetries(2);
            target.setTimeout(1500);
            PDU pdu = new PDU();
            pdu.add(new VariableBinding(new OID(".1.3.6.1.2.1.1.1.0"))); // infos machine
            pdu.add(new VariableBinding(new OID(".1.3.6.1.2.1.2.1.0"))); // n interfaces réseau
            pdu.add(new VariableBinding(new OID(".1.3.6.1.2.1.1.7.0"))); // infos générales
            //pdu.add(new VariableBinding(new OID(".1.3.6.1.2.1.2.2.1.8.3"))); // état int réseau
            pdu.setType(PDU.GET);
            Snmp snmp = new Snmp(transport);
            ResponseEvent paquetReponse = null;
            try
            {
                paquetReponse = snmp.get(pdu, target);
                System.out.println("Requete SNMP envoyée à l'agent");
            }
            catch (IOException ex)
            {
                Logger.getLogger(TaskClient.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (paquetReponse != null)
            {
                PDU pduReponse = paquetReponse.getResponse();
                if(pduReponse != null)
                {
                    System.out.println(pduReponse);
                    System.out.println("Status de la réponse = " + pduReponse.getErrorStatus());
                    System.out.println("Status de la réponse = " + pduReponse.getErrorStatusText());
                    Vector vecReponse = pduReponse.getVariableBindings();
                    for (int i=0; i<vecReponse.size(); i++)
                    {
                        System.out.println("Elément n°"+i+ " : "+vecReponse.elementAt(i));
                    }
                }
            }
            //</editor-fold>
            
            ARQ req = (ARQ)ois.readObject();
            String numCard = req.getNumCarte();
            //Integer hash = DB.db.get(numCard);                        
            Integer hash = new String("1234").hashCode();
            
            SecureRandom sr = new SecureRandom();
            int challenge = sr.nextInt();
            
            A a = new A(challenge);
            oos.writeObject(a);
            
            a = (A)ois.readObject();
            int reponse = a.getNbre();
            
            //System.out.println("hash = " + hash);
            //System.out.println("challenge = " + challenge);
            
            Random r = new Random(hash*challenge);
            int cible = r.nextInt();
            
            //System.out.println("rep : " + reponse);
            //System.out.println("cible : " + cible);
            ARP arp = new ARP();
            if(reponse == cible)
            {
                arp.setType(ARP.SUCCESS);
                System.out.println("SUCCESS");
                // Ajout signature
            }
            else
            {
                arp.setType(ARP.FAIL);
                System.out.println("FAIL");
            }
            
            
            //oos.writeObject(arp);
            
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
