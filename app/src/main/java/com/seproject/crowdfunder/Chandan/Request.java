package com.seproject.crowdfunder.Chandan;

public class Request {

    String title;
    String date;
    String amount;

    public Request(String title, String date, String amount) {
        this.title = title;
        this.date = date;
        this.amount = amount;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
