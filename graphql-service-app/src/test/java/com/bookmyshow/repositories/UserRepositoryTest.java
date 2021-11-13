package com.bookmyshow.repositories;

import com.bookmyshow.models.Role;
import com.bookmyshow.models.User;
import com.bookmyshow.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    @SuppressWarnings("unused")
    private UserRepository userRepository;

    @Test
    public void shouldReturnInsertedUserWhenSaveMethodIsCalled() {
        User user = User.builder()
                .name("test")
                .email("test@gmail.com")
                .password("test@123")
                .role(Role.ADMIN)
                .build();

        User savedUser = userRepository.save(user);

        assertNotNull(savedUser);
        assertNotNull(savedUser.getId());
        assertEquals(user, savedUser);
    }
}
