package com.cvc.cvcms.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;

/**
 * @author ZZJ
 * @date 2021/3/24 15:42
 * @desc 把所有错误用json返回
 */
@Slf4j
@RestControllerAdvice
public class CustomExtHandler {

    @ExceptionHandler(value = NoHandlerFoundException.class)
    public JsonStandard handlerNoHandlerFoundException(Exception e, HttpServletRequest request) {
        log.error("NoHandlerFoundException处理类捕获异常",e);
        return JsonStandard.error(ErrorEnum.NOT_FOUND,"404 NOT FOUND");
    }

    @ExceptionHandler(value = BusinessException.class)
    public JsonStandard handlerBusinessException(Exception e, HttpServletRequest request) {
        log.error("handlerBusinessException处理类捕获异常",e);
        return JsonStandard.error(ErrorEnum.NOT_ACCEPTABLE,e.getMessage());
    }

    @ExceptionHandler(value = AccessDeniedException.class)
    public JsonStandard handlerAccessDeniedException(Exception e, HttpServletRequest request) {
        log.error("handlerAccessDeniedException处理类捕获异常",e);
        return JsonStandard.error(ErrorEnum.NO_PERNISSION,e.getMessage());
    }

    @ExceptionHandler(value = Exception.class)
    public JsonStandard handlerException(Exception e, HttpServletRequest request) {
        log.error("总异常捕获处理类捕获异常",e);
        return JsonStandard.error(ErrorEnum.INNTERNAL_SERVER_ERROR,"总异常捕获处理类捕获异常"+e.getMessage());
    }
}
