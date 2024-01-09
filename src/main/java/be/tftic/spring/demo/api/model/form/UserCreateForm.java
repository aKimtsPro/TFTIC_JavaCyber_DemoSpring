package be.tftic.spring.demo.api.model.form;

import be.tftic.spring.demo.domain.entity.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class UserCreateForm {

    @NotBlank
//    @Size(min = 1, max = 20)
    @Pattern(regexp = "[a-zA-Z0-9]{1,20}")
    private String username;
    @NotBlank
//    @Size(min = 6)
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{6,}$")
    private String password;

    public User mapToEntity(){
        User user = new User();
        user.setUsername( this.username );
        user.setPassword( this.password );
        return user;
    }

}
