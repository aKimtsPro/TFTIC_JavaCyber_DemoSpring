package be.tftic.spring.demo.bll.exceptions;

import lombok.Getter;

@Getter
public class EntityAlreadyExistsException extends RuntimeException {
    private final String uniqueField;
    private final Object value;

    public EntityAlreadyExistsException(String uniqueField, Object value) {
        super("Entity with {" +uniqueField+'='+value+"} already exists");
        this.uniqueField = uniqueField;
        this.value = value;
    }
}
