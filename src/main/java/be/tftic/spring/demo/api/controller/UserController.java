package be.tftic.spring.demo.api.controller;

import be.tftic.spring.demo.api.model.dto.UserDTO;
import be.tftic.spring.demo.api.model.form.UserCreateForm;
import be.tftic.spring.demo.api.model.form.UserUpdateForm;
import be.tftic.spring.demo.bll.UserService;
import be.tftic.spring.demo.domain.entity.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
                .map( UserDTO::mapToDto)
                .toList();
        return ResponseEntity.ok( dtos );
    }


    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getOne(@PathVariable long id){
        User user = userService.getOne(id);
        UserDTO dto = UserDTO.mapToDto(user);
        return ResponseEntity.ok( dto );
    }

    @PostMapping
    public ResponseEntity<UserDTO> create(@RequestBody UserCreateForm form){
        User user = userService.create( form.mapToEntity() );
        UserDTO dto = UserDTO.mapToDto(user);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> update(@PathVariable long id, @RequestBody UserUpdateForm form ){
        User toUpdate = userService.getOne(id);
        toUpdate.setPassword( form.getPassword() );
        toUpdate.setAlias( form.getAlias() );
        User user = userService.update(toUpdate);
        return ResponseEntity.ok(
                UserDTO.mapToDto(user)
        );
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<UserDTO> delete(@PathVariable long id){
        User user = userService.delete( id );
        UserDTO dto = UserDTO.mapToDto( user );
        return ResponseEntity.ok( dto );
    }


}
