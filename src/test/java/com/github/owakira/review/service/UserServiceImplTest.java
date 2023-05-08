package com.github.owakira.review.service;

import com.github.owakira.review.exception.EmailAlreadyExistsException;
import com.github.owakira.review.exception.UsernameAlreadyExistsException;
import com.github.owakira.review.model.domain.User;
import com.github.owakira.review.model.dto.CreateUserDTO;
import com.github.owakira.review.model.entity.UserEntity;
import com.github.owakira.review.repository.UserRepository;
import com.github.owakira.review.service.impl.UserServiceImpl;
import org.hibernate.exception.ConstraintViolationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    private final static CreateUserDTO dto = new CreateUserDTO(
            "test@test.com",
            "test",
            "test"
    );

    private final static UserEntity userEntity = new UserEntity();

    static {
        userEntity.setId(1L);
        userEntity.setEmail(dto.getEmail());
        userEntity.setUsername(dto.getUsername());
        userEntity.setPassword(dto.getPassword());
        userEntity.setCreatedAt(LocalDateTime.now());
    }

    @Test
    public void createUser_UserSuccessfullyCreated_ReturnsCreatedUser() {
        Mockito.doReturn(userEntity.getPassword())
                .when(passwordEncoder)
                .encode(userEntity.getPassword());
        Mockito.doReturn(userEntity)
                .when(userRepository)
                .save(Mockito.any());

        var user = userService.createUser(dto);

        Assertions.assertEquals(userEntity.getEmail(), user.getEmail());
        Assertions.assertEquals(userEntity.getUsername(), user.getUsername());
    }

    @Test
    public void createUser_EmailAlreadyExists_ThrowsEmailAlreadyExistsException() {
        var exception = new DataIntegrityViolationException(
                "",
                new ConstraintViolationException(
                        "",
                        new SQLException(UserEntity.EMAIL_UNIQUE_CONSTRAINT),
                        ""
                )
        );
        Mockito.doThrow(exception).when(userRepository).save(Mockito.any());

        Assertions.assertThrows(EmailAlreadyExistsException.class, () -> userService.createUser(dto));
    }

    @Test
    public void createUser_UsernameAlreadyExists_ThrowsUsernameAlreadyExists() {
        var exception = new DataIntegrityViolationException(
                "",
                new ConstraintViolationException(
                        "",
                        new SQLException(UserEntity.USERNAME_UNIQUE_CONSTRAINT),
                        ""
                )
        );
        Mockito.doThrow(exception).when(userRepository).save(Mockito.any());

        Assertions.assertThrows(UsernameAlreadyExistsException.class, () -> userService.createUser(dto));
    }

    @Test
    public void createUser_UnexpectedDataIntegrityViolationException_ThrowsUnexpectedException() {
        var exception = new DataIntegrityViolationException("", new RuntimeException("Unexpected"));
        Mockito.doThrow(exception).when(userRepository).save(Mockito.any());
        Assertions.assertThrows(RuntimeException.class, () -> userService.createUser(dto));
    }

    @Test
    public void findUserById_UserFound_ReturnsFoundUser() {
        Mockito.doReturn(Optional.of(userEntity))
                .when(userRepository)
                .findById(userEntity.getId());

        var expectedUser = User.fromEntity(userEntity);
        var user = userService.findUserById(userEntity.getId());
        Assertions.assertEquals(expectedUser, user.get());
    }
}
