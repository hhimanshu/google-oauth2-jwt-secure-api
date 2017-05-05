package com.hhimanshu.secure.api;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.hhimanshu.secure.auth.TokenVerifier;
import com.hhimanshu.secure.common.InvalidTokenException;
import java.io.IOException;
import java.security.GeneralSecurityException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/login")
public class Login {

  private static final String haritToken = "eyJhbGciOiJSUzI1NiIsImtpZCI6ImYwNDFmZDg5YzRhYmU4YzBiMzgxMWVlOWFmYTk3YjEyZDBkNjg3ZjcifQ.eyJhenAiOiIyMDU5MzYzNDM3OTQtOWhxMzNnM3VxZHJxa3JycXEyMGZuYTBrMDZjMnU5YnAuYXBwcy5nb29nbGV1c2VyY29udGVudC5jb20iLCJhdWQiOiIyMDU5MzYzNDM3OTQtOWhxMzNnM3VxZHJxa3JycXEyMGZuYTBrMDZjMnU5YnAuYXBwcy5nb29nbGV1c2VyY29udGVudC5jb20iLCJzdWIiOiIxMDAyMTY2OTY4MzI0NzA0MzE1MDciLCJlbWFpbCI6Imhhcml0LnN1YnNjcmlwdGlvbnNAZ21haWwuY29tIiwiZW1haWxfdmVyaWZpZWQiOnRydWUsImF0X2hhc2giOiJldUJ4Y3NJNjFqRWZIMjRFc0FId3pBIiwiaXNzIjoiYWNjb3VudHMuZ29vZ2xlLmNvbSIsImlhdCI6MTQ5Mzk1NzQ3OSwiZXhwIjoxNDkzOTYxMDc5LCJuYW1lIjoiSGFyaXQgSGltYW5zaHUiLCJwaWN0dXJlIjoiaHR0cHM6Ly9saDQuZ29vZ2xldXNlcmNvbnRlbnQuY29tLy1fbFhqMk9VbVRuZy9BQUFBQUFBQUFBSS9BQUFBQUFBQUFDTS9YYU5jMTJadGV5OC9zOTYtYy9waG90by5qcGciLCJnaXZlbl9uYW1lIjoiSGFyaXQiLCJmYW1pbHlfbmFtZSI6IkhpbWFuc2h1IiwibG9jYWxlIjoiZW4ifQ.BVXCpz1e14I4cptZEiuec6exhcUvzaiKKB9wGqCSD1qTJ1xSSPnTtXSNCmLv2VniUdGEbrkhfqqUGotjqC3gQaJFNuRd3q6loaMASwyLjAUwk_7iyx_IJKkquzgPCbZXn2SrG5F-zQ-scS0ek3E_XtX2l7mLY9n5xtx5iqNIe3Xl5wCRN5KYbvVx46u9HVCPlYb4Wh6ocVEK0z0qoD51eu47iKmMs3oV_qJ2cJEBj6cLSowdoPbYOI3pBwRvkQAknfZaIzRWWrqmvqpGwvmkN4h096m91swFJUjvJujn6Lmq0a4v5mQ8Rj_u1-dlSj8Qlho7WTUigL_rk6oRB3l04w";

  private TokenVerifier tokenVerifier;

  @Autowired
  public Login(TokenVerifier tokenVerifier) {
    this.tokenVerifier = tokenVerifier;
  }

  @RequestMapping(method = POST)
  public String authenticate(@RequestBody SecureToken secureToken)
      throws GeneralSecurityException, IOException {

    System.out.println("token = " + secureToken.getIdToken());
    final Payload payload;
    try {
      payload = tokenVerifier.verify(haritToken);
    } catch (InvalidTokenException e) {
      // TODO: 5/5/17 return HTTP 401
      return "Invalid Token for Harit";
    }

    // TODO: 5/5/17 Generate JWT Token for your APP and send in header
    return (String) payload.get("name");
  }
}

class SecureToken {
  private String idToken;
  // This needed to be public else getIdToken results in null
  public String getIdToken() {
    return idToken;
  }
}
