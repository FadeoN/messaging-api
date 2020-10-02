package com.messaging.configs.security;

import com.messaging.services.UserService;
import com.messaging.utils.URLConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserService userService;

    @Autowired
    public WebSecurityConfig(UserService userService) {
        this.userService = userService;
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager customAuthenticationManager() throws Exception {
        return authenticationManager();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/index").permitAll()
                .antMatchers(String.format("%s%s", URLConstants.USERS_BASE_URL, URLConstants.USERS_REGISTER_URL)).permitAll()
                .antMatchers("/swagger-ui.html/**").hasRole("USER")
                .antMatchers(String.format("%s/blocked", URLConstants.USERS_BASE_URL)).hasRole("USER")
                .antMatchers(String.format("%s/**", URLConstants.USERS_BASE_URL)).hasRole("USER")
                .antMatchers(String.format("%s/**", URLConstants.CHAT_BASE_URL)).hasRole("USER")
                .antMatchers(String.format("%s", URLConstants.LOG_ACTIVITY_URL)).hasRole("USER")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage(String.format("%s%s", URLConstants.USERS_BASE_URL, URLConstants.USERS_LOGIN_URL))
                .permitAll()
                .and()
                .logout().deleteCookies("JSESSIONID")
                .and()
                .rememberMe();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(bCryptPasswordEncoder());
    }
}