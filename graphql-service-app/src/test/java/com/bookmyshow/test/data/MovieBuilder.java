package com.bookmyshow.test.data;

import com.bookmyshow.models.Movie;

import java.util.HashMap;

import static com.bookmyshow.models.Certification.U;

public class MovieBuilder {

    private final static HashMap<String, Movie> MOVIES = new HashMap<String, Movie>() {
        {
            put("The Avengers", Movie.builder()
                    .name("The Avengers")
                    .description("marvel")
                    .cast("Chris Evans")
                    .certification(U)
                    .genre("action/sci-fic")
                    .duration(180).build());

            put("Cinderella", Movie.builder()
                    .name("Cinderella")
                    .description("test description")
                    .cast("Lily James")
                    .certification(U)
                    .genre("fantasy")
                    .duration(160).build());

            put("Harry", Movie.builder()
                    .name("Harry")
                    .description("test description")
                    .cast("Lily James")
                    .certification(U)
                    .genre("fantasy")
                    .duration(160).build());
        }
    };

    public static Movie createMovie(String movieName) {
        return MOVIES.get(movieName);
    }

    public static Movie updateMovie(String movieName) {
        return Movie.builder()
                .name("The Avengers")
                .description("Hollywood Movie")
                .cast("John Cena")
                .certification(U)
                .genre("Adventures")
                .duration(180).build();
    }
}
