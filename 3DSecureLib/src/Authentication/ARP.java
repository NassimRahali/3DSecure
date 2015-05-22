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
public class ARP implements Serializable
{
    static final long serialVersionUID = 73;
    
    public static final int SUCCESS = 1;
    public static final int FAIL = 2;
    
    private int type;
    // private Signature sig;

    public ARP() {}

    public ARP(int type)
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
    
}
