package be.tftic.spring.demo.api.model.form;

import be.tftic.spring.demo.domain.entity.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
public class UserUpdateForm {

    @NotBlank
//    @Size(min = 6)
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{6,}$")
    private String password;
    @Size(max = 3)
    private List<String> alias;

    public User mapToEntity(){
        User user = new User();
        user.setPassword( this.password );
        user.setAlias( this.alias );
        return user;
    }

}
