package com.bookmyshow.mappers;

import com.bookmyshow.mappers.MovieMapper;
import com.bookmyshow.models.Movie;
import com.bookmyshow.models.MovieDTO;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.List;

import static com.bookmyshow.models.Certification.U;
import static com.bookmyshow.test.data.MovieBuilder.createMovie;
import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class MovieMapperTest {

    private final MovieMapper mapper = Mappers.getMapper(MovieMapper.class);

    @Test
    void shouldReturnMovieDTOForMovie() {
        Movie firstSampleMovie = createMovie("The Avengers");

        MovieDTO movieDTO = mapper.movieToMovieDTO(firstSampleMovie);

        assertMovieAndMovieDTO(firstSampleMovie, movieDTO);
    }

    @Test
    void shouldReturnMovieDTOListForMovieList() {
        Movie firstSampleMovie = createMovie("The Avengers");
        Movie secondSampleMovie = createMovie("Cinderella");

        List<Movie> movieList = asList(firstSampleMovie, secondSampleMovie);
        List<MovieDTO> movieDTOList = mapper.movieToMovieDTOList(movieList);

        for (int i = 0; i < movieList.size(); i++)
            assertMovieAndMovieDTO(movieList.get(i), movieDTOList.get(i));
    }

    @Test
    void shouldReturnMovieForMovieDTO() {
        MovieDTO sampleMovieDTO = MovieDTO.builder()
                .name("avengers")
                .description("marvel")
                .cast("Chris Evans")
                .certification(U)
                .genre("action/sci-fic")
                .duration(180).build();

        Movie movie = mapper.movieDTOToMovie(sampleMovieDTO);

        assertMovieAndMovieDTO(movie, sampleMovieDTO);
    }

    @Test
    void shouldReturnMovieDTOIsNullIfMovieIsNull() {
        Movie movie = null;

        @SuppressWarnings("ConstantConditions")
        MovieDTO movieDTO = mapper.movieToMovieDTO(movie);

        assertNull(movieDTO);
    }

    @Test
    void shouldReturnMovieDTOListIsNullIfMovieListIsNull() {
        List<Movie> movieList = null;

        @SuppressWarnings("ConstantConditions")
        List<MovieDTO> movieDTOList = mapper.movieToMovieDTOList(movieList);

        assertNull(movieDTOList);
    }

    @Test
    void shouldReturnMovieIsNullIfMovieDTOIsNull() {
        MovieDTO movieDTO = null;

        @SuppressWarnings("ConstantConditions")
        Movie movie = mapper.movieDTOToMovie(movieDTO);

        assertNull(movie);
    }

    private void assertMovieAndMovieDTO(Movie movie, MovieDTO movieDTO) {
        assertEquals(movie.getName(), movieDTO.getName());
        assertEquals(movie.getDescription(), movieDTO.getDescription());
        assertEquals(movie.getCast(), movieDTO.getCast());
        assertEquals(movie.getCertification(), movieDTO.getCertification());
        assertEquals(movie.getDuration(), movieDTO.getDuration());
        assertEquals(movie.getGenre(), movieDTO.getGenre());
    }
}
