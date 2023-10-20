package com.francogaldame.ochranaBank.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@EnableWebSecurity

@Configuration
public class WebAuthorization{

    @Bean

    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.authorizeRequests()
                .antMatchers("/api/login").permitAll()
                .antMatchers("/web/index.html","/web/css/**","/web/js/**","/web/img/**").permitAll()
                .antMatchers(HttpMethod.POST, "/api/clients").permitAll()
                .antMatchers("/web/accounts.html","/web/account.html","/web/cards.html","/web/create-cards.html",
                        "/web/transfers.html","/web/loan-application.html").hasAnyAuthority("CLIENT","ADMIN")
                .antMatchers("/api/clients/current").hasAnyAuthority("CLIENT","ADMIN")
                .antMatchers(HttpMethod.POST, "/api/clients/current/cards").hasAnyAuthority("CLIENT","ADMIN")
                .antMatchers(HttpMethod.GET, "/api/client-loans").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.GET, "/api/cards").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.POST, "/api/clients/current/accounts/approved").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.POST, "/api/clients/current/cards/approved").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.POST, "/api/loans/approved").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.POST, "/api/clients/current/accounts/delete").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.POST, "/api/clients/current/cards/delete").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.POST, "/api/loans/delete").hasAuthority("ADMIN")
                .antMatchers( "/api/clients/current/cards").hasAnyAuthority("CLIENT","ADMIN")
                .antMatchers( HttpMethod.GET,"/api/clients/current/accounts").hasAnyAuthority("CLIENT","ADMIN")
                .antMatchers(HttpMethod.POST, "/api/clients/current/accounts").hasAnyAuthority("CLIENT","ADMIN")
                .antMatchers(HttpMethod.POST, "/api/transactions").hasAnyAuthority("CLIENT","ADMIN")
                .antMatchers(HttpMethod.POST,"/api/accounts").hasAnyAuthority("ADMIN","CLIENT")
                .antMatchers(HttpMethod.GET,"/api/accounts").hasAnyAuthority("ADMIN","CLIENT")
                .antMatchers("/api/accounts/**").hasAnyAuthority("ADMIN","CLIENT")
                .antMatchers(HttpMethod.GET, "/api/loans").hasAnyAuthority("ADMIN","CLIENT")
                .antMatchers(HttpMethod.POST, "/api/loans").hasAnyAuthority("ADMIN","CLIENT")
                .antMatchers(HttpMethod.GET, "/api/clients").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.GET, "/api/clients/**").hasAuthority("ADMIN")
                .antMatchers("/rest/**").hasAuthority("ADMIN")
                .antMatchers("/api/clients").hasAuthority("ADMIN")
                .antMatchers("/web/manager-accounts.html","/web/manager-cards.html","/web/manager-clients.html",
                        "/web/manager-client.html", "/web/manager-loans.html").hasAuthority("ADMIN")
                .anyRequest().denyAll();

        http.formLogin()
                .usernameParameter("email")
                .passwordParameter("password")
                .loginPage("/api/login");

        http.logout().logoutUrl("/api/logout").deleteCookies("JSESSIONID");



        // turn off checking for CSRF tokens
        http.csrf().disable();

        //disabling frameOptions so h2-console can be accessed
        http.headers().frameOptions().disable();

        // if user is not authenticated, just send an authentication failure response
        http.exceptionHandling().authenticationEntryPoint((req, res, exc) -> res.sendError(HttpServletResponse.SC_UNAUTHORIZED));

        // if login is successful, just clear the flags asking for authentication
        http.formLogin().successHandler((req, res, auth) -> clearAuthenticationAttributes(req));

        // if login fails, just send an authentication failure response
        http.formLogin().failureHandler((req, res, exc) -> res.sendError(HttpServletResponse.SC_UNAUTHORIZED));

        // if logout is successful, just send a success response
        http.logout().logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler());

        return http.build();
    }

    private void clearAuthenticationAttributes(HttpServletRequest request) {

        HttpSession session = request.getSession(false);
        if (session != null) {
            session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
        }
    }
}
