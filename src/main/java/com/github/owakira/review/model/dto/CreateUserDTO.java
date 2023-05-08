package com.github.owakira.review.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@AllArgsConstructor
public class CreateUserDTO {
    @ToString.Exclude
    private String email;
    private String username;
    @ToString.Exclude
    private String password;
}
