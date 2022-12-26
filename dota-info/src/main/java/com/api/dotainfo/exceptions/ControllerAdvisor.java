package com.api.dotainfo.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@ControllerAdvice
public class ControllerAdvisor extends ResponseEntityExceptionHandler {

    @ExceptionHandler({PlayerTagNotAvailableException.class, TeamNameNotAvailableException.class})
    public ResponseEntity<Object> handleTagInUseException(RuntimeException exception, WebRequest request) {

        ExceptionDetails exceptionDetails =
                new ExceptionDetails(new Date(), exception.getMessage(), request.getDescription(false));

        return ResponseEntity.status(HttpStatus.CONFLICT).body(exceptionDetails);
    }

    @ExceptionHandler({TeamNotFoundException.class, PlayerNotFoundException.class})
    public ResponseEntity<Object> handleEntityNotFoundException(RuntimeException exception, WebRequest request) {

        ExceptionDetails exceptionDetails =
                new ExceptionDetails(new Date(), exception.getMessage(), request.getDescription(false));

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exceptionDetails);
    }

    @ExceptionHandler({InvalidPlayerRoleNumberException.class, TeamCompositionException.class})
    public ResponseEntity<Object> handleInvalidPlayerRoleNumberException(RuntimeException exception,
                                                                         WebRequest request) {

        ExceptionDetails exceptionDetails =
                new ExceptionDetails(new Date(), exception.getMessage(), request.getDescription(false));

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exceptionDetails);
    }
}
