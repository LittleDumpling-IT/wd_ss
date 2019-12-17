package com.wendu.config.security;


import com.wendu.config.jwt.JwtProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Token验证过滤器
 * <p>
 * Author GreedyStar
 * Date   2018/7/20
 */
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private JwtProperty jwtProperty;

    final  List<String> urlP = new ArrayList<String>(){{
        add("/login");
        add("/logout");
    }};

    public JwtAuthenticationFilter(JwtProperty jwtProperty) {
        this.jwtProperty = jwtProperty;
    }




    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        httpServletResponse.setContentType("application/json");
        String authorization = httpServletRequest.getHeader("Authorization");
        String uri=httpServletRequest.getRequestURI();
        if(urlP.contains(uri)){
            filterChain.doFilter(httpServletRequest, httpServletResponse);
            return;
        }

      // 放行GET请求
      /*  if (httpServletRequest.getMethod().equals(String.valueOf(RequestMethod.GET))) {
            filterChain.doFilter(httpServletRequest, httpServletResponse);
            return;
        }
        if (StringUtils.isEmpty(authorization)) { // 未提供Token
            httpServletResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            httpServletResponse.getWriter().write(JSON.toJSONString(ResultBean.tokenError(403,"Token not provided")));
            return;
        }
        if (!authorization.startsWith("bearer ")) { // Token格式错误
            httpServletResponse.getWriter().write(JSON.toJSONString(ResultBean.tokenError(403,"Token format error")));

            return;
        }
        authorization = authorization.replace("bearer ", "");
        Claims claims = JwtUtil.parseToken(authorization, jwtProperty.getBase64Security());
        if (null == claims) { // Token不可解码
            httpServletResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            httpServletResponse.getWriter().write(JSON.toJSONString(ResultBean.tokenError(403,"Can't parse token")));

            return;
        }
        if (claims.getExpiration().getTime() <= new Date().getTime()) { // Token超时
            httpServletResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            httpServletResponse.getWriter().write(JSON.toJSONString(ResultBean.tokenError(403,"Token expired")));

            return;
        }
        // 再进行一些必要的验证
        if (StringUtils.isEmpty(claims.get("username"))) {
            httpServletResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            httpServletResponse.getWriter().write(JSON.toJSONString(ResultBean.tokenError(403,"Invalid token")));

            return;
        }
        String users= claims.get("username").toString();
        JSONObject json = JSONObject.parseObject(users);
        SystemUser user = JSON.parseObject(json.toJSONString(),SystemUser.class);*/
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken("admin","123456");
        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }


}
