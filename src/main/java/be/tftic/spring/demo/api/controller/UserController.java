package be.tftic.spring.demo.api.controller;

import be.tftic.spring.demo.api.model.dto.UserDTO;
import be.tftic.spring.demo.bll.UserService;
import be.tftic.spring.demo.domain.entity.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> getAll(){
        List<User> users = userService.getAll();
        List<UserDTO> dtos = users.stream()
                .map( (user) -> new UserDTO(
                        user.getId(),
                        user.getUsername(),
                        user.getAlias())
                )
                .toList();
        return ResponseEntity.ok( dtos );
    }



}
