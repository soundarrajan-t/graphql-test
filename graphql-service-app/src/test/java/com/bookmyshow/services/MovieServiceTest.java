package com.bookmyshow.services;

import com.bookmyshow.gateways.OMDBWebClient;
import com.bookmyshow.models.Movie;
import com.bookmyshow.models.MovieDTO;
import com.bookmyshow.publisher.MoviePublisher;
import com.bookmyshow.repositories.MovieRepository;
import com.bookmyshow.services.MovieService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.bookmyshow.test.data.MovieBuilder.createMovie;
import static com.bookmyshow.test.data.MovieBuilder.updateMovie;
import static com.bookmyshow.test.data.MovieDTOBuilder.createMovieDTO;
import static com.bookmyshow.test.data.ResponseBuilder.getResponse;
import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = MovieService.class)
@SuppressWarnings({"unused"})
public class MovieServiceTest {

    @Autowired
    private MovieService movieService;

    @MockBean
    private OMDBWebClient omdbWebClient;

    @MockBean
    private MovieRepository movieRepository;

    @MockBean
    private MoviePublisher moviePublisher;

    @Test
    public void shouldReturnValidMovieListForGetAllMovies() {
        Movie firstSampleMovie = createMovie("The Avengers");
        Movie secondSampleMovie = createMovie("Harry");
        MovieDTO firstSampleMovieDTO = createMovieDTO("The Avengers");
        MovieDTO secondSampleMovieDTO = createMovieDTO("Harry");
        Map<String, String> firstResponse = getResponse("The Avengers");
        Map<String, String> secondResponse = getResponse("Harry");

        when(omdbWebClient.getOMDBResponse("The Avengers")).thenReturn(firstResponse);
        when(omdbWebClient.getOMDBResponse("Harry")).thenReturn(secondResponse);
        when(movieRepository.findAll()).thenReturn(asList(firstSampleMovie, secondSampleMovie));

        List<MovieDTO> movieDTOs = movieService.getAllMovies();

        verify(movieRepository).findAll();
        assertThat(movieDTOs.size(), is(2));
        assertThat(movieDTOs, is(asList(firstSampleMovieDTO, secondSampleMovieDTO)));
    }

    @Test
    public void shouldReturnValidMovieForGetMovieByName() {
        Movie expectedMovie = createMovie("The Avengers");
        MovieDTO expectedMovieDTO = createMovieDTO("The Avengers");

        when(movieRepository.findByName("The Avengers")).thenReturn(Optional.of(expectedMovie));

        Optional<MovieDTO> actualMovie = movieService.getMovie("The Avengers");

        verify(movieRepository).findByName("The Avengers");
        assertNotNull(actualMovie);
        assertThat(actualMovie, is(Optional.of(expectedMovieDTO)));
    }

    @Test
    public void shouldReturnSavedMovieNameForCreateMovie() {
        Movie expectedMovie = createMovie("The Avengers");

        when(movieRepository.save(expectedMovie)).thenReturn(expectedMovie);

        String actualMovieName = movieService.createMovie(expectedMovie);

        verify(movieRepository).save(expectedMovie);
        assertThat(actualMovieName, is(expectedMovie.getName()));
    }

    @Test
    public void shouldDeleteMovieForDeleteMovieWithName() {
        doNothing().when(movieRepository).deleteByName("The Avengers");

        String deletedMovieName = movieService.deleteMovieByName("The Avengers");

        verify(movieRepository).deleteByName("The Avengers");
        assertThat(deletedMovieName, is("The Avengers"));
    }

    @Test
    public void shouldReturnUpdatedMovieNameForUpdateMovie() {
        Movie expectedMovie = updateMovie("The Avengers");

        when(movieRepository.findByName("The Avengers")).thenReturn(Optional.of(expectedMovie));

        String actualMovieName = movieService.updateMovie(expectedMovie);

        verify(movieRepository).findByName("The Avengers");
        assertNotNull(actualMovieName);
        assertThat(actualMovieName, is(expectedMovie.getName()));
    }

    @Test
    public void shouldAddRatingValueForValidMovieName() {
        MovieDTO inputMovieDTO = createMovieDTO("The Avengers");
        Map<String, String> result = getResponse("The Avengers");

        when(omdbWebClient.getOMDBResponse("The Avengers")).thenReturn(result);

        MovieDTO movieDTO = movieService.addRatingTo(inputMovieDTO);

        verify(omdbWebClient).getOMDBResponse("The Avengers");
        assertNotNull(movieDTO.getRating());
        assertEquals(0.0, movieDTO.getRating());
    }

    @Test
    public void shouldAddZeroRatingForUnValidMovieName() {
        MovieDTO inputMovieDTO = createMovieDTO("Harry");
        Map<String, String> result = getResponse("Harry");

        when(omdbWebClient.getOMDBResponse("Harry")).thenReturn(result);

        MovieDTO movieDTO = movieService.addRatingTo(inputMovieDTO);

        verify(omdbWebClient).getOMDBResponse("Harry");
        assertNotNull(movieDTO.getRating());
        assertEquals(0.0, movieDTO.getRating());
    }

    @Test
    public void shouldAddRatingValueForAllMovies() {
        Map<String, String> firstResponse = getResponse("The Avengers");
        Map<String, String> secondResponse = getResponse("Harry");
        MovieDTO firstSampleMovieDTO = createMovieDTO("The Avengers");
        MovieDTO secondSampleMovieDTO = createMovieDTO("Harry");
        List<MovieDTO> movieDTOS = asList(firstSampleMovieDTO, secondSampleMovieDTO);

        when(omdbWebClient.getOMDBResponse("The Avengers")).thenReturn(firstResponse);
        when(omdbWebClient.getOMDBResponse("Harry")).thenReturn(secondResponse);

        movieDTOS = movieService.addRatingTo(movieDTOS);

        for (MovieDTO movieDTO : movieDTOS) {
            verify(omdbWebClient).getOMDBResponse(movieDTO.getName());
            assertNotNull(movieDTO.getRating());
        }
        assertEquals(0.0, movieDTOS.get(0).getRating());
        assertEquals(0.0, movieDTOS.get(1).getRating());
    }

    @Test
    void shouldReturnEmptyMovieWhenMovieIsNotAvailable() {
        String movieName = "Cinema";

        when(movieRepository.findByName(movieName)).thenReturn(Optional.empty());

        assertThat(movieService.getMovie(movieName), is(Optional.empty()));
    }
}