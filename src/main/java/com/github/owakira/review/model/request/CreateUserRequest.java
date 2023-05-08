package com.github.owakira.review.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@AllArgsConstructor
public class CreateUserRequest {
    @NotNull(message = "Email is required")
    @Email(message = "Incorrect email")
    @ToString.Exclude
    private String email;

    @NotNull(message = "Username is required")
    @Pattern(
            regexp = "^(?=.{3,32}$)(?![_.])(?!.*[_.]{2})[a-zA-Z0-9._]+(?<![_.])$",
            message = "Incorrect username"
    )
    private String username;

    @NotNull(message = "Password is required")
    @Pattern(
            regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$",
            message = "Incorrect password. Minimum eight characters, at least one letter and one number"
    )
    @ToString.Exclude
    private String password;
}
