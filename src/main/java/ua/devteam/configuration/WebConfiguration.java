package ua.devteam.configuration;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.validation.Validator;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import ua.devteam.advice.ControllersAdvice;

import java.util.*;

/**
 * Configures and instantiates all WEB tier beans.
 */
@EnableWebMvc
@Configuration
@EnableAspectJAutoProxy
@ComponentScan(basePackages = {"ua.devteam.controllers", "ua.devteam.validation"})
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebConfiguration extends WebMvcConfigurerAdapter {


    @Bean
    public InternalResourceViewResolver viewResolver() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/views/");
        viewResolver.setSuffix(".jsp");

        return viewResolver;
    }

    @Bean
    public LocaleResolver localeResolver() {
        CookieLocaleResolver resolver = new CookieLocaleResolver();
        resolver.setDefaultLocale(Locale.ENGLISH);

        return resolver;
    }

    @Bean
    public ControllersAdvice controllersAdvice(Validator compositeValidator) {
        return new ControllersAdvice(messageSource(), compositeValidator);
    }

    @Bean
    public List<Validator> entityValidators(Validator checkValidator, Validator customerRegistrationFormValidator,
                                            Validator technicalTaskValidator) {
        return new LinkedList<Validator>() {
            {
                add(checkValidator);
                add(customerRegistrationFormValidator);
                add(technicalTaskValidator);
            }
        };
    }

    @Bean
    public PropertyPlaceholderConfigurer placeholderConfigurer() {
        PropertyPlaceholderConfigurer configurer = new PropertyPlaceholderConfigurer();
        configurer.setProperties(convertResourceBundleToProperties(ResourceBundle.getBundle("/properties/mapping")));

        return configurer;
    }

    @Bean
    public ResourceBundle pagesProperties() {
        return ResourceBundle.getBundle("/properties/view_naming");
    }

    @Bean
    public MessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename("/l10n/localization");
        messageSource.setDefaultEncoding("UTF-8");

        return messageSource;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        LocaleChangeInterceptor interceptor = new LocaleChangeInterceptor();
        interceptor.setParamName("language");
        registry.addInterceptor(interceptor);
    }

    private Properties convertResourceBundleToProperties(ResourceBundle resource) {
        Properties properties = new Properties();

        resource.keySet().forEach(key -> properties.put(key, resource.getString(key)));

        return properties;
    }
}
