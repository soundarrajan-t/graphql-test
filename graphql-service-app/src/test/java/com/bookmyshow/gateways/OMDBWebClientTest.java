package com.bookmyshow.gateways;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Map;

import static com.bookmyshow.test.data.ResponseBuilder.getResponse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = OMDBWebClient.class)
@SuppressWarnings("unchecked")
public class OMDBWebClientTest {

    @Autowired
    private OMDBWebClient omdbWebClient;

    @Mock
    private WebClient webClientMock;

    @SuppressWarnings("rawtypes")
    @Mock
    private WebClient.RequestHeadersSpec requestHeadersMock;

    @SuppressWarnings("rawtypes")
    @Mock
    private WebClient.RequestHeadersUriSpec requestHeadersUriMock;

    @Mock
    private WebClient.ResponseSpec responseMock;

    @Test
    void shouldReturnExpectedResultWhenExternalAPIIsCalled() {
        Map<String, String> expectedResult = getResponse("Harry");
        when(webClientMock.get()).thenReturn(requestHeadersUriMock);
        when(requestHeadersUriMock.uri(anyString())).thenReturn(requestHeadersMock);
        when(requestHeadersMock.retrieve()).thenReturn(responseMock);
        when(responseMock.bodyToMono(Map.class)).thenReturn(Mono.just(expectedResult));

        omdbWebClient.webClient = webClientMock;
        Map<String, String> actualResult = omdbWebClient.getOMDBResponse("Harry");

        verify(webClientMock).get();
        Mono<Map<String, String>> mapMono = Mono.just(actualResult);
        StepVerifier.create(mapMono)
                .expectNext(expectedResult)
                .verifyComplete();
        assertEquals(expectedResult, actualResult);

    }
}