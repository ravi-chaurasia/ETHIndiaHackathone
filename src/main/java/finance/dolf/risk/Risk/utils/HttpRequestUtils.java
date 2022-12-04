package finance.dolf.risk.Risk.utils;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpRequest.BodyPublishers;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class HttpRequestUtils {
    private final HttpClient httpClient;

    public HttpRequestUtils() {
        httpClient = HttpClient.newHttpClient();
    }

    public <T> T getHttpClassResponse(String url, Class<T> wClass) throws Exception {
        HttpRequest httpRequest = createHttpRequestJson(url);
        try {
            log.info("Trying to send a request to {}, with repsonse type {}", url, wClass);
            return httpClient.send(httpRequest, new JsonBodyHandler<>(wClass)).body().get();
        } catch (IOException | InterruptedException e) {
            log.error("Requst to {} failed due to {}", url, e);
            throw e;
        }
    }

    public <T> T getHttpClassResponseWithApiKey(String url, String apiKey, Class<T> wClass) throws Exception {
        HttpRequest httpRequest = createHttpRequestWithApiKey(url, apiKey);
        try {
            log.info("Trying to send a request to {}, with repsonse type {}", url, wClass);
            return httpClient.send(httpRequest, new JsonBodyHandler<>(wClass)).body().get();
        } catch (IOException | InterruptedException e) {
            log.error("Requst to {} failed due to {}", url, e);
            throw e;
        }
    }

    private HttpRequest createHttpRequestJson(String url) {
        log.info("Creating httpRequest object for url {}", url);
        return HttpRequest.newBuilder(
                URI.create(url))
                .header("accept", "application/json")
                .build();

    }

    private HttpRequest createHttpRequestWithApiKey(String url, String apiKey) {
        log.info("Creating httpRequest object for url {}", url);
        return HttpRequest.newBuilder(
                URI.create(url))
                .headers("accept", "application/json", "X-Api-Key", apiKey)
                .build();

    }

    private HttpRequest createHttpPostRequestJson(String url, String postBody) {
        log.info("Creating http Post Request object for url {}", url);
        return HttpRequest.newBuilder(
                URI.create(url))
                .POST(BodyPublishers.ofString(postBody))
                .header("Content-type", "application/json")
                .build();
    }

    public <T> T getPostRequestHttpClassResponse(String url, String postBody, Class<T> wClass) throws Exception {
        HttpRequest httpRequest = createHttpPostRequestJson(url, postBody);

        try {
            log.info("Trying to send a request to {}, with body type {}", url, postBody);
            return httpClient.send(httpRequest, new JsonBodyHandler<>(wClass)).body().get();
        } catch (IOException | InterruptedException e) {
            log.error("Requst to {} failed due to {}", url, e);
            throw e;
        }
    }

}
