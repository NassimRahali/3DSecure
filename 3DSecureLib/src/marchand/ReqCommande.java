/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package marchand;

import java.io.Serializable;
import java.util.HashMap;

/**
 *
 * @author Thibault
 */
public class ReqCommande implements Serializable 
{
    static final long serialVersionUID = 41L;

    public static final int CMD = 1;
    
    private int type;

    private HashMap<Produit, Integer> commande;
    
    public ReqCommande(){}
    
    public ReqCommande(int type)
    {
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

    public HashMap<Produit, Integer> getCommande()
    {
        return commande;
    }

    public void setCommande(HashMap<Produit, Integer> commande)
    {
        this.commande = commande;
    }
    
    

}
