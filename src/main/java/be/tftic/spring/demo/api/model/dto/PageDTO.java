package be.tftic.spring.demo.api.model.dto;

import org.springframework.data.domain.Page;

import java.util.Collection;
import java.util.function.Function;

public record PageDTO<T>(
    Collection<T> data,
    PageInfo info
) {

    public static <TENTITY, TDTO> PageDTO<TDTO> fromPage(Page<TENTITY> page, Function<TENTITY,TDTO> mapper){
        PageInfo info = new PageInfo(
                page.getNumber(),
                page.getTotalPages(),
                page.getNumber() > 0 ? page.getNumber() - 1 : null,
                page.getNumber() < page.getTotalPages() ? page.getNumber() + 1 : null,
                page.getSize(),
                page.getNumberOfElements(),
                page.getTotalElements()
        );

        return new PageDTO<>(
                page.getContent()
                        .stream()
                        .map(mapper)
                        .toList(),
                info
        );
    }


    public record PageInfo(
            int currentPage,
            int maxPage,
            Integer previousPage,
            Integer nextPage,
            int pageSize,
            int nbrElements,
            long totalNbrElements
    ){}

}
