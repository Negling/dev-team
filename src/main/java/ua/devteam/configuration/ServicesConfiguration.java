package ua.devteam.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.Validator;
import ua.devteam.validation.entityValidators.OperationValidator;
import ua.devteam.validation.entityValidators.RequestForDevelopersValidator;
import ua.devteam.validation.entityValidators.TechnicalTaskValidator;

import java.util.ArrayList;
import java.util.List;

@Configuration
@ComponentScan(basePackages = {"ua.devteam.service.impl", "ua.devteam.validation"})
public class ServicesConfiguration {

}
