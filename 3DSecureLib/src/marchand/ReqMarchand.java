/*
 * RAHALI Nassim
 * M18
 * 2014-2015
 */
package marchand;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Nassim
 */
public class ReqMarchand implements Serializable {

    static final long serialVersionUID = 42L;

    public static final int PROD = 0;
    public static final int CMD = 1;
    public static final int ARP = 3;

    private int type;

    private List produits;

    public ReqMarchand() {

    }

    public ReqMarchand(int type) {
        this.type = type;
    }

    public ReqMarchand(int type, List produits) {
        this.type = type;
        this.produits = produits;
    }

    public int getType() {
        return type;
    }

    public List getProduits() {
        return produits;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setProduits(List produits) {
        this.produits = produits;
    }

}
