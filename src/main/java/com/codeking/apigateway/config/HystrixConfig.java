package com.codeking.apigateway.config;

import org.springframework.cloud.netflix.zuul.filters.route.FallbackProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

@Configuration
public class HystrixConfig {

    private static final String SERVICE_DOWN_MESSAGE = "{\"message\": \"Sorry, Service is down please try again later\"}";

    private ClientHttpResponse errorResponse = new ClientHttpResponse() {
        @Override
        public HttpStatus getStatusCode() {
            return HttpStatus.SERVICE_UNAVAILABLE;
        }

        @Override
        public int getRawStatusCode() {
            return HttpStatus.SERVICE_UNAVAILABLE.value();
        }

        @Override
        public String getStatusText() {
            return HttpStatus.SERVICE_UNAVAILABLE.toString();
        }

        @Override
        public void close() {

        }

        @Override
        public InputStream getBody() {
            return new ByteArrayInputStream(SERVICE_DOWN_MESSAGE.getBytes());
        }

        @Override
        public HttpHeaders getHeaders() {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            return headers;
        }
    };

    @Bean
    public FallbackProvider userServiceFallbackProvider() {
        return new FallbackProvider() {
            @Override
            public String getRoute() {
                return "userService";
            }

            @Override
            public ClientHttpResponse fallbackResponse(String route, Throwable cause) {
                return errorResponse;
            }

        };
    }
}
