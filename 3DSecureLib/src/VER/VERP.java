/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package VER;

import java.io.Serializable;

/**
 *
 * @author Thibault
 */
public class VERP implements Serializable
{
    static final long serialVersionUID = 73;
    
    public static final int SUCCESS = 1;
    public static final int FAIL = 2;
    
    private int type;
    
    private String IPACS;
    private int PORTACS;

    public VERP() {}

    public VERP(int type, String IPACS, int PORTACS)
    {
        this.type = type;
        this.IPACS = IPACS;
        this.PORTACS = PORTACS;
    }

    public int getType()
    {
        return type;
    }

    public void setType(int type)
    {
        this.type = type;
    }

    public String getIPACS()
    {
        return IPACS;
    }

    public void setIPACS(String IPACS)
    {
        this.IPACS = IPACS;
    }

    public int getPORTACS()
    {
        return PORTACS;
    }

    public void setPORTACS(int PORTACS)
    {
        this.PORTACS = PORTACS;
    }
    
    
}
