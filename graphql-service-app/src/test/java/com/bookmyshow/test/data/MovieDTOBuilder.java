package com.bookmyshow.test.data;

import com.bookmyshow.models.MovieDTO;

import java.util.HashMap;

import static com.bookmyshow.models.Certification.U;

public class MovieDTOBuilder {

    private final static HashMap<String, MovieDTO> MOVIE_DTOS = new HashMap<String, MovieDTO>() {
        {
            put("The Avengers", MovieDTO.builder()
                    .name("The Avengers")
                    .description("marvel")
                    .cast("Chris Evans")
                    .certification(U)
                    .genre("action/sci-fic")
                    .rating(0.0)
                    .duration(180).build());

            put("Harry", MovieDTO.builder()
                    .name("Harry")
                    .description("test description")
                    .cast("Lily James")
                    .certification(U)
                    .genre("fantasy")
                    .rating(0.0)
                    .duration(160).build());
        }
    };

    public static MovieDTO createMovieDTO(String movieName) {
        return MOVIE_DTOS.get(movieName);
    }
}
