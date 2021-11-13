package com.bookmyshow.gateways;

import okhttp3.HttpUrl;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.io.IOException;
import java.util.Map;

import static com.bookmyshow.test.data.ResourceReader.readFromResource;
import static com.bookmyshow.test.data.ResponseBuilder.getResponse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {OMDBWebClient.class})
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class OMDBMockWebServerTest {

    public static MockWebServer mockWebServer;
    @Autowired
    public OMDBWebClient omdbWebClient;
    @Value("${omdb.test.url}")
    private String omdbTestUrl;

    @BeforeAll
    static void setUp() throws IOException {
        mockWebServer = new MockWebServer();
        mockWebServer.start();
    }

    @AfterAll
    static void tearDown() throws IOException {
        mockWebServer.shutdown();
    }

    @Test
    void shouldRReturnExpectedResultWhenExternalAPIIsCalled() throws Exception {
        Map<String, String> expectedResult = getResponse("Harry");
        HttpUrl url = mockWebServer.url(omdbTestUrl);
        String inputResponse = readFromResource("omdb_response/movies.json");
        mockWebServer.enqueue(new MockResponse()
                .setBody(inputResponse)
                .setResponseCode(200)
                .addHeader("Content-Type", "application/json"));

        omdbWebClient.omdbUrl = (url.toString());
        Map<String, String> actualResult = omdbWebClient.getOMDBResponse("Harry");

        int actualRequestCount = mockWebServer.getRequestCount();
        assertEquals(1, actualRequestCount);
        Mono<Map<String, String>> mapMono = Mono.just(actualResult);
        StepVerifier.create(mapMono)
                .expectNext(expectedResult)
                .verifyComplete();
        assertEquals(expectedResult, actualResult);
        RecordedRequest recordedRequest = mockWebServer.takeRequest();
        assertEquals("GET", recordedRequest.getMethod());
        String requestMethod = recordedRequest.getPath();
        assertTrue(requestMethod.contains("Harry"));
    }
}