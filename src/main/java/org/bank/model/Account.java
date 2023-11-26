package org.bank.model;

public class Account {

    private String accountId;

    private double total;

    public Account(String accountId, double total) {
        this.accountId = accountId;
        this.total = total;
    }

    public String getAccountId() {
        return accountId;
    }

    public Account setAccountId(String accountId) {
        this.accountId = accountId;

        return this;
    }

    public double getTotal() {
        return total;
    }

    public Account setTotal(double total) {
        this.total = total;

        return this;
    }
}
