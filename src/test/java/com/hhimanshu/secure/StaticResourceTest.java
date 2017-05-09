package com.hhimanshu.secure;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class StaticResourceTest {

  @Autowired
  private TestRestTemplate restTemplate;

  @LocalServerPort
  private int port;

  @Test
  public void getHomePageReturnsHtmlData() throws Exception {
    final String url = "http://localhost:" + port;

    ResponseEntity<String> responseEntity = this.restTemplate.getForEntity(url, String.class);
    assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    assertThat(responseEntity.getBody()).contains("Google Sign In");
  }
}