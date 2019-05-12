package com.zappos.backoffice.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
           .antMatchers("/css/**")
           .antMatchers("/images/**")
           .antMatchers("/upload/**")
           .antMatchers("/webjars/**")
           .antMatchers("/service/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.authorizeRequests()
            .antMatchers("/view").permitAll()
            .antMatchers("/upload/**").permitAll()
            .antMatchers("/service/v1/**").permitAll()
            .antMatchers("/v2/api-doc/**").permitAll();
    }

}
