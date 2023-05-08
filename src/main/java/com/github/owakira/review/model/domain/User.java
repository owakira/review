package com.github.owakira.review.model.domain;

import com.github.owakira.review.model.entity.UserEntity;
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

    public static User fromEntity(UserEntity userEntity) {
        return new User(
                userEntity.getId(),
                userEntity.getEmail(),
                userEntity.getUsername(),
                userEntity.getCreatedAt()
        );
    }
}
