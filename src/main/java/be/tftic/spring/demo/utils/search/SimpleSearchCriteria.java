package be.tftic.spring.demo.utils.search;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.From;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

// TODO: fix: dto fieldNames sometimes dont match entity fieldNames
public class SimpleSearchCriteria<T> implements SearchCriteria<T> {

    private final String field;
    private final Operation operation;
    private final String value;

    public SimpleSearchCriteria(String field, Operation operation, String value) {
        this.field = field;
        this.operation = operation;
        this.value = value;
    }

    @Override
    public Specification<T> toSpecification() {
        return switch (this.operation){
            case EQUAL ->           (r,q,cb) -> cb.equal( getPath(r,field), value );
            case NOT_EQUAL ->       (r,q,cb) -> cb.notEqual( getPath(r,field), value );
            case GREATER_EQUAL ->   (r,q,cb) -> cb.ge( getPath(r,field,Number.class), numberFrom(value) );
            case GREATER ->         (r,q,cb) -> cb.gt( getPath(r,field,Number.class), numberFrom(value) );
            case LESSER_EQUAL ->    (r,q,cb) -> cb.le( getPath(r,field,Number.class), numberFrom(value) );
            case LESSER ->          (r,q,cb) -> cb.lt( getPath(r,field,Number.class), numberFrom(value) );
            case AFTER ->           (r,q,cb) -> cb.greaterThan( getPath(r,field,LocalDateTime.class), LocalDateTime.parse(value) ); // TODO wont work for date and time
            case BEFORE ->          (r,q,cb) -> cb.lessThan( getPath(r,field,LocalDateTime.class), LocalDateTime.parse(value) );
            case IN ->              (r,q,cb) -> {
                CriteriaBuilder.In<String> in = cb.in( getPath(r, field) );
                Arrays.stream(handleIn(value)).forEach(in::value);
                return in;
            };
        };
    }

    private Path<String> getPath(Root<T> root, String fieldName){
        return getPath(root,fieldName,String.class);
    }

    private <X> Path<X> getPath(Root<T> root, String fieldName, Class<X> target){
        if( fieldName == null )
            throw new IllegalArgumentException("fieldName should not be null");
        String[] fieldParts = fieldName.split("\\.");

        From<?,?> from = root;
        int i;
        for (i = 0; i<fieldParts.length-1;i++){
            from = root.join(fieldParts[i]);
        }

        return from.get(fieldParts[i]);
    }

    private static String[] handleIn(String value) {
        return value.split(",");
    }

    private static Number numberFrom(String string){
        if( string == null )
            throw new IllegalArgumentException("null string");

        try {
            return Double.parseDouble(string);
        }
        catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

    public static <T> List<SearchCriteria<T>> fromParams(Map<String,String> params, Class<T> tClass){
        return params.entrySet().stream()
                .map((param) -> SimpleSearchCriteria.fromParam(param, tClass))
                .toList();
    }

    private static <T> SearchCriteria<T> fromParam(Map.Entry<String, String> param, Class<T> tClass) {
        String[] key = param.getKey().split("_");
        return new SimpleSearchCriteria<>(
                key.length > 1 ? key[1] : key[0],
                key.length > 1 ? Operation.fromSymbol(key[0]) : Operation.EQUAL,
                param.getValue()
        );
    }
}
