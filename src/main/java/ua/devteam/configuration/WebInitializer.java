package ua.devteam.configuration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

/**
 * Registers {@link org.springframework.web.servlet.DispatcherServlet DispatcherServlet} configured with annotated classes, e.g. Spring's
 * {@link org.springframework.context.annotation.Configuration @Configuration} classes.
 */
public class WebInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
    private final static Logger logger = LogManager.getLogger(WebInitializer.class);

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class<?>[]{DataAccessConfiguration.class, ServicesConfiguration.class, SecurityConfiguration.class};
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class<?>[]{WebConfiguration.class};
    }

    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }

    @Override
    protected DispatcherServlet createDispatcherServlet(WebApplicationContext servletAppContext) {
        /* To handle 404 requests we need to set "throwExceptionIfNoHandlerFound" dispatcherServlet property to true,
           and with this, if no handler found for specific request path - a NoHandlerFoundException will occur, which
           can be handled with {@link org.springframework.web.bind.annotation.ControllerAdvice ControllerAdvice}
           exceptionHandler method. */

        DispatcherServlet dispatcherServlet = (DispatcherServlet) super.createDispatcherServlet(servletAppContext);
        dispatcherServlet.setThrowExceptionIfNoHandlerFound(true);

        logger.info("DispatcherServlet successfully created, \"throwExceptionIfNoHandlerFound property\" is set to " +
                "true.");

        return dispatcherServlet;
    }

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        super.onStartup(servletContext);

        String activeProfiles = "prod";
        servletContext.setInitParameter("spring.profiles.active", activeProfiles);

        logger.info("Spring web application active profile is set to \"" + activeProfiles + "\".");
    }
}
