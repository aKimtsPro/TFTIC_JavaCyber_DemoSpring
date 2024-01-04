package be.tftic.spring.demo.api.model.dto;

import be.tftic.spring.demo.bonus.Annotated;

import java.util.List;

// @Annotated uniquement présente pour la petite démo sur les annotation
@Annotated("la valeur")
public record UserDTO(
        long id,
        String username,
        List<String> alias
) {}
