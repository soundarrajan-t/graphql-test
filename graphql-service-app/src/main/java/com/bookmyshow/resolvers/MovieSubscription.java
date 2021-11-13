package com.bookmyshow.resolvers;

import com.bookmyshow.models.MovieDTO;
import com.bookmyshow.publisher.MoviePublisher;
import graphql.kickstart.tools.GraphQLSubscriptionResolver;
import lombok.RequiredArgsConstructor;
import org.reactivestreams.Publisher;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@SuppressWarnings("unused")
public class MovieSubscription implements GraphQLSubscriptionResolver {

    private final MoviePublisher moviePublisher;

    public Publisher<List<MovieDTO>> moviesByGenre(String genre) {
        return moviePublisher.getMoviesPublisherByGenre(genre);
    }

    public Publisher<List<MovieDTO>> movies() {
        return moviePublisher.getMoviesPublisher();
    }

}