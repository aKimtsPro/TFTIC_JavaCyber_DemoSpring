package be.tftic.spring.demo.api.model.dto;


import be.tftic.spring.demo.domain.entity.User;

public record AuthDTO (
        String token,
        UserDTO user
){

    public static AuthDTO from(User user, String token){
        return new AuthDTO(
                token,
                UserDTO.fromEntity(user)
        );
    }

}
