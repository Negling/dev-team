package ua.devteam.advice;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class SystemArchitecture {

    @Pointcut("@annotation(org.springframework.web.bind.annotation.RequestMapping)")
    public void inControllerMethods() {
    }

    @Pointcut("@within(org.springframework.web.bind.annotation.RestController)")
    public void inRestControllers() {
    }

    @Pointcut("within(ua.devteam.controllers..*)")
    public void inWebLayer() {
    }

    @Pointcut("within(ua.devteam.service..*)")
    public void inServiceLayer() {
    }

    @Pointcut("within(ua.devteam.dao..*)")
    public void inDataAccessLayer() {
    }

}
