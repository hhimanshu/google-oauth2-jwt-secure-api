package com.hhimanshu.secure.auth.filters;

import static com.hhimanshu.secure.auth.AppTokenProviderAndAuthenticator.addAuthentication;
import static com.hhimanshu.secure.auth.AppTokenProviderAndAuthenticator.getUserFromToken;

import java.io.IOException;
import java.util.Optional;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class RestFilter implements Filter {

  @Override
  public void init(FilterConfig filterConfig) throws ServletException {
    System.out.println("init /rest/* filter");
  }

  @Override
  public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
      FilterChain filterChain) throws IOException, ServletException {

    HttpServletRequest request = (HttpServletRequest) servletRequest;
    HttpServletResponse response = (HttpServletResponse) servletResponse;

    System.out.println("Token Filter pathInfo:" + request.getRequestURI());

    Optional<String> userFromToken = getUserFromToken(request);
    if (!userFromToken.isPresent()) {
      response.sendError(HttpStatus.UNAUTHORIZED.value());
      return;
    }

    addAuthentication(response, userFromToken.get());
    filterChain.doFilter(request, servletResponse);
  }

  @Override
  public void destroy() {
  }
}
