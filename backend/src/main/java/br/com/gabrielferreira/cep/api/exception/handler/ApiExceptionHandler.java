package br.com.gabrielferreira.cep.api.exception.handler;

import br.com.gabrielferreira.cep.api.exception.dtos.ProblemDetailDTO;
import br.com.gabrielferreira.cep.domain.exceptions.BadGatewayException;
import br.com.gabrielferreira.cep.domain.exceptions.BadRequestException;
import br.com.gabrielferreira.cep.domain.exceptions.ConflictException;
import br.com.gabrielferreira.cep.domain.exceptions.NotFoundException;
import feign.RetryableException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.OffsetDateTime;

@ControllerAdvice
@Slf4j
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleUncaught(Exception ex, WebRequest request) {
        log.error("An unexpected error occurred: {}", ex.getMessage(), ex);
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

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<Object> handleBadRequest(Exception ex, WebRequest request) {
        log.error("Bad request error: {}", ex.getMessage(), ex);
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        ProblemDetailDTO problemDetailDto = ProblemDetailDTO.builder()
                .status(httpStatus.value())
                .title("Bad Request")
                .detail(ex.getMessage())
                .message("The request could not be processed due to invalid input. Please check your request and try again.")
                .timestamp(OffsetDateTime.now())
                .build();
        return handleExceptionInternal(ex, problemDetailDto, new HttpHeaders(), httpStatus, request);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Object> handleNotFound(Exception ex, WebRequest request) {
        log.error("Resource not found: {}", ex.getMessage(), ex);
        HttpStatus httpStatus = HttpStatus.NOT_FOUND;
        ProblemDetailDTO problemDetailDto = ProblemDetailDTO.builder()
                .status(httpStatus.value())
                .title("Resource Not Found")
                .detail(ex.getMessage())
                .message("The requested resource was not found. Please verify the request and try again.")
                .timestamp(OffsetDateTime.now())
                .build();
        return handleExceptionInternal(ex, problemDetailDto, new HttpHeaders(), httpStatus, request);
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<Object> handleConflict(Exception ex, WebRequest request) {
        log.error("Conflict error: {}", ex.getMessage(), ex);
        HttpStatus httpStatus = HttpStatus.CONFLICT;
        ProblemDetailDTO problemDetailDto = ProblemDetailDTO.builder()
                .status(httpStatus.value())
                .title("Conflict")
                .detail(ex.getMessage())
                .message("A conflict occurred while processing the request. Please try again later.")
                .timestamp(OffsetDateTime.now())
                .build();
        return handleExceptionInternal(ex, problemDetailDto, new HttpHeaders(), httpStatus, request);
    }

    @ExceptionHandler(BadGatewayException.class)
    public ResponseEntity<Object> handleBadGateway(Exception ex, WebRequest request) {
        log.error("Bad gateway error: {}", ex.getMessage(), ex);
        HttpStatus httpStatus = HttpStatus.BAD_GATEWAY;
        ProblemDetailDTO problemDetailDto = ProblemDetailDTO.builder()
                .status(httpStatus.value())
                .title("Bad Gateway")
                .detail(ex.getMessage())
                .message("An error occurred while communicating with the external service. Please try again later.")
                .timestamp(OffsetDateTime.now())
                .build();
        return handleExceptionInternal(ex, problemDetailDto, new HttpHeaders(), httpStatus, request);
    }

    @ExceptionHandler(RetryableException.class)
    public ResponseEntity<Object> handleFeignClientException(RetryableException ex, WebRequest request) {
        log.error("External service is unavailable: {}", ex.getMessage(), ex);
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
