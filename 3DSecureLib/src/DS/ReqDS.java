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
    private long cardNumber;
    private String cardOwner;
    private int status;

    public ReqDS() {}
    
    public ReqDS(int type)
    {
        this.type = type;
    }
    
    public ReqDS(int type, String nom, long number)
    {
        this.type = type;
        this.cardNumber = number;
        this.cardOwner = nom;
    }

    public int getType()
    {
        return type;
    }

    public void setType(int type)
    {
        this.type = type;
    }

    public long getCardNumber() {
        return cardNumber;
    }

    public String getCardOwner() {
        return cardOwner;
    }

    public void setCardNumber(long cardNumber) {
        this.cardNumber = cardNumber;
    }

    public void setCardOwner(String cardOwner) {
        this.cardOwner = cardOwner;
    }

    public int getStatus() {
        return this.status;
    }
    
    public void setStatus(int status) {
        this.status = status;
    }
}
