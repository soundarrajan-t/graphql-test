package com.bookmyshow.resolvers;

import com.bookmyshow.exceptions.MovieNotAvailableException;
import com.bookmyshow.models.MovieDTO;
import com.bookmyshow.services.MovieService;
import graphql.kickstart.tools.GraphQLQueryResolver;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;


@Component
@RequiredArgsConstructor
@SuppressWarnings({"unused"})
public class MovieQuery implements GraphQLQueryResolver {

    private final Logger log = LogManager.getLogger(MovieQuery.class);

    private final MovieService movieService;

    public Optional<MovieDTO> getMovieByName(String name) {
        log.info("Method getMovieByName is called");
        Optional<MovieDTO> movie = movieService.getMovie(name);
        if (movie.isPresent())
            return movie;
        log.error("Requested Movie with name {} is not available ", name);
        throw new MovieNotAvailableException("Requested Movie is not available");
    }

    public List<MovieDTO> getMovies() {
        log.info("Method getMovies is called");
        return movieService.getAllMovies();
    }

}