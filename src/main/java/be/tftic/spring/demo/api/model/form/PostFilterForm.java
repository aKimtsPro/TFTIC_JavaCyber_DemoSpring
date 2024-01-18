package be.tftic.spring.demo.api.model.form;

import java.time.LocalDateTime;

public record PostFilterForm(
   long topicId,
   LocalDateTime after,
   LocalDateTime before
) {}
