package ua.devteam.advice;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

/**
 * Simple class to map project architecture pointcuts.
 */
@Aspect
public class SystemArchitecture {

    /**
     * All methods annotated with {@link org.springframework.web.bind.annotation.RequestMapping}.
     */
    @Pointcut("@annotation(org.springframework.web.bind.annotation.RequestMapping)")
    public void inControllerMethods() {
    }

    /**
     * All methods within classes annotated with {@link org.springframework.web.bind.annotation.RestController}.
     */
    @Pointcut("@within(org.springframework.web.bind.annotation.RestController)")
    public void inRestControllers() {
    }

    /**
     * All methods in controllers package.
     */
    @Pointcut("within(ua.devteam.controllers..*)")
    public void inWebLayer() {
    }

    /**
     * All methods in service package.
     */
    @Pointcut("within(ua.devteam.service..*)")
    public void inServiceLayer() {
    }

    /**
     * All methods in DAO layer.
     */
    @Pointcut("within(ua.devteam.dao..*)")
    public void inDataAccessLayer() {
    }
}
