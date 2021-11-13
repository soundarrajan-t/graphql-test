package com.bookmyshow.resolvers;

import com.bookmyshow.exceptionhandler.CustomGraphQLErrorHandler;
import com.bookmyshow.models.MovieDTO;
import com.bookmyshow.publisher.MoviePublisher;
import com.bookmyshow.services.MovieService;
import com.bookmyshow.services.UserService;
import com.graphql.spring.boot.test.GraphQLResponse;
import com.graphql.spring.boot.test.GraphQLTest;
import com.graphql.spring.boot.test.GraphQLTestTemplate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;

import java.io.IOException;
import java.util.Collections;
import java.util.Optional;

import static com.bookmyshow.test.data.MovieDTOBuilder.createMovieDTO;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@GraphQLTest
@ContextConfiguration(classes = CustomGraphQLErrorHandler.class)
@SuppressWarnings({"unused"})
public class MovieQueryTest {

    @Autowired
    private GraphQLTestTemplate graphQLTestTemplate;

    @MockBean
    private MovieService movieService;

    @MockBean
    private UserService userService;

    @MockBean
    private MoviePublisher moviePublisher;

    @Test
    public void shouldReturnValidMovieListForMoviesQuery() throws Exception {
        MovieDTO expectedMovie = createMovieDTO("The Avengers");

        when(movieService.getAllMovies()).thenReturn(Collections.singletonList(expectedMovie));

        GraphQLResponse response = graphQLTestTemplate.postForResource("/graphql/request/query_all_movies_test.graphql");

        verify(movieService).getAllMovies();
        assertNotNull(response);
        assertThat(response.isOk()).isTrue();
        assertThat(response.get("$.data.movies[0].name")).isEqualTo("The Avengers");
        assertThat(response.get("$.data.movies[0].duration")).isEqualTo("180");
    }

    @Test
    void shouldThrowExceptionForInvalidMovieByNameQuery() throws IOException {
        when(movieService.getMovie("Hello")).thenReturn(Optional.empty());

        GraphQLResponse response = graphQLTestTemplate.postForResource("/graphql/request/query_movie_by_name_exception_test.graphql");

        verify(movieService).getMovie("Hello");
        assertNotNull(response);
        assertThat(response.isOk()).isTrue();
        assertThat(response.get("$.errors[0].message")).contains("Requested Movie is not available");
    }

    @Test
    public void shouldReturnValidMovieForMovieByNameQuery() throws Exception {
        MovieDTO expectedMovie = createMovieDTO("The Avengers");

        when(movieService.getMovie("The Avengers")).thenReturn(Optional.of(expectedMovie));

        GraphQLResponse response = graphQLTestTemplate.postForResource("/graphql/request/query_movie_by_name_test.graphql");

        verify(movieService).getMovie("The Avengers");
        assertNotNull(response);
        assertThat(response.isOk()).isTrue();
        assertThat(response.get("$.data.movieByName.name")).isEqualTo("The Avengers");
        assertThat(response.get("$.data.movieByName.duration")).isEqualTo("180");
    }

}