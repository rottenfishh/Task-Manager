package ru.nsu.kolodina.taskmanager;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/index")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/register")
    public void register(@RequestBody SignUpRequest request) {
        System.out.println("USER CREATED");
        userService.createUser(request.getName(), request.getPassword());
    }

    // spring сам реализует логин из securityconfig
}
