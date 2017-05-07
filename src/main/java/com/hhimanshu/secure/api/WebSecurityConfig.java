package com.hhimanshu.secure.api;

import com.hhimanshu.secure.auth.filters.LoginFilter;
import com.hhimanshu.secure.auth.filters.TokenFilter;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@EnableWebSecurity //(debug = true) // when you want to see what filters are applied
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

  @Override
  public void configure(HttpSecurity http) throws Exception {
    http.csrf().disable().authorizeRequests()
        .antMatchers("/css/**", "/js/**", "/images/**", "/static/**", "/**/favicon.ico").permitAll()
        .antMatchers(HttpMethod.POST, "/login").permitAll()
        .antMatchers("/").permitAll()
        .anyRequest().authenticated()
        .and()
        .addFilterBefore(new LoginFilter("/login"), BasicAuthenticationFilter.class)
        .addFilterBefore(new TokenFilter("/rest"), BasicAuthenticationFilter.class);
  }
}
