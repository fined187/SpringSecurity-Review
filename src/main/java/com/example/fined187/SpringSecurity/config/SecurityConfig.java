package com.example.fined187.SpringSecurity.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity                  //  config 사용 선언.
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        인증
        http
                .authorizeRequests()
                .anyRequest()
                .authenticated();

//        인증 방식.
        http
                .formLogin()
//                .loginPage("/loginPage")
//                .defaultSuccessUrl("/defaultSuccessUrl")
                .failureUrl("/login")
                .usernameParameter("username")
                .passwordParameter("password")
                .successHandler((AuthenticationSuccessHandler) (httpServletRequest, httpServletResponse, authentication) -> {
                    System.out.println("login success");
                    httpServletResponse.sendRedirect("/defaultSuccessUrl");         //  defaultSuccessUrl로 리다이렉트
                })
                .failureHandler(((httpServletRequest, httpServletResponse, e) -> {
                    System.out.println("login fail");
                    httpServletResponse.sendRedirect("login");                      //  login 페이지 호출.(리다이렉트)
                }))
                .permitAll();

        http
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login")
                .deleteCookies("sample", "cookies")
                .logoutSuccessHandler(
                        (httpServletRequest, httpServletResponse, authentication) ->
                                System.out.println("logout saved")
                );
    }
}
