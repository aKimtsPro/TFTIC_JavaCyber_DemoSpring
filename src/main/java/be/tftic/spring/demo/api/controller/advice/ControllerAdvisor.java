package be.tftic.spring.demo.api.controller.advice;

import be.tftic.spring.demo.api.model.dto.ErrorDTO;
import be.tftic.spring.demo.api.model.dto.ValidationErrorDTO;
import be.tftic.spring.demo.bll.exceptions.EntityNotFoundException;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class ControllerAdvisor {

    private final MessageSource messageSource;

    public ControllerAdvisor(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorDTO> handle(EntityNotFoundException ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(
                        new ErrorDTO(
                                HttpStatus.NOT_FOUND,
                                ex.getMessage()
                        )
                );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationErrorDTO> handle(MethodArgumentNotValidException ex){

        return ResponseEntity.status( HttpStatus.UNPROCESSABLE_ENTITY )
                .body(
                        new ValidationErrorDTO(
                                HttpStatus.UNPROCESSABLE_ENTITY,
                                messageSource.getMessage("form.invalid", null, LocaleContextHolder.getLocale()),
                                ex.getBindingResult()
                        )
                );
    }

}
