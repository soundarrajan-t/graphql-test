package com.bookmyshow.mappers;

import com.bookmyshow.models.Movie;
import com.bookmyshow.models.MovieDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MovieMapper {
    MovieDTO movieToMovieDTO(Movie movie);

    List<MovieDTO> movieToMovieDTOList(List<Movie> movieList);

    Movie movieDTOToMovie(MovieDTO movieDTO);
}
