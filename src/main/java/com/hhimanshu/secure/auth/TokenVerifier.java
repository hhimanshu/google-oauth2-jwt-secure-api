package com.hhimanshu.secure.auth;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.hhimanshu.secure.common.InvalidTokenException;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.Collections;
import org.springframework.stereotype.Component;

@Component
public class TokenVerifier {

  private static final HttpTransport transport = new NetHttpTransport();
  private static final JsonFactory jsonFactory = new JacksonFactory();
  private static final String CLIENT_ID = "205936343794-9hq33g3uqdrqkrrqq20fna0k06c2u9bp.apps.googleusercontent.com";


  public Payload verify(String idTokenString)
      throws GeneralSecurityException, IOException, InvalidTokenException {
    final GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.
        Builder(transport, jsonFactory)
        .setIssuers(Arrays.asList("https://accounts.google.com", "accounts.google.com"))
        .setAudience(Collections.singletonList(CLIENT_ID))
        .build();


    System.out.println("validating:" + idTokenString);
    GoogleIdToken idToken = verifier.verify(idTokenString);

    if (idToken == null) {
      throw new InvalidTokenException("idToken is invalid");
    }

    Payload payload = idToken.getPayload();

    // fetch more attributes based on payload
    System.out.println("payload:" + payload);

    return payload;
  }
}
