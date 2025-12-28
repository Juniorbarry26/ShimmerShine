package com.Alsainey.ShimmerShine.user.controllers;

import com.Alsainey.ShimmerShine.user.User;
import com.Alsainey.ShimmerShine.user.dto.UpdateUser;
import com.Alsainey.ShimmerShine.user.services.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Tag(name = "User", description = "")
public class UserController {

    private final UserService userService;

    @PutMapping("/update")
    public ResponseEntity<User> updateUser(
            @AuthenticationPrincipal User currentUser,
            @RequestBody UpdateUser request) {

        return ResponseEntity.ok(userService.updateUser(currentUser, request));
    }

}
