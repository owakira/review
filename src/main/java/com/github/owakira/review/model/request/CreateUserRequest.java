package com.github.owakira.review.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@AllArgsConstructor
public class CreateUserRequest {
    @ToString.Exclude
    private String email;
    private String username;
    @ToString.Exclude
    private String password;
}
