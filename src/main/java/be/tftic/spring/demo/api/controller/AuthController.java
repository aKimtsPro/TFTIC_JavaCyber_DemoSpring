package be.tftic.spring.demo.api.controller;

import be.tftic.spring.demo.api.model.dto.AuthDTO;
import be.tftic.spring.demo.api.model.form.LoginForm;
import be.tftic.spring.demo.api.model.form.RegisterForm;
import be.tftic.spring.demo.bll.AuthService;
import be.tftic.spring.demo.domain.entity.User;
import be.tftic.spring.demo.utils.jwt.JwtProvider;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    private final JwtProvider jwtProvider;

    public AuthController(AuthService authService, JwtProvider jwtProvider) {
        this.authService = authService;
        this.jwtProvider = jwtProvider;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthDTO> login(@RequestBody @Valid LoginForm form){
        User user = authService.login(form.username(), form.password());
        String token = jwtProvider.generateToken(user);
        return ResponseEntity.ok( AuthDTO.from(user, token) );
    }

    @PostMapping("/register")
    public ResponseEntity<AuthDTO> register(@RequestBody @Valid RegisterForm form){
        User user = authService.register(form.toEntity());
        String token = jwtProvider.generateToken(user);
        return ResponseEntity.ok( AuthDTO.from(user, token) );
    }

}
