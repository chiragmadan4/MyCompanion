package com.example.chiragmadan.mycompanion;

public class Bill {
    String Month;
    String bill;

    public Bill(String month, String bill) {
        Month = month;
        this.bill = bill;
    }

    public String getMonth() {
        return Month;
    }

    public void setMonth(String month) {
        Month = month;
    }

    public String getBill() {
        return bill;
    }

    public void setBill(String bill) {
        this.bill = bill;
    }
}
