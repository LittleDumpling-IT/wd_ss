package com.wendu.config.security;

import com.alibaba.fastjson.JSON;
import com.wendu.entily.Result;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 登录失败Handler
 * <p>
 * Author GreedyStar
 * Date   2018/7/20
 */
@Component
public class LoginFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        String message="";
        if(e instanceof UsernameNotFoundException){
            message="用户不存在";
        }else if(e instanceof BadCredentialsException){
            message="密码错误";
        }
        httpServletResponse.setContentType("application/json;charset=UTF-8");
        httpServletResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        httpServletResponse.getWriter().write(JSON.toJSONString(new Result(false,400,message)));
    }
}
