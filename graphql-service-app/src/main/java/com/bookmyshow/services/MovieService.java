package com.bookmyshow.services;

import com.bookmyshow.gateways.OMDBWebClient;
import com.bookmyshow.mappers.MovieMapper;
import com.bookmyshow.models.Movie;
import com.bookmyshow.models.MovieDTO;
import com.bookmyshow.publisher.MoviePublisher;
import com.bookmyshow.repositories.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class MovieService {

    private final OMDBWebClient omdbWebClient;

    private final MovieMapper mapper = Mappers.getMapper(MovieMapper.class);

    private final MovieRepository movieRepository;

    private final MoviePublisher moviePublisher;

    private final Logger log = LogManager.getLogger(MovieService.class);

    public List<MovieDTO> getAllMovies() {
        log.info("Method getAllMovies is called");
        List<Movie> movie = movieRepository.findAll();
        List<MovieDTO> movieDTOS = mapper.movieToMovieDTOList(movie);
        return addRatingTo(movieDTOS);
    }

    public Optional<MovieDTO> getMovie(String name) {
        log.info("Method getMovies is called");
        Optional<Movie> movie = movieRepository.findByName(name);
        if (movie.isPresent()) {
            MovieDTO movieDTO = mapper.movieToMovieDTO(movie.get());
            return Optional.ofNullable(addRatingTo(movieDTO));
        }
        return Optional.empty();
    }

    public String createMovie(Movie movie) {
        log.info("Method createMovie is called");
        String movieName = movieRepository.save(movie).getName();
        moviePublisher.publish();
        return movieName;
    }

    public String deleteMovieByName(String movieName) {
        log.info("Method deleteMovieByName is called");
        movieRepository.deleteByName(movieName);
        moviePublisher.publish();
        return movieName;
    }

    public List<MovieDTO> getMoviesByGenre(String genre) {
        log.info("Method getMoviesByGenre is called");
        List<Movie> movie = movieRepository.findByGenre(genre);
        List<MovieDTO> movieDTOS = mapper.movieToMovieDTOList(movie);
        return addRatingTo(movieDTOS);
    }

    public MovieDTO addRatingTo(MovieDTO movieDTO) {
        String movieName = movieDTO.getName();
        Map<String, String> result = omdbWebClient.getOMDBResponse(movieName);
        boolean response = Boolean.parseBoolean(result.get("Response"));
        String title = result.get("Title");
        double imdbRating = 0.0;
        if (isCorrectResponse(movieName, response, title))
            imdbRating = Double.parseDouble(result.get("imdbRating"));
        movieDTO.setRating(imdbRating);
        return movieDTO;
    }

    private boolean isCorrectResponse(String movieName, boolean response, String title) {
        return response && title.equalsIgnoreCase(movieName);
    }

    public List<MovieDTO> addRatingTo(List<MovieDTO> movieDTOs) {
        for (int index = 0; index < movieDTOs.size(); index++)
            movieDTOs.set(index, addRatingTo(movieDTOs.get(index)));
        return movieDTOs;
    }

    public String updateMovie(Movie movie) {
        log.info("Method updateMovie is called");
        Optional<Movie> optionalMovie = movieRepository.findByName(movie.getName());
        if(optionalMovie.isPresent()) {
            Movie existingMovie = optionalMovie.get();
            existingMovie.setName(movie.getName());
            existingMovie.setCast(movie.getCast());
            existingMovie.setDescription(movie.getDescription());
            existingMovie.setCertification(movie.getCertification());
            existingMovie.setGenre(movie.getGenre());
            existingMovie.setDuration(movie.getDuration());
        }
        moviePublisher.publish();
        return movie.getName();
    }
}