package com.wendu.config.security;

import com.alibaba.fastjson.JSON;

import com.wendu.config.jwt.JwtProperty;
import com.wendu.config.jwt.JwtUtil;
import com.wendu.entily.Result;
import com.wendu.entily.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 登录成功Handler
 * <p>
 * Author GreedyStar
 * Date   2018/7/20
 */
@Component
public class LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    @Autowired
    private JwtProperty jwtProperty;


    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        String username = httpServletRequest.getParameter("username");
/*
        SystemUser systUser= (SystemUser) authentication.getPrincipal();
*/
        String token = JwtUtil.createToken  ("admin", jwtProperty);
        httpServletResponse.setContentType("application/json;charset=UTF-8");
        httpServletResponse.getWriter().write(JSON.toJSONString(new Result(true, StatusCode.OK,"登陆成功","bearer "+token)));
    }
}
