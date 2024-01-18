package be.tftic.spring.demo.api.model.form;

import be.tftic.spring.demo.domain.entity.Topic;
import be.tftic.spring.demo.domain.entity.TopicCategory;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record TopicForm(
        @NotBlank String title,
        String description,
        @NotNull TopicCategory category
) {

    public Topic toEntity(){
        Topic topic = new Topic();

        topic.setTitle(title);
        topic.setDescription(description);
        topic.setCategory(category);

        return topic;
    }

}
