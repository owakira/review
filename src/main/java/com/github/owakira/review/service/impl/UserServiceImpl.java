package com.github.owakira.review.service.impl;

import com.github.owakira.review.model.domain.User;
import com.github.owakira.review.model.dto.CreateUserDTO;
import com.github.owakira.review.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Log4j2
public class UserServiceImpl implements UserService {
    @Override
    public User createUser(CreateUserDTO dto) {
        return null;
    }

    @Override
    public Optional<User> findUserById(Long id) {
        return Optional.empty();
    }
}
