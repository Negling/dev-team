package ua.devteam.configuration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;
import org.springframework.web.filter.CharacterEncodingFilter;

import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;

public class SecurityInitializer extends AbstractSecurityWebApplicationInitializer {

    private final static Logger logger = LogManager.getLogger(SecurityInitializer.class);

    @Override
    protected void beforeSpringSecurityFilterChain(ServletContext servletContext) {
        String encoding = "UTF-8";

        FilterRegistration.Dynamic characterEncodingFilter =
                servletContext.addFilter("encodingFilter", new CharacterEncodingFilter());
        characterEncodingFilter.setInitParameter("encoding", encoding);
        characterEncodingFilter.setInitParameter("forceEncoding", "true");
        characterEncodingFilter.addMappingForUrlPatterns(null, false, "/*");

        logger.info("Character Encoding filter has successfully registered before security filter chain. " +
                "Encoding param - " + encoding);
    }
}
