package be.tftic.spring.demo.bll.exceptions;

public class EntityNotFoundException extends RuntimeException {

    private final Object id;
    private final Class<?> entityType;

    public EntityNotFoundException(Object id, Class<?> entityType) {
        super("entity {"+entityType.getSimpleName()+"} could not be found with id {"+id+"}");
        this.id = id;
        this.entityType = entityType;
    }

    public EntityNotFoundException(String message, Object id, Class<?> entityType) {
        super(message);
        this.id = id;
        this.entityType = entityType;
    }
}
