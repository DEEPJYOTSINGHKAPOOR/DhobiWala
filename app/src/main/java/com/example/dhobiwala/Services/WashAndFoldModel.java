package com.example.dhobiwala.Services;

public class WashAndFoldModel {
    private String pant_count;
    private String shirt_count;
    private String suit_count;
    private String towel_count ;
    private String transactionId ;

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getPant_count() {
        return pant_count;
    }

    public void setPant_count(String pant_count) {
        this.pant_count = pant_count;
    }

    public String getShirt_count() {
        return shirt_count;
    }

    public void setShirt_count(String shirt_count) {
        this.shirt_count = shirt_count;
    }

    public String getSuit_count() {
        return suit_count;
    }

    public void setSuit_count(String suit_count) {
        this.suit_count = suit_count;
    }

    public String getTowel_count() {
        return towel_count;
    }

    public void setTowel_count(String towel_count) {
        this.towel_count = towel_count;
    }
}
