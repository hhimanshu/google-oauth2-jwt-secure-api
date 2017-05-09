package com.hhimanshu.secure.auth.filters;

import java.util.Collections;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Filters {

  @Bean
  public FilterRegistrationBean loginRegistrationBean() {
    FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
    filterRegistrationBean.setFilter(new LoginFilter());
    filterRegistrationBean.setUrlPatterns(Collections.singletonList("/login/*"));
    return filterRegistrationBean;
  }

  @Bean
  public FilterRegistrationBean restRegistrationBean() {
    FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
    filterRegistrationBean.setFilter(new RestFilter());
    filterRegistrationBean.setUrlPatterns(Collections.singletonList("/rest/*"));
    return filterRegistrationBean;
  }
}
