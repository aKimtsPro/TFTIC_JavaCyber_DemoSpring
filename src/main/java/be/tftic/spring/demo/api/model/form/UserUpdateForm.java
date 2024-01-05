package be.tftic.spring.demo.api.model.form;

import be.tftic.spring.demo.domain.entity.User;
import lombok.Data;

import java.util.List;

@Data
public class UserUpdateForm {

    private String password;
    private List<String> alias;

    public User mapToEntity(){
        User user = new User();
        user.setPassword( this.password );
        user.setAlias( this.alias );
        return user;
    }

}
