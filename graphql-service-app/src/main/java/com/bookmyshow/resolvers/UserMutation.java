package com.bookmyshow.resolvers;

import com.bookmyshow.exceptions.UserAlreadyExistsException;
import com.bookmyshow.mappers.UserMapper;
import com.bookmyshow.models.UserDTO;
import com.bookmyshow.services.UserService;
import graphql.kickstart.tools.GraphQLMutationResolver;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mapstruct.factory.Mappers;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;

@Validated
@Component
@RequiredArgsConstructor
@SuppressWarnings({"unused"})
public class UserMutation implements GraphQLMutationResolver {

    private final UserMapper userMapper = Mappers.getMapper(UserMapper.class);

    private final Logger logger = LogManager.getLogger(UserMutation.class);

    private final UserService userService;

    @PreAuthorize("hasRole('ADMIN')")
    public String createUser(@Valid UserDTO userDTO) {
        logger.info("Method create user is called");
        if (userService.getUser(userDTO.getEmail()).isPresent())
            throw new UserAlreadyExistsException("User Already Exists");
        return userService.createUser(userMapper.userDTOToUser(userDTO));
    }
}