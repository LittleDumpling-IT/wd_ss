package com.wendu.config.security;


import com.wendu.config.jwt.JwtProperty;
import com.wendu.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.web.cors.CorsUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * Security配置
 * <p>
 * Author GreedyStar
 * Date   2018/7/20
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private LoginSuccessHandler loginSuccessHandler; // 登录成功处理器
    @Autowired
    private LoginFailureHandler loginFailureHandler; // 登录失败处理器
    @Autowired
    private LogoutSuccessHandler logoutSuccessHandler; // 注销成功处理器
    @Autowired
    private AuthEntryPoint authEntryPoint; // 权限认证异常处理器
    @Autowired
    private AdminService adminService;
    @Autowired
    private JwtProperty jwtProperty; // jwt属性


  @Autowired
    private AuthenticationDetailsSource<HttpServletRequest, WebAuthenticationDetails> authenticationDetailsSource;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        // 配置UserService和密码加密服务
        auth.authenticationProvider(new MyAuthenticationProvider(adminService));
    }


    public void configure(WebSecurity web) throws Exception {
        /* 在这里配置security放行的请求 */
        // swagger2
        web.ignoring().antMatchers("/swagger-ui.html*/**");
        web.ignoring().mvcMatchers("/v2/api-docs", "/configuration/security", "swagger-resources");
    }


    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.cors().and().csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .authorizeRequests().requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
                .antMatchers("/login","/logout","/swagger-ui.html","/swagger-ui.html/*").permitAll()
                .and()
                .formLogin().loginProcessingUrl("/login")
                .authenticationDetailsSource(authenticationDetailsSource)
                .successHandler(loginSuccessHandler)
                .failureHandler(loginFailureHandler)
                .and()
                .logout().logoutUrl("/logout").logoutSuccessHandler(logoutSuccessHandler)
                .and()
                .exceptionHandling().authenticationEntryPoint(authEntryPoint);
        // 配置jwt验证过滤器，位于用户名密码验证过滤器之后
        httpSecurity.addFilterAfter(new JwtAuthenticationFilter(jwtProperty), UsernamePasswordAuthenticationFilter.class);
    }


}
