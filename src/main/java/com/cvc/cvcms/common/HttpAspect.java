package com.cvc.cvcms.common;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * @author ZZJ
 * @date 2021/4/6 16:44
 * @desc
 */
@Aspect
@Component
@Slf4j
public class HttpAspect {
    @Pointcut("execution( public * com.cvc.cvcms.controller.*.*(..))")
    public void log(){

    }

    @Before("log()")
    public void doBefore(JoinPoint joinPoint){
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        // url
        log.info("url={}",request.getRequestURL());
        //method
        log.info("method = {}",request.getMethod());
        //ip
        log.info("ip = {}",request.getRemoteAddr());
        //类方法
        log.info("class_method={}",joinPoint.getSignature().getDeclaringTypeName()+"."+ joinPoint.getSignature().getName());
        //参数
        String[] argNames = ((MethodSignature)joinPoint.getSignature()).getParameterNames();
        log.info("args = {} -> {}", argNames ,Arrays.toString(joinPoint.getArgs()));

    }


    @After("log()")
    public void doAfter(){

    }

    @AfterReturning(pointcut = "log()",returning = "object")
    public void doAfterReturning(Object object){
        log.info("response = {}",object);
    }
}
