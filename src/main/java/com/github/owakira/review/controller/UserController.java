package com.github.owakira.review.controller;

import com.github.owakira.review.exception.UserNotFoundException;
import com.github.owakira.review.model.dto.CreateUserDTO;
import com.github.owakira.review.model.request.CreateUserRequest;
import com.github.owakira.review.model.response.UserResponse;
import com.github.owakira.review.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(UserController.REQUEST_ENDPOINT)
@RequiredArgsConstructor
@Log4j2
public class UserController {
    private final UserService userService;

    public final static String REQUEST_ENDPOINT = "/api/users";
    public final static String CREATE_USER = "/";
    public final static String FIND_USER_BY_ID_ENDPOINT = "/{id}";

    @PostMapping(CREATE_USER)
    public ResponseEntity<?> createUser(@Valid @RequestBody CreateUserRequest request) {
        log.info("Create user. request={}", request);
        var dto = new CreateUserDTO(request.getEmail(), request.getUsername(), request.getPassword());
        var user = userService.createUser(dto);
        log.info("User successfully created. user={}", user);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(UserResponse.fromDomain(user));
    }

    @GetMapping(FIND_USER_BY_ID_ENDPOINT)
    public ResponseEntity<?> findUserById(@PathVariable Long id) {
        log.info("Find user by id. id={}", id);

        var user = userService.findUserById(id)
                .orElseThrow(() -> {
                    log.info("User not found");
                    return new UserNotFoundException();
                });

        log.info("User successfully find. user={}", user);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(UserResponse.fromDomain(user));
    }
}
