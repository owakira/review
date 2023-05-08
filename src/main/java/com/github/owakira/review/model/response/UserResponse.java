package com.github.owakira.review.model.response;

import com.github.owakira.review.model.domain.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class UserResponse {
    private Long id;
    @ToString.Exclude
    private String email;
    private String username;
    private LocalDateTime createdAt;

    public static UserResponse fromDomain(User user) {
        return new UserResponse(
                user.getId(),
                user.getEmail(),
                user.getUsername(),
                user.getCreatedAt()
        );
    }
}
