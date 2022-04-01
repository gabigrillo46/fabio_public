package com.piantino.report.Dtos;



public class PaymentDetailsDTO {

    private String payment_detail;
    private Double amount_paid;

    public String getPayment_detail() {
        return payment_detail;
    }

    public void setPayment_detail(String payment_detail) {
        this.payment_detail = payment_detail;
    }

    public Double getAmount_paid() {
        return amount_paid;
    }

    public void setAmount_paid(Double amount_paid) {
        this.amount_paid = amount_paid;
    }
}
