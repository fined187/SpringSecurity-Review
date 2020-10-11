package com.example.fined187.SpringSecurity.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
//  config 사용 선언.
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        인증
        http
                .authorizeRequests()
                .anyRequest()               //  어느 요청이 들어와도 인증이 되어야 함.
                .authenticated();

//        인증 방식.
        http
                .formLogin()               //   로그인을 통한 인증 방식.
//                .loginPage("/loginPage")
//                .defaultSuccessUrl("/defaultSuccessUrl")
                .failureUrl("/login")
                .usernameParameter("username")
                .passwordParameter("password")
                .successHandler((AuthenticationSuccessHandler) (httpServletRequest, httpServletResponse, authentication) -> {
                    System.out.println("login success");
                    httpServletResponse.sendRedirect("/");         //  defaultSuccessUrl로 리다이렉트
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
        http
                .rememberMe()
                .tokenValiditySeconds(3600)
                .userDetailsService(userDetailsService);

        http
                .sessionManagement()
                .maximumSessions(1)
                .maxSessionsPreventsLogin(true);           //  false default.

//        http
//                .sessionManagement()
//                .sessionFixation().changeSessionId();
    }
}