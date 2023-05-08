package com.github.owakira.review.model.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class User {
    private Long id;
    @ToString.Exclude
    private String email;
    private String username;
    private LocalDateTime createdAt;
}
