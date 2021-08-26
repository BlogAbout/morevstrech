package ru.morevstrech.service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
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
        if (signupRequest.getCode() == 0) {
            if (userService.existsByUsername(signupRequest.getUsername()))
                return ResponseEntity.status(HttpStatus.ACCEPTED).body(new MessageResponse(
                        "username-is-exist",
                        "Пользователь с таким именем пользователя уже существует."
                ));

            if (userService.existsByPhone(signupRequest.getPhone()))
                return ResponseEntity.status(HttpStatus.ACCEPTED).body(new MessageResponse(
                        "phone-is-exist",
                        "Пользователь с таким номером телефона уже существует."
                ));

            User user = userService.create(signupRequest);

            return ResponseEntity.status(HttpStatus.OK).body(user);
        } else {
            User user = (User) userService.loadUserByUsername(signupRequest.getUsername());

            if (user.getValidationCode() != signupRequest.getCode())
                return ResponseEntity.status(HttpStatus.ACCEPTED).body(new MessageResponse(
                        "validation-code-error",
                        "Код проверки неверный."
                ));

            userService.activate(user);

            return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse(
                    "user-created",
                    "Вы успешно зарегистрировались."
            ));
        }
    }

    @PostMapping("/signin")
    public ResponseEntity<?> authUser(@RequestBody SigninRequest signinRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    signinRequest.getUsername(),
                    signinRequest.getPassword()
            ));

            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtUtils.generateJwtToken(authentication);

            User user = (User) authentication.getPrincipal();

            Set<String> roles = user.getAuthorities().stream()
                    .map(item -> item.getAuthority())
                    .collect(Collectors.toSet());

            return ResponseEntity.status(HttpStatus.OK).body(new JwtResponse(
                    jwt,
                    user.getId(),
                    user.getUsername(),
                    user.getEmail(),
                    user.getAccount(),
                    roles
            ));
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(new MessageResponse(
                    "authentication-error",
                    "Логин или пароль указаны неверно"
            ));
        }
    }
}