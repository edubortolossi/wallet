package com.challenge.wallet.support.utils;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.HttpMethod.DELETE;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import com.challenge.wallet.support.LoadJsonFileUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
@Profile("container-test")
public class HttpCaller {

  public static final String PATH_FORMAT = "%s/%s/%s";
  public static final String URL = "http://localhost:8080";

  private final LoadJsonFileUtils loadJsonFileUtils;
  private final RestTemplate restTemplate;

  public ResponseEntity<String> post(
      final String endpoint, final String payload) {

    return restTemplate.postForEntity(URL + endpoint,
        new HttpEntity<>(payload, getHttpHeaders()), String.class);
  }

  public ResponseEntity<String> get(final String endpoint) {
    return restTemplate.exchange(URL + endpoint,
        GET, new HttpEntity<>(getHttpHeaders()), String.class);
  }

  public ResponseEntity<String> delete(final String endpoint) {
    return restTemplate.exchange(URL + endpoint,
        DELETE, new HttpEntity<>(getHttpHeaders()), String.class);
  }

  private HttpHeaders getHttpHeaders() {
    HttpHeaders httpHeaders = new HttpHeaders();
    httpHeaders.add(AUTHORIZATION, "any");
    httpHeaders.add(CONTENT_TYPE, APPLICATION_JSON_VALUE);
    return httpHeaders;
  }
}
