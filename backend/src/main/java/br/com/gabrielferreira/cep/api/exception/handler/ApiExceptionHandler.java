package br.com.gabrielferreira.cep.api.exception.handler;

import br.com.gabrielferreira.cep.api.exception.dtos.ProblemDetailDTO;
import feign.RetryableException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.OffsetDateTime;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleUncaught(Exception ex, WebRequest request) {
        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        ProblemDetailDTO problemDetailDto = ProblemDetailDTO.builder()
                .status(httpStatus.value())
                .title("An unexpected error occurred")
                .detail(ex.getMessage())
                .message("An internal system error has occurred. Please try again later.")
                .timestamp(OffsetDateTime.now())
                .build();
        return handleExceptionInternal(ex, problemDetailDto, new HttpHeaders(), httpStatus, request);
    }

    @ExceptionHandler(RetryableException.class)
    public ResponseEntity<Object> handleFeignClientException(RetryableException ex, WebRequest request) {
        HttpStatus httpStatus = HttpStatus.SERVICE_UNAVAILABLE;
        ProblemDetailDTO problemDetailDto = ProblemDetailDTO.builder()
                .status(httpStatus.value())
                .title("External service unavailable")
                .detail("The external service is currently unavailable. Please try again later.")
                .message("The external service is currently unavailable. Please try again later.")
                .timestamp(OffsetDateTime.now())
                .build();
        return handleExceptionInternal(ex, problemDetailDto, new HttpHeaders(), httpStatus, request);
    }
}
