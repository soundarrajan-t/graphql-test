package com.bookmyshow.test.data;

import com.bookmyshow.models.Role;
import com.bookmyshow.models.User;

public class UserBuilder {

    public static User createUser() {
        return User.builder()
                .name("test_user")
                .email("test12345@gmail.com")
                .password("Test@12345")
                .role(Role.USER)
                .build();
    }
}
