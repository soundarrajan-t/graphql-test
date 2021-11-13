package com.bookmyshow.mappers;

import com.bookmyshow.models.User;
import com.bookmyshow.models.UserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {
    User userDTOToUser(UserDTO userDTO);
}