package Model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class InsuranceCard implements Serializable {
    private int cardNumber;
    private PolicyHolder policyHolder;
    private Customer cardHolder; // Every customer has their one and only insurance card
    private String policyOwner; // There is only one policy owner, which must be the policyholder customer
    private Date expirationDate;

    public InsuranceCard(int cardNumber, PolicyHolder policyHolder, Customer cardHolder, String policyOwner, Date expirationDate) {
        this.cardNumber = cardNumber;
        this.policyHolder = policyHolder;
        this.cardHolder = cardHolder;
        this.policyOwner = policyOwner;
        this.expirationDate = expirationDate;
    }

    public int getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(int cardNumber) {
        this.cardNumber = cardNumber;
    }

    public PolicyHolder getPolicyHolder() {
        return policyHolder;
    }

    public void setPolicyHolder(PolicyHolder policyHolder) {
        this.policyHolder = policyHolder;
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
        return "Insurance Card Number: " + cardNumber + "\n"
                + "Policy Owner: " + getPolicyOwner() + "\n"
                + "Policy Holder: " + getPolicyHolder() + "\n"
                + "Current Card Holder: " + getCardHolder();
    }
}
