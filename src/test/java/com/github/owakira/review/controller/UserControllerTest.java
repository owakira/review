package com.github.owakira.review.controller;

import com.github.owakira.review.exception.UserNotFoundException;
import com.github.owakira.review.model.domain.User;
import com.github.owakira.review.model.dto.CreateUserDTO;
import com.github.owakira.review.model.request.CreateUserRequest;
import com.github.owakira.review.model.response.UserResponse;
import com.github.owakira.review.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {
    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @Test
    public void createUser_UserSuccessfullyCreate_ReturnsCreatedUser() {
        var request = new CreateUserRequest("test@gmail.com", "test", "test");
        var dto = new CreateUserDTO(request.getEmail(), request.getUsername(), request.getPassword());
        var user = new User(
                1L,
                dto.getEmail(),
                dto.getUsername(),
                LocalDateTime.now()
        );
        Mockito.doReturn(user).when(userService).createUser(dto);

        var response = userController.createUser(request);
        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());

        var userResponse = UserResponse.fromDomain(user);
        Assertions.assertEquals(userResponse, response.getBody());
    }

    @Test
    public void findUserById_UserNotFound_ThrowsUserNotFoundException() {
        var id = 1L;
        Mockito.doReturn(Optional.empty())
                .when(userService)
                .findUserById(id);
        Assertions.assertThrows(UserNotFoundException.class, () -> userController.findUserById(id));
    }

    @Test
    public void findUserById_UserFound_ReturnsFoundUser() {
        var id = 1L;
        var user = new User(
                id,
                "test@test.com",
                "test",
                LocalDateTime.now()
        );
        Mockito.doReturn(Optional.of(user)).when(userService).findUserById(id);

        var response = userController.findUserById(id);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());

        var userResponse = UserResponse.fromDomain(user);
        Assertions.assertEquals(userResponse, response.getBody());
    }
}
