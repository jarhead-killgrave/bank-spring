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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof Account account)) return false;

        if (Double.compare(getTotal(), account.getTotal()) != 0) return false;

        return getAccountId().equals(account.getAccountId());
    }

    @Override
    public int hashCode() {
        return getAccountId().hashCode();
    }

    @Override
    public String toString() {
        return "Account{" +
                "accountId='" + accountId + '\'' +
                ", total=" + total +
                '}';
    }
}
