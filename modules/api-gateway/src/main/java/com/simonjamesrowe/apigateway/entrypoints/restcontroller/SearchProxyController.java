package com.simonjamesrowe.apigateway.entrypoints.restcontroller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;

@RestController
@RequestMapping("/search")
@RequiredArgsConstructor
public class SearchProxyController {

    private final RestClient restClient;

    @Value("${search.url}")
    private String searchUrl;

    @GetMapping("/**")
    public ResponseEntity<String> proxySearchRequest(HttpServletRequest request) {
        // Strip the /search prefix
        String path = request.getRequestURI().substring("/search".length());
        String queryString = request.getQueryString();
        String fullUrl = searchUrl + path + (queryString != null ? "?" + queryString : "");

        return restClient.get()
                .uri(fullUrl)
                .retrieve()
                .toEntity(String.class);
    }
}
