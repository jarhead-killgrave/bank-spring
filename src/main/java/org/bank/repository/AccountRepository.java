package org.bank.repository;

import org.bank.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;

@Repository
public class AccountRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public AccountRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Transactional(rollbackFor = Exception.class)
    public void createAccounts(String... accounts) {

        System.out.println("Begin - Create accounts");

        try {

            for (String account : accounts) {
                String[] split = account.split(":");
                String accountId = split[0];
                double total = Double.parseDouble(split[1]);
                jdbcTemplate.update(
                        "INSERT INTO accounts (accountId, total) VALUES (?, ?)",
                        accountId,
                        total
                );
            }
        } finally {
            System.out.println("End - Create accounts");
        }
    }

    public void findAllAccounts() {
        List<Account> accounts = jdbcTemplate.query(
                "SELECT * FROM accounts",
                (rs, rowNum) -> new Account(
                        rs.getString("accountId"),
                        rs.getDouble("total")
                )
        );

        System.out.println("Accounts: ");
        accounts.forEach(System.out::println);
    }

    @Transactional(rollbackFor = Exception.class)
    public void transfer(String from, String to, double amount) throws SQLException {
        System.out.println("Begin - Transfer from " + from + " to " + to);

        try {
            if (amount < 0) {
                throw new SQLException("Amount must be positive");
            }

            Double totalFromAccount = this.jdbcTemplate.queryForObject(
                    "SELECT total FROM accounts WHERE accountId = ?",
                    Double.class,
                    from
            );

            if (totalFromAccount == null) {
                throw new SQLException("Account not found");
            }

            if (totalFromAccount < amount) {
                throw new SQLException("Not enough money");
            }

            this.jdbcTemplate.update(
                    "UPDATE accounts SET total = total - ? WHERE accountId = ?",
                    amount,
                    from
            );

            this.jdbcTemplate.update(
                    "UPDATE accounts SET total = total + ? WHERE accountId = ?",
                    amount,
                    to
            );
        } finally {
            System.out.println("End - Transfer from " + from + " to " + to);
        }
    }

}
