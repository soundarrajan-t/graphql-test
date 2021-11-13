package com.bookmyshow.gateways;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

@Setter
@Service
@Getter
@SuppressWarnings("unchecked")
public class OMDBWebClient {

    public WebClient webClient = WebClient.builder().build();

    public String omdbApi = System.getenv("OMDB_API");

    @Value("${omdb.url}")
    public String omdbUrl;

    public Map<String, String> getOMDBResponse(String movieName) {
        String uri = omdbUrl + "?t=" + movieName + "&plot=shot&apikey=" + omdbApi;
        return (Map<String, String>) webClient.get().uri(uri).retrieve().bodyToMono(Map.class).block();
    }

}