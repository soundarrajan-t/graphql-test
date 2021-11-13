package com.bookmyshow.repositories;

import com.bookmyshow.models.Movie;
import com.bookmyshow.repositories.MovieRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.Optional;

import static com.bookmyshow.test.data.MovieBuilder.createMovie;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
public class MovieRepositoryTest {

    @Autowired
    @SuppressWarnings("unused")
    private MovieRepository movieRepository;

    @Test
    public void shouldReturnInsertedMovieWhenSaveMethodIsCalled() {
        Movie movie = createMovie("The Avengers");

        Movie savedMovie = movieRepository.save(movie);

        assertNotNull(savedMovie);
        assertNotNull(savedMovie.getId());
        assertEquals(movie, savedMovie);
    }

    @Test
    @Sql(scripts = {"classpath:V2__Insert_Movie_Data_Test.sql"})
    public void shouldReturnMoviesWhenFindALlMethodIsCalled() {
        Movie firstSampleMovie = createMovie("The Avengers");
        Movie secondSampleMovie = createMovie("Cinderella");

        List<Movie> moviesList = movieRepository.findAll();

        assertThat(moviesList.size(), is(2));
        assertEquals(firstSampleMovie, moviesList.get(0));
        assertEquals(secondSampleMovie, moviesList.get(1));
    }

    @Test
    public void shouldReturnValidMovieWhenFindByNameIsCalled() {
        Movie movie = createMovie("The Avengers");

        Movie savedMovie = movieRepository.save(movie);
        Optional<Movie> actualMovie = movieRepository.findByName(savedMovie.getName());

        assertThat(actualMovie.isPresent(), is(true));
        assertEquals(movie, actualMovie.get());
    }

    @Test
    public void shouldDeleteValidMovieWhenDeleteByNameIsCalled() {
        Movie movie = createMovie("The Avengers");

        Movie savedMovie = movieRepository.save(movie);
        movieRepository.deleteByName(savedMovie.getName());
        Optional<Movie> actualMovie = movieRepository.findByName(savedMovie.getName());

        assertThat(actualMovie, is(Optional.empty()));
    }

}
