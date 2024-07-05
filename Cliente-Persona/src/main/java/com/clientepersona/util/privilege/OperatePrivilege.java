package com.clientepersona.util.privilege;


import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.lang.reflect.Method;

@Aspect
@Component
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class OperatePrivilege {

    @Autowired
    private HttpServletRequest httpServletRequest;

    @Pointcut("@annotation(Privilege)")
    public void annotationPointCut() {
    }

    @Before("annotationPointCut()")
    public void befor(JoinPoint joinPoint) {

        MethodSignature sign = (MethodSignature) joinPoint.getSignature();
        Method method = sign.getMethod();
        if (!httpServletRequest.getHeader("user-agent").startsWith("Java/")) {
            String token = httpServletRequest.getHeader("Authorization").split(" ")[1];

            Privilege privilege = method.getAnnotation(Privilege.class);
            boolean access = false;

            if (!access) {
                throw new ResponseStatusException(
                        HttpStatus.FORBIDDEN,
                        "Can't access to this service");
            }
        }
    }

}

