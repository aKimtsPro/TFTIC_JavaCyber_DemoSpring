package be.tftic.spring.demo.api.model.form;


import jakarta.validation.constraints.NotNull;

public record LoginForm(
        @NotNull String username,
        @NotNull String password
) {}
