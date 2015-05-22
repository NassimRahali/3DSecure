/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package Authentication;

import java.io.Serializable;

/**
 *
 * @author Thibault
 */
public class ARQ implements Serializable
{
    static final long serialVersionUID = 73;
    
    private String numCarte;

    public ARQ() {}

    public ARQ(String numCarte)
    {
        this.numCarte = numCarte;
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
