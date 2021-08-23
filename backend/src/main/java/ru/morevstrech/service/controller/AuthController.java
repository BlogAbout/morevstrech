package ru.morevstrech.service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ru.morevstrech.service.config.jwt.JwtUtils;
import ru.morevstrech.service.entity.User;
import ru.morevstrech.service.pojo.JwtResponse;
import ru.morevstrech.service.pojo.MessageResponse;
import ru.morevstrech.service.pojo.SigninRequest;
import ru.morevstrech.service.pojo.SignupRequest;
import ru.morevstrech.service.service.UserService;

import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*", maxAge = 3600)
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtils jwtUtils;

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignupRequest signupRequest) {
        if (userService.existsByUsername(signupRequest.getUsername()))
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(new MessageResponse(
                    "username-is-exist",
                    "Пользователь с таким именем пользователя уже существует."
            ));

        if (userService.existsByEmail(signupRequest.getEmail()))
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(new MessageResponse(
                    "email-is-exist",
                    "Пользователь с таким e-mail уже существует."
            ));

        User user = userService.create(signupRequest);

        return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse(
                "user-created",
                "Вы успешно зарегистрировались."
        ));
    }

    @PostMapping("/signin")
    public ResponseEntity<?> authUser(@RequestBody SigninRequest signinRequest) {
        System.out.println(signinRequest.getUsername());
        System.out.println(signinRequest.getPassword());
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                signinRequest.getUsername(),
                signinRequest.getPassword()
        ));
        System.out.println("authentication" + authentication);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);
        System.out.println("jwt" + jwt);

        User user = (User) authentication.getPrincipal();

        Set<String> roles = user.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toSet());

        return ResponseEntity.status(HttpStatus.OK).body(new JwtResponse(
                jwt,
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                roles
        ));
    }
}