/*
 * RAHALI Nassim
 * M18
 * 2014-2015
 */
package marchand;

import java.io.Serializable;

/**
 *
 * @author Nassim
 */
public class Produit implements Serializable {
    static final long serialVersionUID = 43L;
    
    private String nom;
    private Float prix;

    public Produit() {
        
    };
    
    public Produit(String nom, Float prix) {
        this.nom = nom;
        this.prix = prix;
    }
    
    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Float getPrix() {
        return prix;
    }

    public void setPrix(Float prix) {
        this.prix = prix;
    }
    
    
}
