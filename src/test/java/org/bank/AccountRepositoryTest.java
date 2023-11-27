package org.bank;

import org.bank.config.ApplicationConfig;
import org.bank.model.Account;
import org.bank.repository.AccountRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {ApplicationConfig.class, DatabaseTestConfig.class})
@ActiveProfiles("test")
@Transactional
class AccountRepositoryTest {

    @Autowired
    AccountRepository accountRepository;

    @Test
    void testCreateAccounts() {
        List<Account> accounts = accountRepository.findAllAccounts();
        Assertions.assertEquals(0, accounts.size());
        accountRepository.createAccounts("A:100", "B:200");
        accounts = accountRepository.findAllAccounts();
        Assertions.assertEquals(2, accounts.size());
        Assertions.assertEquals(100, accounts.get(0).getTotal());
        Assertions.assertEquals(200, accounts.get(1).getTotal());
    }

    @Test
    void testTransfer() throws SQLException {
        accountRepository.createAccounts("A:100", "B:200");
        accountRepository.transfer("A", "B", 50);
        List<Account> accounts = accountRepository.findAllAccounts();
        Assertions.assertEquals(2, accounts.size());
        Assertions.assertEquals(50, accounts.get(0).getTotal());
        Assertions.assertEquals(250, accounts.get(1).getTotal());
    }

    @Test
    void throwExceptionIfAmountIsNegative() throws SQLException {
        accountRepository.createAccounts("A:100", "B:200");
        Assertions.assertThrows(SQLException.class, () -> {
            accountRepository.transfer("A", "B", -50);
        });
    }

    @Test
    void throwExceptionIfAmountIsTooLarge() throws SQLException {
        accountRepository.createAccounts("A:100", "B:200");
        Assertions.assertThrows(SQLException.class, () -> {
            accountRepository.transfer("A", "B", 150);
        });
    }

}
