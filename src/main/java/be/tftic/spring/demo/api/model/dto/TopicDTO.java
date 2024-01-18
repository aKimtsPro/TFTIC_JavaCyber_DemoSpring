package be.tftic.spring.demo.api.model.dto;

import be.tftic.spring.demo.domain.entity.Topic;
import be.tftic.spring.demo.domain.entity.TopicCategory;
import be.tftic.spring.demo.domain.entity.User;

public record TopicDTO(
        Long id,
        String title,
        String description,
        TopicCategory category,
        UserDTO createdBy
) {

    public static TopicDTO fromEntity(Topic entity){
        if( entity == null )
            return null;

        return new TopicDTO(
                entity.getId(),
                entity.getTitle(),
                entity.getDescription(),
                entity.getCategory(),
                UserDTO.fromEntity( entity.getCreatedBy() )
        );

    }

    public record UserDTO(
            long id,
            String username
    ){
        public static UserDTO fromEntity(User user){
            if( user == null )
                return null;

            return new UserDTO(
                    user.getId(),
                    user.getUsername()
            );
        }
    }

}
