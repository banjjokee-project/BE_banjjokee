package com.project.BE_banjjokee.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class ControllerAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(CommentNotFoundException.class)
    public ResponseEntity<ErrorResponse> notFoundExceptionHandler(BusinessException exception) {
        return ResponseEntity.status(exception.getErrorCode().getErrorCode())
                .body(new ErrorResponse(exception.getErrorCode().getErrorMessage()));
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ErrorResponse> notAuthorizedExceptionHandler(BusinessException exception) {
        return ResponseEntity.status(exception.getErrorCode().getErrorCode())
                .body(new ErrorResponse(exception.getErrorCode().getErrorMessage()));
    }

    @ExceptionHandler(CommentInvalidRequestException.class)
    public ResponseEntity<ErrorResponse> invalidRequestExceptionHandler(BusinessException exception) {
        return ResponseEntity.status(exception.getErrorCode().getErrorCode())
                .body(new ErrorResponse(exception.getErrorCode().getErrorMessage()));
    }

}
