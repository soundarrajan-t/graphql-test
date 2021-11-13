package com.bookmyshow.resolvers;

import com.bookmyshow.exceptionhandler.CustomGraphQLErrorHandler;
import com.bookmyshow.models.Movie;
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
import java.util.Optional;

import static com.bookmyshow.test.data.MovieBuilder.createMovie;
import static com.bookmyshow.test.data.MovieBuilder.updateMovie;
import static com.bookmyshow.test.data.MovieDTOBuilder.createMovieDTO;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@GraphQLTest
@ContextConfiguration(classes = CustomGraphQLErrorHandler.class)
@SuppressWarnings({"unused"})
public class MovieMutationTest {

    @Autowired
    private GraphQLTestTemplate graphQLTestTemplate;

    @MockBean
    private MovieService movieService;

    @MockBean
    private UserService userService;

    @MockBean
    private MoviePublisher moviePublisher;

    @Test
    public void shouldReturnValidMovieAfterCreatingMovie() throws Exception {
        Movie expectedMovie = createMovie("The Avengers");
        when(movieService.createMovie(any())).thenReturn(expectedMovie.getName());

        GraphQLResponse response = graphQLTestTemplate.postForResource("/graphql/request/mutation_create_movie.graphql");

        verify(movieService).createMovie(any());
        assertNotNull(response);
        assertThat(response.isOk()).isTrue();
        assertThat(response.get("$.data.createMovie")).isEqualTo("The Avengers");
    }

    @Test
    public void shouldDeleteMovieWithDeleteMovieMutation() throws Exception {
        MovieDTO expectedMovie = createMovieDTO("The Avengers");
        when(movieService.deleteMovieByName("The Avengers")).thenReturn("The Avengers");
        when(movieService.getMovie("The Avengers")).thenReturn(Optional.of(expectedMovie));

        GraphQLResponse response = graphQLTestTemplate.postForResource("/graphql/request/mutation_delete_movie.graphql");

        verify(movieService).deleteMovieByName("The Avengers");
        verify(movieService).getMovie("The Avengers");
        assertNotNull(response);
        assertThat(response.isOk()).isTrue();
        assertThat(response.get("$.data.deleteMovie")).isEqualTo("The Avengers");
    }

    @Test
    void shouldThrowExceptionForInvalidNameInDeleteMovieMutation() throws IOException {
        when(movieService.getMovie("Hello")).thenReturn(Optional.empty());

        GraphQLResponse response = graphQLTestTemplate.postForResource("/graphql/request/mutation_delete_movie_by_name_exception.graphql");

        verify(movieService).getMovie("Hello");
        assertNotNull(response);
        assertThat(response.isOk()).isTrue();
        assertThat(response.get("$.errors[0].message")).contains("Requested Movie is not available");
        assertThat(response.get("$.errors[0].extensions.errorCode")).isEqualTo("CAN_NOT_FETCH_MOVIE_BY_NAME");

    }

    @Test
    void shouldThrowExceptionIfMovieAlreadyExistsInCreateMovieMutation() throws IOException {
        MovieDTO expectedMovie = createMovieDTO("The Avengers");

        when(movieService.getMovie("The Avengers")).thenReturn(Optional.of(expectedMovie));

        GraphQLResponse response = graphQLTestTemplate.postForResource("/graphql/request/mutation_create_movie.graphql");

        verify(movieService).getMovie("The Avengers");
        assertNotNull(response);
        assertThat(response.isOk()).isTrue();
        assertThat(response.get("$.errors[0].message")).contains("Movie Already Exists");
        assertThat(response.get("$.errors[0].extensions.errorCode")).isEqualTo("MOVIE_WITH_NAME_ALREADY_EXISTS");
    }

    @Test
    void shouldThrowExceptionForInvalidNameInUpdateMovie() throws IOException {
        when(movieService.getMovie("Hello")).thenReturn(Optional.empty());

        GraphQLResponse response = graphQLTestTemplate.postForResource("/graphql/request/mutation_update_movie_exception.graphql");

        verify(movieService).getMovie("Hello");
        assertNotNull(response);
        assertThat(response.isOk()).isTrue();
        assertThat(response.get("$.errors[0].message")).contains("Requested Movie is not available");
        assertThat(response.get("$.errors[0].extensions.errorCode")).isEqualTo("CAN_NOT_FETCH_MOVIE_BY_NAME");
    }

    @Test
    void shouldThrowExceptionIfThereIsValidationErrorInInputQuery() throws IOException {
        GraphQLResponse response = graphQLTestTemplate.postForResource("/graphql/request/query_movie_validation_error.graphql");

        assertNotNull(response);
        assertThat(response.isOk()).isTrue();
        assertThat(response.get("$.errors[0].message")).contains("Validation error");
        assertThat(response.get("$.errors[0].extensions.errorCode")).isEqualTo("QUERY_VALIDATION_ERROR");
    }

    @Test
    void shouldThrowExceptionIfCertificateFieldIsEmptyInInputQuery() throws IOException {
        GraphQLResponse response = graphQLTestTemplate.postForResource("/graphql/request/mutation_movie_illegal_argument.graphql");

        assertNotNull(response);
        assertThat(response.isOk()).isTrue();
        assertThat(response.get("$.errors[0].message")).isEqualTo("certification: [A, UA, U] any one of these Required");
        assertThat(response.get("$.errors[0].extensions.errorCode")).isEqualTo("ILLEGAL_ARGUMENT");
    }

}