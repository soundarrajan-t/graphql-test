package com.bookmyshow;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static test.data.Utility.getRequestPath;
import static test.data.Utility.readFromResource;

@TestMethodOrder(OrderAnnotation.class)
public class MovieMutationIT {

    private static String END_POINT;
    private static String EXPECTED_REQUEST_BODY;
    private static String FILE_NAME;
    private static String USER_NAME;
    private static String PASSWORD;
    private static GraphQLQuery QUERY;

    @BeforeEach
    void setUp() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 80;
        END_POINT = "/bookmyshow";
        USER_NAME = "admin_user@gmail.com";
        PASSWORD = "Password@123";
        QUERY = new GraphQLQuery();
    }

    @Test
    @Order(1)
    public void shouldCreateMovieForUserWithAdminWhenTriedToCreateMovie() {
        FILE_NAME = "mutation_create_movie";
        EXPECTED_REQUEST_BODY = readFromResource(getRequestPath(FILE_NAME));
        QUERY.setQuery(EXPECTED_REQUEST_BODY);

        given().log().all()
            .auth()
            .preemptive()
            .basic(USER_NAME, PASSWORD)
            .contentType(ContentType.JSON)
            .body(QUERY)

        .when()
            .post(END_POINT)

        .then().log().all()
            .assertThat()
            .statusCode(200)
                .and()
            .body("data.createMovie", equalTo("The Rewrite"));
    }

    @Test
    @Order(2)
    public void shouldUpdateMovieForUserWithAdminWhenTriedToUpdateMovie() {
        FILE_NAME = "mutation_update_movie";
        EXPECTED_REQUEST_BODY = readFromResource(getRequestPath(FILE_NAME));
        QUERY.setQuery(EXPECTED_REQUEST_BODY);

        given().log().all()
                .auth()
                .preemptive()
                .basic(USER_NAME, PASSWORD)
                .contentType(ContentType.JSON)
                .body(QUERY)

                .when()
                .post(END_POINT)

                .then().log().all()
                .assertThat()
                .statusCode(200)
                .and()
                .body("data.updateMovie", equalTo("The Rewrite"));
    }

    @Test
    @Order(3)
    public void shouldDeleteMovieForUserWithAdminWhenTriedToDeleteMovie() {
        FILE_NAME = "mutation_delete_movie";
        EXPECTED_REQUEST_BODY = readFromResource(getRequestPath(FILE_NAME));
        QUERY.setQuery(EXPECTED_REQUEST_BODY);

        given().log().all()
            .auth()
            .preemptive()
            .basic(USER_NAME, PASSWORD)
            .contentType(ContentType.JSON)
            .body(QUERY)

        .when()
            .post(END_POINT)

        .then().log().all()
            .assertThat()
            .statusCode(200)
                .and()
            .body("data.deleteMovie", equalTo("The Rewrite"));
    }
}

