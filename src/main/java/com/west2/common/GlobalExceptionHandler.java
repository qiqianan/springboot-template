package com.west2.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;


/**
 * @author 1
 * 全局异常处理
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 认证异常
     */
    @ExceptionHandler(value = AuthenticationException.class)
    @ResponseBody
    public HttpResult AuthenticationExceptionHandler(HttpServletRequest req, AuthenticationException e){
        log.error("AuthenticationException ：{}",e.getMessage());
        if (e instanceof BadCredentialsException) {
            return HttpResult.error("密码错误");
        } else if (e instanceof UsernameNotFoundException) {
            return HttpResult.error("用户名不存在");
        }
        return HttpResult.error("登录失败");
    }

    /**
     * 权限不足异常
     */
    @ExceptionHandler(value = AccessDeniedException.class)
    @ResponseBody
    public HttpResult AccessDeniedExceptionHandler(HttpServletRequest req, AccessDeniedException e){
        log.error("AccessDeniedException ：{}",e.getMessage());
        return HttpResult.error("权限不足");
    }

    /**
     * 其他异常
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public HttpResult exceptionHandler(HttpServletRequest req, Exception e){
        log.error("其他异常：{}",e.getMessage());
        return HttpResult.error("系统异常");
    }
}
