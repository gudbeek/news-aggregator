package com.gudbeek.controller;

import com.gudbeek.dto.LoginResponse;
import com.gudbeek.dto.LoginUserDto;
import com.gudbeek.dto.RegisterUserDto;
import com.gudbeek.entity.User;
import com.gudbeek.service.AuthenticationService;
import com.gudbeek.service.JwtService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(method = { RequestMethod.GET, RequestMethod.POST })
public class UserRegistrationController {
    //singup
    //signin

    //POST /api/register: Register a new user.

    //POST /api/login: Log in a user.

    private final JwtService jwtService;
    private final AuthenticationService authenticationService;

    public UserRegistrationController(JwtService jwtService, AuthenticationService authenticationService) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterUserDto registerUserDto) {
        User registeredUser = authenticationService.signup(registerUserDto);
        return ResponseEntity.ok("User registered "+registeredUser.getName()+" successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticate(@RequestBody LoginUserDto loginUserDto) {
        User authenticatedUser = authenticationService.authenticate(loginUserDto);
        String jwtToken = jwtService.generateToken((UserDetails) authenticatedUser);
        LoginResponse loginResponse = new LoginResponse().setToken(jwtToken).setExpiresIn(jwtService.getExpirationTime());
        return ResponseEntity.ok(loginResponse);
    }

    @GetMapping("/home")
    public ResponseEntity<String> home() {
        return ResponseEntity.ok("This is home!");
    }

    @GetMapping("/nothome")
    public ResponseEntity<String> nothome() {
        System.out.println("Inside not home");
        return ResponseEntity.ok("This is not home!");
    }
}
