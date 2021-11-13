package com.bookmyshow;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static test.data.Utility.getRequestPath;
import static test.data.Utility.readFromResource;

public class UserMutationIT {

    @BeforeEach
    void setUp() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 80;
    }

    @Test
    public void shouldReturnUserForCreateUser() {
        String userName = "admin_user@gmail.com";
        String password = "Password@123";
        String endPoint = "/bookmyshow";
        String fileName = "mutation_create_user";
        String expectedRequestBody = readFromResource(getRequestPath(fileName));
        GraphQLQuery query = new GraphQLQuery();
        query.setQuery(expectedRequestBody);

        given().log().all()
            .auth()
            .preemptive()
            .basic(userName, password)
            .contentType(ContentType.JSON)
            .body(query)

        .when()
            .post(endPoint)

        .then().log().all()
            .assertThat()
            .statusCode(200)
                .and()
            .body("data.createUser", equalTo("happy_user"));
    }
}