package com.bookmyshow.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UserDTO {

    @NotBlank(message = "Name is required")
    @Size(max = 250)
    private String name;

    @NotBlank(message = "email is required")
    @Size(max = 250)
    @Pattern(regexp = "^(.+)@(.+)$", message = "Invalid email Id")
    private String email;

    @NotBlank(message = "password is required")
    @Size(max = 250)
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[A-Z]).{8,16}$",
            message = "Invalid password. " +
                    "Should contain atleast one Upper case letter. " +
                    "Should contain atleast on Number. " +
                    "password length should be atleast 8 and atmost 16 characters. ")
    private String password;

    @NotNull
    private Role role;
}