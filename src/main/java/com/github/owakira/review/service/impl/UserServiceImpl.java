package com.github.owakira.review.service.impl;

import com.github.owakira.review.exception.EmailAlreadyExistsException;
import com.github.owakira.review.exception.UsernameAlreadyExistsException;
import com.github.owakira.review.model.domain.User;
import com.github.owakira.review.model.dto.CreateUserDTO;
import com.github.owakira.review.model.entity.UserEntity;
import com.github.owakira.review.repository.UserRepository;
import com.github.owakira.review.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Log4j2
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User createUser(CreateUserDTO dto) {
        log.info("Create user. dto={}", dto);

        var userEntity = new UserEntity();
        userEntity.setEmail(dto.getEmail());
        userEntity.setUsername(dto.getUsername());
        userEntity.setPassword(passwordEncoder.encode(dto.getPassword()));
        try {
            userRepository.save(userEntity);
            log.info(
                    "User successfully created. userEntity={}",
                    userEntity
            );
            return User.fromEntity(userEntity);
        } catch (DataIntegrityViolationException e) {
            if (e.getCause() instanceof ConstraintViolationException) {
                var constraintException = (ConstraintViolationException) e.getCause();
                var sqlException = constraintException.getSQLException();
                var message = sqlException.getMessage();

                if (message.contains(UserEntity.EMAIL_UNIQUE_CONSTRAINT)) {
                    log.info("Email already exists. dto={}", dto);
                    throw new EmailAlreadyExistsException();
                } else if (message.contains(UserEntity.USERNAME_UNIQUE_CONSTRAINT)) {
                    log.info("Username already exists. dto={}", dto);
                    throw new UsernameAlreadyExistsException();
                }
            }
            log.error(e);
            throw e;
        }
    }

    @Override
    public Optional<User> findUserById(Long id) {
        log.info("Find user by id. id={}", id);
        return userRepository.findById(id).map(User::fromEntity);
    }
}
