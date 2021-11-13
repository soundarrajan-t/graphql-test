package com.bookmyshow.resolvers;

import com.bookmyshow.exceptionhandler.CustomGraphQLErrorHandler;
import com.bookmyshow.models.User;
import com.bookmyshow.publisher.MoviePublisher;
import com.bookmyshow.services.MovieService;
import com.bookmyshow.services.UserService;
import com.graphql.spring.boot.test.GraphQLResponse;
import com.graphql.spring.boot.test.GraphQLTest;
import com.graphql.spring.boot.test.GraphQLTestTemplate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;

import java.io.IOException;
import java.util.Optional;

import static com.bookmyshow.test.data.UserBuilder.createUser;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@GraphQLTest
@ContextConfiguration(classes = CustomGraphQLErrorHandler.class)
@SuppressWarnings({"unused"})
public class UserMutationTest {

    @Autowired
    private GraphQLTestTemplate graphQLTestTemplate;

    @MockBean
    private UserService userService;

    @MockBean
    private MovieService movieService;

    @MockBean
    private MoviePublisher moviePublisher;

    @Test
    public void shouldReturnValidUserAfterCreatingUser() throws IOException {
        User expectedUser = createUser();
        when(userService.createUser(any())).thenReturn(expectedUser.getName());

        GraphQLResponse response = graphQLTestTemplate.postForResource("/graphql/request/mutation_create_user.graphql");

        verify(userService).createUser(any());
        assertNotNull(response);
        assertThat(response.isOk()).isTrue();
        assertThat(response.get("$.data.createUser")).isEqualTo("test_user");
    }

    @Test
    void shouldThrowExceptionIfUserAlreadyExistsInCreateUserMutation() throws IOException {
        User expectedUser = createUser();

        when(userService.getUser("test@gmail.com")).thenReturn(Optional.of(expectedUser));

        GraphQLResponse response = graphQLTestTemplate.postForResource("/graphql/request/mutation_create_user.graphql");

        verify(userService).getUser("test@gmail.com");
        assertNotNull(response);
        assertThat(response.isOk()).isTrue();
        assertThat(response.get("$.errors[0].message")).contains("User Already Exists");
        assertThat(response.get("$.errors[0].extensions.errorCode")).isEqualTo("USER_WITH_EMAIL_ALREADY_EXISTS");
    }

    @Test
    void shouldThrowExceptionIfThereIsValidationErrorInInputQuery() throws IOException {
        GraphQLResponse response = graphQLTestTemplate.postForResource("/graphql/request/mutation_user_validation_error.graphql");

        assertNotNull(response);
        assertThat(response.isOk()).isTrue();
        assertThat(response.get("$.errors[0].message")).contains("Validation error");
        assertThat(response.get("$.errors[0].extensions.errorCode")).isEqualTo("QUERY_VALIDATION_ERROR");
    }

    @Test
    void shouldThrowExceptionIfRoleFieldIsEmptyInInputQuery() throws IOException {
        GraphQLResponse response = graphQLTestTemplate.postForResource("/graphql/request/mutation_user_illegal_argument.graphql");

        assertNotNull(response);
        assertThat(response.isOk()).isTrue();
        assertThat(response.get("$.errors[0].message")).isEqualTo("role: [USER, ADMIN] any one of these Required");
        assertThat(response.get("$.errors[0].extensions.errorCode")).isEqualTo("ILLEGAL_ARGUMENT");
    }

}