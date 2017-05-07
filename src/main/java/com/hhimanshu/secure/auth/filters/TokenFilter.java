package com.hhimanshu.secure.auth.filters;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import org.springframework.web.filter.GenericFilterBean;

public class TokenFilter extends GenericFilterBean {

  private final String protectedUrlPath;

  public TokenFilter(String urlPath) {
    this.protectedUrlPath = urlPath;
  }

  @Override
  public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
      FilterChain filterChain) throws IOException, ServletException {

    HttpServletRequest request = (HttpServletRequest) servletRequest;

    // do not intercept non /rest requests
    if (!request.getRequestURI().contains(protectedUrlPath)) {
      filterChain.doFilter(servletRequest, servletResponse);
      return;
    }


    System.out.println("Token Filter pathInfo:" + request.getRequestURI());
  }
}
