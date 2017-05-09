package com.hhimanshu.secure;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.anyString;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.hhimanshu.secure.auth.GoogleTokenVerifier;
import com.hhimanshu.secure.common.InvalidTokenException;
import java.util.UUID;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class SecureCommunicationWorkflowTest {

  @Autowired
  private TestRestTemplate restTemplate;

  @LocalServerPort
  private int port;

  @MockBean
  private GoogleTokenVerifier googleTokenVerifier;

  @Test
  public void whenGoogleLoginFailsShouldReturnUnauthorized() throws Exception {
    given(this.googleTokenVerifier.verify(anyString())).willThrow(new InvalidTokenException("foo"));

    String url = "http://localhost:" + port + "/login";
    ResponseEntity<String> response = this.restTemplate.postForEntity(url, new HttpEntity<>(""), String.class);
    assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
  }

  @Test
  public void getProtectedResourceWhenGoogleLoginPassesShouldReturnData() throws Exception {

    final String authToken;
    // login with google, get IdToken and return AuthHeader
    {
      final Payload payload = new Payload();
      // setting up random google user
      payload.setSubject(UUID.randomUUID().toString());
      given(googleTokenVerifier.verify(anyString())).willReturn(payload);

      String url = "http://localhost:" + port + "/login";
      HttpHeaders headers = new HttpHeaders();
      headers.add("X-ID-TOKEN", "SecureIdTokenFromGoogle");
      ResponseEntity<String> response = this.restTemplate.postForEntity(url, new HttpEntity<>(headers), String.class);
      assertEquals(HttpStatus.OK, response.getStatusCode());

      HttpHeaders responseHeaders = response.getHeaders();
      assertTrue(responseHeaders.containsKey("Authorization"));

      authToken = responseHeaders.getFirst("Authorization");

    }

    // request protected resource using authToken
    {
      assertNotNull(authToken);
      String url = "http://localhost:" + port + "/rest/tweets";

      final HttpHeaders headers = new HttpHeaders();
      headers.add("Authorization", "Bearer " + authToken);
      ResponseEntity<String> response = this.restTemplate
          .exchange(url, HttpMethod.GET, new HttpEntity<>(headers), String.class);

      assertEquals(HttpStatus.OK, response.getStatusCode());
      assertEquals("My Tweets!", response.getBody());

      final HttpHeaders responseHeaders = response.getHeaders();
      assertTrue(responseHeaders.containsKey("Authorization"));
      // On each request a new Auth Token will be generated
      assertNotEquals(authToken, responseHeaders.getFirst("Authorization"));
    }
  }
}
