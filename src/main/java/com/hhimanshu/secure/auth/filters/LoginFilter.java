package com.hhimanshu.secure.auth.filters;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.hhimanshu.secure.auth.AppTokenProviderAndAuthenticator;
import com.hhimanshu.secure.auth.GoogleTokenVerifier;
import com.hhimanshu.secure.common.InvalidTokenException;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Objects;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.filter.GenericFilterBean;

public class LoginFilter extends GenericFilterBean {

  private final String urlPathToFilter;
  public LoginFilter(String url) {
    urlPathToFilter = url;
  }

  @Override
  public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
      FilterChain filterChain) throws IOException, ServletException {

    HttpServletRequest request = (HttpServletRequest) servletRequest;

    // do not intercept non /login requests
    if(!Objects.equals(urlPathToFilter, request.getRequestURI())) {
      filterChain.doFilter(servletRequest, servletResponse);
      return;
    }

    String idToken = ((HttpServletRequest) servletRequest).getHeader("X-ID-TOKEN");
    HttpServletResponse response = (HttpServletResponse) servletResponse;
    System.out.println("filtered /login request: " + idToken);

    if (idToken != null) {
      final Payload payload;
      try {
        payload = GoogleTokenVerifier.verify(idToken);
        if (payload != null) {
          // TODO: 5/6/17 get this username from DB (createOrGet)
          String username = "myUniqueUser";
          AppTokenProviderAndAuthenticator.addAuthentication(response, username);
          filterChain.doFilter(servletRequest, response);
          return;
        }
      } catch (GeneralSecurityException | InvalidTokenException e) {
        // This is not a valid token, we will send HTTP 401 back
      }
    }
    ((HttpServletResponse) servletResponse).sendError(HttpServletResponse.SC_UNAUTHORIZED);
  }
}
