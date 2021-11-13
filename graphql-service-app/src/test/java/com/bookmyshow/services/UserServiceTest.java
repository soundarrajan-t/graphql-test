package com.bookmyshow.services;

import com.bookmyshow.models.User;
import com.bookmyshow.repositories.UserRepository;
import com.bookmyshow.services.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static com.bookmyshow.test.data.UserBuilder.createUser;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = UserService.class)
@SuppressWarnings({"unused"})
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    @Test
    public void shouldReturnSavedUserNameForCreateUser() {
        User expectedUser = createUser();

        when(userRepository.save(expectedUser)).thenReturn(expectedUser);

        String actualUserName = userService.createUser(expectedUser);

        verify(userRepository).save(expectedUser);
        assertThat(actualUserName, is(expectedUser.getName()));
    }

    @Test
    void shouldReturnUserForGetUserByEmail() {
        User expectedUser = createUser();

        when(userRepository.findByEmail(expectedUser.getEmail())).thenReturn(Optional.of(expectedUser));

        Optional<User> actualUser = userService.getUser(expectedUser.getEmail());

        verify(userRepository).findByEmail(expectedUser.getEmail());
        assertNotNull(actualUser);
        assertThat(actualUser, is(Optional.of(expectedUser)));
    }
}