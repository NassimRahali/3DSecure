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
public class VERQ implements Serializable
{
    static final long serialVersionUID = 73;

    private String numCard;
    private String cardOwner;
    
    public VERQ() {}

    public VERQ(String numCard, String cardOwner)
    {
        this.numCard = numCard;
        this.cardOwner = cardOwner;
    }

    public String getNumCard()
    {
        return numCard;
    }

    public void setNumCard(String numCard)
    {
        this.numCard = numCard;
    }

    public String getCardOwner()
    {
        return cardOwner;
    }

    public void setCardOwner(String cardOwner)
    {
        this.cardOwner = cardOwner;
    }    
}
