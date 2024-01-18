package be.tftic.spring.demo.utils.specifications;

import be.tftic.spring.demo.utils.search.SearchCriteria;
import lombok.NonNull;
import org.springframework.data.jpa.domain.Specification;

import java.util.*;

public class SpecJoiner<T> {

    private final Class<T> clazz;
    private final Set<Specification<T>> specs = new HashSet<>();
    private final PredicateJoiner specJoiner;
    private SpecJoiner<T> parent;
    private final Set<SpecJoiner<T>> children = new HashSet<>();
    private boolean generalNot;
    private boolean oneTimeNot = false;


    SpecJoiner(
            @NonNull PredicateJoiner joiner,
            Class<T> clazz
    ){
        this.clazz = clazz;
        this.specJoiner = joiner;
        this.parent = null;
        this.generalNot = false;
    }
    private SpecJoiner(
            @NonNull SpecJoiner<T> parent,
            @NonNull PredicateJoiner joiner,
            boolean generalNot
    ) {
        this.clazz = parent.clazz;
        this.specJoiner = joiner;
        this.parent = parent;
        this.parent.children.add(this);
        this.generalNot = generalNot;
    }

    public SpecJoiner<T> not(){
        this.oneTimeNot = !this.oneTimeNot;
        return this;
    }

    public static <T> SpecJoiner<T> joint(PredicateJoiner joiner, Class<T> tClass){
        return switch (joiner) {
            case AND -> andJoint(tClass);
            case OR -> orJoint(tClass);
        };
    }

    public static <T> SpecJoiner<T> andJoint(Class<T> clazz){
        return new SpecJoiner<>(PredicateJoiner.AND,clazz);
    }

    public SpecJoiner<T> addAndJoint(){
        return addJoint(PredicateJoiner.AND);
    }
    public SpecJoiner<T> addOrJoint(){
        return addJoint(PredicateJoiner.OR);
    }

    private SpecJoiner<T> addJoint(PredicateJoiner joiner){
        if(oneTimeNot){
            oneTimeNot = false;
            return new SpecJoiner<>(this, PredicateJoiner.AND, true);
        }
        else {
            return new SpecJoiner<>(this, PredicateJoiner.AND, false);
        }
    }

    private SpecJoiner<T> addJoint(SpecJoiner<T> joiner) {
        if(oneTimeNot){
            oneTimeNot = false;
            joiner.generalNot = true;
        }
        joiner.parent = this;
        this.children.add(joiner);
        return this;
    }

    public SpecJoiner<T> endJoint(){
        return parent;
    }

    public static <T> SpecJoiner<T> orJoint(Class<T> clazz){
        return new SpecJoiner<>(PredicateJoiner.OR, clazz);
    }

    public SpecJoiner<T> spec(Specification<T> spec) {
        this.specs.add( applyNot(spec) );
        return this;
    }

    public SpecJoiner<T> criteria(SearchCriteria<T> searchCriteria) {
        this.specs.add( searchCriteria.toSpecification() );
        return this;
    }

    public SpecJoiner<T> criterias(Collection<SearchCriteria<T>> searchCriterias){
        searchCriterias.forEach( this::criteria );
        return this;
    }

    public SpecJoiner<T> equals(String fieldName, Object value){
        Specification<T> spec = (root, query, cb) -> cb.equal(root.get(fieldName), value);
        specs.add( applyNot(spec) );
        return this;
    }

    public <C extends Comparable<C>> SpecJoiner<T> less(String fieldName, C value){
        Specification<T> spec = (r,q,cb) -> cb.lessThan(r.get(fieldName), value);
        specs.add(spec);
        return this;
    }

    public <C extends Comparable<C>> SpecJoiner<T> lessEqual(String fieldName, C value){
        Specification<T> spec = (r,q,cb) -> cb.lessThanOrEqualTo(r.get(fieldName), value);
        specs.add(spec);
        return this;
    }

    public Specification<T> build(){
        SpecJoiner<T> root = this;
        while(root.parent != null){
            root = parent;
        }
        return root.buildNode();
    }

    private Specification<T> buildNode(){
        List<Specification<T>> specs = new ArrayList<>( this.specs );
        specs.addAll(
                children.stream()
                    .map(SpecJoiner::buildNode)
                    .toList()
        );

        Specification<T> spec = switch (this.specJoiner) {
            case AND -> Specification.allOf(specs);
            case OR -> Specification.anyOf(specs);
        };

        return generalNot ? Specification.not(spec) : spec;
    }

    private Specification<T> applyNot(Specification<T> spec) {
        if(oneTimeNot) {
            oneTimeNot = false;
            spec = Specification.not(spec);
        }
        return spec;
    }

}
