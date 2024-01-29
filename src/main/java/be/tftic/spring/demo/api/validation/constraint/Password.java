package be.tftic.spring.demo.api.validation.constraint;

import be.tftic.spring.demo.api.validation.validator.PasswordValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {PasswordValidator.class})
public @interface Password {

    String message() default "{password.invalid}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};


    @AliasFor("value")
    int minLength() default 6;
    @AliasFor("minLength")
    int value() default 6;



}
