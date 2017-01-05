package ua.devteam.advice;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class SystemArchitecture {

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