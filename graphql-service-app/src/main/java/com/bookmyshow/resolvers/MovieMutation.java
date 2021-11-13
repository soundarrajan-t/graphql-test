package com.bookmyshow.resolvers;

import com.bookmyshow.exceptions.MovieAlreadyExistsException;
import com.bookmyshow.exceptions.MovieNotAvailableException;
import com.bookmyshow.mappers.MovieMapper;
import com.bookmyshow.models.MovieDTO;
import com.bookmyshow.services.MovieService;
import graphql.kickstart.tools.GraphQLMutationResolver;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mapstruct.factory.Mappers;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Validated
@SuppressWarnings({"unused"})
public class MovieMutation implements GraphQLMutationResolver {

    private final MovieMapper mapper = Mappers.getMapper(MovieMapper.class);

    private final Logger log = LogManager.getLogger(MovieMutation.class);

    private final MovieService movieService;

    @PreAuthorize("hasRole('ADMIN')")
    public String createMovie(@Valid MovieDTO movieDTO) {
        log.info("Method createMovie is called");
        if (movieService.getMovie(movieDTO.getName()).isPresent())
            throw new MovieAlreadyExistsException("Movie Already Exists");
        return movieService.createMovie(mapper.movieDTOToMovie(movieDTO));
    }

    @PreAuthorize("hasRole('ADMIN')")
    public String deleteMovie(String name) {
        log.info("Method deleteMovie is called");
        if (movieService.getMovie(name).isPresent())
            return movieService.deleteMovieByName(name);
        throw new MovieNotAvailableException("Requested Movie is not available");
    }

    @PreAuthorize("hasRole('ADMIN')")
    public String updateMovie(@Valid MovieDTO movieDTO) {
        log.info("Method updateMovie is called");
        if (movieService.getMovie(movieDTO.getName()).isPresent())
            return movieService.updateMovie(mapper.movieDTOToMovie(movieDTO));
        throw new MovieNotAvailableException("Requested Movie is not available");
    }
}