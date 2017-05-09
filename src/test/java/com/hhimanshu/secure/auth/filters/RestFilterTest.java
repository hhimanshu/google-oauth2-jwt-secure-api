package com.hhimanshu.secure.auth.filters;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class RestFilterTest {

  @Autowired
  private TestRestTemplate restTemplate;

  @LocalServerPort
  private int port;

  @Test
  public void testGetWhenNoSecurityHeaderShouldReturnUnauthorized() throws Exception {
    String url = "http://localhost:" + port + "/rest/hello";
    ResponseEntity<String> response = this.restTemplate.getForEntity(url, String.class);
    assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
  }

  @Test
  public void testGetWhenAuthTokenAvailableInRequestShouldReturnData() throws Exception {
    final String token = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJteVVuaXF1ZVVzZXIiLCJleHAiOjE0OTUxNjEwNjZ9.fT1jC_JJKlq_vCs_EyEQTKfUqZq9yTp7cqmO5EdZ3mp9uYjrkyKQfZ4PvS02Q8I69p9THIpnCPw_iE7QPO2jYA";
    String url = "http://localhost:" + port + "/rest/hello";
    final HttpHeaders headers = new HttpHeaders();
    headers.add("Authorization", "Bearer " + token);
    ResponseEntity<String> response = this.restTemplate
        .exchange(url, HttpMethod.GET, new HttpEntity<>(headers), String.class);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals("Hello World!", response.getBody());
  }
}