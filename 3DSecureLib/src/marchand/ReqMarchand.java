/*
 * RAHALI Nassim
 * M18
 * 2014-2015
 */
package marchand;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author Nassim
 */
public class ReqMarchand implements Serializable {

    static final long serialVersionUID = 42L;

    public static final int PROD = 1;
    public static final int CMD = 2;
    public static final int ARP = 3;
    
    private int type;

    private List<Produit> produits;
    private HashMap<Produit, Integer> commande;
    private String numCarte;
    
    private String mspiAddr;
    private int mspiPort;
    private int status;
    private float total;
    
    public ReqMarchand() {}

    public ReqMarchand(int type) {
        this.type = type;
    }

    public int getType()
    {
        return type;
    }

    public void setType(int type)
    {
        this.type = type;
    }

    public List<Produit> getProduits()
    {
        return produits;
    }

    public void setProduits(List<Produit> produits)
    {
        this.produits = produits;
    }

    public HashMap<Produit, Integer> getCommande()
    {
        return commande;
    }

    public void setCommande(HashMap<Produit, Integer> commande)
    {
        this.commande = commande;
    }

    public String getNumCarte()
    {
        return numCarte;
    }

    public void setNumCarte(String numCarte)
    {
        this.numCarte = numCarte;
    }

    public void setMspi(String addr, int port) {
        this.mspiAddr = addr;
        this.mspiPort = port;
    }
    
    public void setMspiAddr(String mspiAddr) {
        this.mspiAddr = mspiAddr;
    }

    public void setMspiPort(int mspiPort) {
        this.mspiPort = mspiPort;
    }
    
    public void setStatus(int status) {
        this.status = status;
    }

    public float getTotal() {
        return total;
    }

    public String getMspiAddr() {
        return mspiAddr;
    }

    public int getMspiPort() {
        return mspiPort;
    }

    public int getStatus() {
        return status;
    }
    
    public void computeTotal() {
        this.total = 0;
        this.commande.forEach( (prod, qte) -> {
            total += qte * prod.getPrix();
        });
    }

}
