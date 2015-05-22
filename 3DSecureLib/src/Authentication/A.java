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
public class A implements Serializable
{
    static final long serialVersionUID = 73;
    
    private int nbre;

    public A() {}

    public A(int n)
    {
        this.nbre = n;
    }

    public int getNbre()
    {
        return nbre;
    }

    public void setNbre(int n)
    {
        this.nbre = n;
    }
    
    
}
