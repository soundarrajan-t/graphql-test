package com.bookmyshow.services;

import com.bookmyshow.models.User;
import com.bookmyshow.repositories.UserRepository;
import com.bookmyshow.services.UserDetailServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static com.bookmyshow.test.data.UserBuilder.createUser;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = UserDetailServiceImpl.class)
@SuppressWarnings({"unused"})
public class UserDetailServiceImplTest {

    @Autowired
    private UserDetailServiceImpl userDetailService;

    @MockBean
    private UserRepository userRepository;

    @Test
    void shouldReturnUserWhenEmailIsProvidedForLoadUserByUserName() {
        User expectedUser = createUser();

        when(userRepository.findByEmail(expectedUser.getEmail())).thenReturn(Optional.of(expectedUser));

        UserDetails actualUser = userDetailService.loadUserByUsername(expectedUser.getEmail());

        verify(userRepository).findByEmail(expectedUser.getEmail());
        assertNotNull(actualUser);
        assertThat(actualUser.getUsername(), is(expectedUser.getEmail()));
    }

    @Test
    void shouldThrowUserNotFoundWhenIncorrectEmailIsProvidedForLoadUserByUserName() {
        Throwable exception = Assertions.assertThrows(RuntimeException.class, () -> {
            userDetailService.loadUserByUsername("doesNotExist@gmail.com");
        });

        assertEquals("Incorrect Email/Password", exception.getMessage());
    }
}