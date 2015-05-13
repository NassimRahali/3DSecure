/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package DS;

import java.io.Serializable;

/**
 *
 * @author Thibault
 */
public class ReqDS implements Serializable
{
    static final long serialVersionUID = 73;
    
    public static final int VERIF = 1;

    private int type;
    private String numCarte;

    public ReqDS() {}
    
    public ReqDS(int type)
    {
        this.type = type;
    }
    
    public ReqDS(int type, String numCarte)
    {
        this.type = type;
        this.numCarte = numCarte;
    }

    public int getType()
    {
        return type;
    }

    public void setType(int type)
    {
        this.type = type;
    }

    public String getNumCarte()
    {
        return numCarte;
    }

    public void setNumCarte(String numCarte)
    {
        this.numCarte = numCarte;
    }
    
    
}
