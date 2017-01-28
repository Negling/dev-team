package ua.devteam.configuration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.DelegatingFilterProxy;

import javax.servlet.Filter;
import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;

/**
 * Registers the {@link DelegatingFilterProxy} to use the springSecurityFilterChain before
 * any other registered {@link Filter}.
 */
public class SecurityInitializer extends AbstractSecurityWebApplicationInitializer {

    private final static Logger logger = LogManager.getLogger(SecurityInitializer.class);

    @Override
    protected void beforeSpringSecurityFilterChain(ServletContext servletContext) {
        String encoding = "UTF-8";


        /* we need encoding filter to be registered before springSecurityFilterChain,
           otherwise encoding filter wont work as expected */
        FilterRegistration.Dynamic characterEncodingFilter =
                servletContext.addFilter("encodingFilter", new CharacterEncodingFilter());
        characterEncodingFilter.setInitParameter("encoding", encoding);
        characterEncodingFilter.setInitParameter("forceEncoding", "true");
        characterEncodingFilter.addMappingForUrlPatterns(null, false, "/*");

        logger.info("Character Encoding filter has successfully registered before security filter chain. " +
                "Encoding param - " + encoding);
    }
}
