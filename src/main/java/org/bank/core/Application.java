package org.bank.core;

import org.bank.config.ApplicationConfig;
import org.bank.config.DatabaseConfig;
import org.bank.repository.AccountRepository;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.sql.SQLException;

public class Application {
    public static void main(String[] args) throws SQLException {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(
                ApplicationConfig.class,
                DatabaseConfig.class
        );

        AccountRepository accountRepository = applicationContext.getBean(AccountRepository.class);

        accountRepository.createAccounts("jana:1100", "manne:700", "romain:0");

        accountRepository.findAllAccounts();

        accountRepository.transfer("jana", "romain", 0.5);

        accountRepository.findAllAccounts();
    }
}