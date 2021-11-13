package com.bookmyshow;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.json.JSONException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.skyscreamer.jsonassert.JSONAssert.assertEquals;
import static test.data.Utility.getRequestPath;
import static test.data.Utility.getResponsePath;
import static test.data.Utility.readFromResource;

public class MovieQueryIT {

    private static String END_POINT;
    private static String EXPECTED_RESPONSE_BODY;
    private static String EXPECTED_REQUEST_BODY;
    private static String FILE_NAME;
    private static GraphQLQuery QUERY;

    @BeforeAll
    static void setUp() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 80;
        END_POINT = "/bookmyshow";

        QUERY = new GraphQLQuery();
    }

    @Test
    public void shouldReturnValidMovieForMovieByNameQuery() {
        FILE_NAME = "query_movie_by_name";
        EXPECTED_REQUEST_BODY = readFromResource(getRequestPath(FILE_NAME));
        QUERY.setQuery(EXPECTED_REQUEST_BODY);

        given().log().all()
            .contentType(ContentType.JSON)
            .body(QUERY)

        .when()
            .post(END_POINT)

        .then().log().all()
            .assertThat()
            .statusCode(200)
                .and()
            .body("data.movieByName.name", equalTo("Harry potter"))
            .body("data.movieByName.certification", equalTo("U"));
    }

    @Test
    public void shouldReturnValidMovieListForMoviesQuery() throws JSONException {
        FILE_NAME = "movies";
        EXPECTED_REQUEST_BODY = readFromResource(getRequestPath(FILE_NAME));
        QUERY.setQuery(EXPECTED_REQUEST_BODY);
        EXPECTED_RESPONSE_BODY = readFromResource(getResponsePath(FILE_NAME));

        String actualResponseBody =
            given().log().all()
                .contentType(ContentType.JSON)
                .body(QUERY)

            .when()
                .post(END_POINT)

            .then().log().all()
                .assertThat()
                .statusCode(200)
                    .and()
                .body("data.movies[0].name", equalTo("The Avengers"))
                    .and()
                .extract()
                .body()
                .asString();

        assertEquals(EXPECTED_RESPONSE_BODY, actualResponseBody, true);
    }

    @Test
    public void shouldReturnValidMovieAndMovieListForMovieByNameAndMoviesQueryWithFragment() throws JSONException {
        String fragmentFileName = "movie_data_fragment";
        String queryFileName = "query_movie_by_name_and_movies_with_fragment";
        EXPECTED_REQUEST_BODY = readFromResource(getRequestPath(fragmentFileName)) + readFromResource(getRequestPath(queryFileName));
        QUERY.setQuery(EXPECTED_REQUEST_BODY);
        EXPECTED_RESPONSE_BODY = readFromResource(getResponsePath(queryFileName));

        String actualResponseBody =
            given().log().all()
                .contentType(ContentType.JSON)
                .body(QUERY)

            .when()
                .post(END_POINT)

            .then().log().all()
                .assertThat()
                .statusCode(200)
                    .and()
                .body("data.movieByName.name", equalTo("Harry potter"))
                .body("data.movies[0].name", equalTo("The Avengers"))
                    .and()
                .extract()
                .body()
                .asString();

        assertEquals(EXPECTED_RESPONSE_BODY, actualResponseBody, true);
    }
}