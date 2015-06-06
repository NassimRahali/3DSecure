/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mspi;

import java.io.Serializable;

/**
 *
 * @author nsm
 */
public class ReqMSPI implements Serializable {

    static final long serialVersionUID = 76L;    
    
    public static final int VERIF = 1;
    public static final int ARP = 2;
    
    private long cardNumber;
    private String cardOwner;
    
    private int type;
    private int status;
    
    public long getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(long cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getCardOwner() {
        return cardOwner;
    }

    public void setCardOwner(String cardOwner) {
        this.cardOwner = cardOwner;
    }
    
    public int getType()
    {
        return type;
    }

    public void setType(int type)
    {
        this.type = type;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
