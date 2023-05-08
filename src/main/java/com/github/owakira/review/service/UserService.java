package com.github.owakira.review.service;

import com.github.owakira.review.model.domain.User;
import com.github.owakira.review.model.dto.CreateUserDTO;

import java.util.Optional;

public interface UserService {
    User createUser(CreateUserDTO dto);

    Optional<User> findUserById(Long id);
}
