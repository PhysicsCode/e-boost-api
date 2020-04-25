package org.physicscode.controller.advice;

import org.physicscode.dto.pojo.output.error.ErrorDTO;
import org.physicscode.exception.ServiceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import reactor.core.publisher.Mono;

@ControllerAdvice
public class DefaultControllerAdvice {

    @ExceptionHandler(value = ServiceException.class)
    public Mono<ResponseEntity<ErrorDTO>> handleServiceException(ServiceException e) {

        return Mono.just(ResponseEntity.status(e.getErrorCode().getHttpStatus()).body(new ErrorDTO(e.getMessage())));
    }

    @ExceptionHandler(value = Exception.class)
    public Mono<ResponseEntity<ErrorDTO>> handleGenericException(Exception e) {

        return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorDTO(e.getMessage())));
    }
}
