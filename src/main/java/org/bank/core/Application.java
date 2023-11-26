package org.bank.core;

import org.bank.config.ApplicationConfig;
import org.bank.config.DatabaseConfig;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Application {
    public static void main(String[] args) {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(
                ApplicationConfig.class,
                DatabaseConfig.class
        );
    }
}