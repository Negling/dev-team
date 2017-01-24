package ua.devteam.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import ua.devteam.advice.ExceptionsAdvice;
import ua.devteam.advice.LoggingAdvice;

@Configuration
@EnableAspectJAutoProxy
@EnableTransactionManagement
@ComponentScan(basePackages = "ua.devteam.service.impl")
public class ServicesConfiguration {

    @Bean
    public LoggingAdvice loggingAdvice() {
        return new LoggingAdvice();
    }

    @Bean
    public ExceptionsAdvice exceptionsAdvice() {
        return new ExceptionsAdvice();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }
}
