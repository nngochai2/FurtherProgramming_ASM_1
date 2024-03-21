package Model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class InsuranceCard implements Serializable {
    private String cardNumber;
    private Customer cardHolder; // Every customer has their one and only insurance card
    private String policyOwner; // There is only one policy owner, which must be the policyholder customer
    private Date expirationDate;

    public InsuranceCard(String cardNumber, Customer cardHolder, String policyOwner, Date expirationDate) {
        this.cardNumber = cardNumber;
        this.cardHolder = cardHolder;
        this.policyOwner = policyOwner;
        this.expirationDate = expirationDate;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public Customer getCardHolder() {
        return cardHolder;
    }

    public void setCardHolder(Customer cardHolder) {
        this.cardHolder = cardHolder;
    }

    public String getPolicyOwner() {
        return policyOwner;
    }

    public void setPolicyOwner(String policyOwner) {
        this.policyOwner = policyOwner;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    @Override
    public String toString() {
        return "Insurance Card Number: " + cardNumber;
    }
}
