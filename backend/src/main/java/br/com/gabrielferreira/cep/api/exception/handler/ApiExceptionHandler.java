package br.com.gabrielferreira.cep.api.exception.handler;

import br.com.gabrielferreira.cep.api.exception.dtos.ProblemDetailDTO;
import br.com.gabrielferreira.cep.api.exception.mappers.ProblemDetailMapper;
import br.com.gabrielferreira.cep.domain.exceptions.BadGatewayException;
import br.com.gabrielferreira.cep.domain.exceptions.BadRequestException;
import br.com.gabrielferreira.cep.domain.exceptions.ConflictException;
import br.com.gabrielferreira.cep.domain.exceptions.NotFoundException;
import feign.RetryableException;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.OffsetDateTime;

@ControllerAdvice
@Slf4j
@RequiredArgsConstructor
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    private final ProblemDetailMapper mapper;

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ApiResponse(
            responseCode = "500",
            description = "Internal Server Error - An unexpected error occurred while processing the request.",
            content = @Content(
                    mediaType = "application/json",
                    examples = {
                            @ExampleObject(
                                    name = "Internal Server Error Example",
                                    value = """
                                            {
                                              "status": 500,
                                              "title": "An unexpected error occurred",
                                              "detail": "NullPointerException at line 42",
                                              "userMessage": "An internal system error has occurred. Please try again later.",
                                              "timestamp": "2024-06-01T12:00:00Z"
                                            }
                                            """
                            )
                    }
            )
    )
    public ResponseEntity<Object> handleUncaught(Exception ex, WebRequest request) {
        log.error("An unexpected error occurred: {}", ex.getMessage(), ex);
        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        ProblemDetailDTO problemDetailDTO = mapper.toProblemDetailDto(
                httpStatus.value(),
                "An unexpected error occurred",
                ex.getMessage(),
                "An internal system error has occurred. Please try again later.",
                OffsetDateTime.now()
        );
        return handleExceptionInternal(ex, problemDetailDTO, new HttpHeaders(), httpStatus, request);
    }

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ApiResponse(
            responseCode = "400",
            description = "Bad Request - The request could not be processed due to invalid input.",
            content = @Content(
                    mediaType = "application/json",
                    examples = {
                            @ExampleObject(
                                    name = "Bad Request Example",
                                    value = """
                                            {
                                              "status": 400,
                                              "title": "Invalid input data",
                                              "detail": "The 'cep' field must be a valid Brazilian postal code.",
                                              "userMessage": "The request could not be processed due to invalid input. Please check your request and try again.",
                                              "timestamp": "2024-06-01T12:00:00Z"
                                            }
                                            """
                            )
                    }
            )
    )
    public ResponseEntity<Object> handleBadRequest(BadRequestException ex, WebRequest request) {
        log.error("Bad request error: {}", ex.getMessage(), ex);
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        ProblemDetailDTO problemDetailDTO = mapper.toProblemDetailDto(
                httpStatus.value(),
                "Bad Request",
                ex.getMessage(),
                "The request could not be processed due to invalid input. Please check your request and try again.",
                OffsetDateTime.now()
        );
        return handleExceptionInternal(ex, problemDetailDTO, new HttpHeaders(), httpStatus, request);
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ApiResponse(
            responseCode = "404",
            description = "Resource Not Found - The requested resource was not found.",
            content = @Content(
                    mediaType = "application/json",
                    examples = {
                            @ExampleObject(
                                    name = "Not Found Example",
                                    value = """
                                            {
                                              "status": 404,
                                              "title": "Resource Not Found",
                                              "detail": "No address found for the provided CEP.",
                                              "userMessage": "The requested resource was not found. Please verify the request and try again.",
                                              "timestamp": "2024-06-01T12:00:00Z"
                                            }
                                            """
                            )
                    }
            )
    )
    public ResponseEntity<Object> handleNotFound(NotFoundException ex, WebRequest request) {
        log.error("Resource not found: {}", ex.getMessage(), ex);
        HttpStatus httpStatus = HttpStatus.NOT_FOUND;
        ProblemDetailDTO problemDetailDTO = mapper.toProblemDetailDto(
                httpStatus.value(),
                "Resource Not Found",
                ex.getMessage(),
                "The requested resource was not found. Please verify the request and try again.",
                OffsetDateTime.now()
        );
        return handleExceptionInternal(ex, problemDetailDTO, new HttpHeaders(), httpStatus, request);
    }

    @ExceptionHandler(ConflictException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    @ApiResponse(
            responseCode = "409",
            description = "Conflict - A conflict occurred while processing the request.",
            content = @Content(
                    mediaType = "application/json",
                    examples = {
                            @ExampleObject(
                                    name = "Conflict Example",
                                    value = """
                                            {
                                              "status": 409,
                                              "title": "Conflict",
                                              "detail": "The provided CEP already exists in the system.",
                                              "userMessage": "A conflict occurred while processing the request. Please try again later.",
                                              "timestamp": "2024-06-01T12:00:00Z"
                                            }
                                            """
                            )
                    }
            )
    )
    public ResponseEntity<Object> handleConflict(ConflictException ex, WebRequest request) {
        log.error("Conflict error: {}", ex.getMessage(), ex);
        HttpStatus httpStatus = HttpStatus.CONFLICT;
        ProblemDetailDTO problemDetailDTO = mapper.toProblemDetailDto(
                httpStatus.value(),
                "Conflict",
                ex.getMessage(),
                "A conflict occurred while processing the request. Please try again later.",
                OffsetDateTime.now()
        );
        return handleExceptionInternal(ex, problemDetailDTO, new HttpHeaders(), httpStatus, request);
    }

    @ExceptionHandler(BadGatewayException.class)
    @ResponseStatus(HttpStatus.BAD_GATEWAY)
    @ApiResponse(
            responseCode = "502",
            description = "Bad Gateway - An error occurred while communicating with the external service.",
            content = @Content(
                    mediaType = "application/json",
                    examples = {
                            @ExampleObject(
                                    name = "Bad Gateway Example",
                                    value = """
                                            {
                                              "status": 502,
                                              "title": "Bad Gateway",
                                              "detail": "Failed to retrieve address information from the external service.",
                                              "userMessage": "An error occurred while communicating with the external service. Please try again later.",
                                              "timestamp": "2024-06-01T12:00:00Z"
                                            }
                                            """
                            )
                    }
            )
    )
    public ResponseEntity<Object> handleBadGateway(BadGatewayException ex, WebRequest request) {
        log.error("Bad gateway error: {}", ex.getMessage(), ex);
        HttpStatus httpStatus = HttpStatus.BAD_GATEWAY;
        ProblemDetailDTO problemDetailDTO = mapper.toProblemDetailDto(
                httpStatus.value(),
                "Bad Gateway",
                ex.getMessage(),
                "An error occurred while communicating with the external service. Please try again later.",
                OffsetDateTime.now()
        );
        return handleExceptionInternal(ex, problemDetailDTO, new HttpHeaders(), httpStatus, request);
    }

    @ExceptionHandler(RetryableException.class)
    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    @ApiResponse(
            responseCode = "503",
            description = "Service Unavailable - The external service is currently unavailable.",
            content = @Content(
                    mediaType = "application/json",
                    examples = {
                            @ExampleObject(
                                    name = "Service Unavailable Example",
                                    value = """
                                            {
                                              "status": 503,
                                              "title": "External Service Unavailable",
                                              "detail": "The external service is currently unavailable. Please try again later.",
                                              "userMessage": "The external service is currently unavailable. Please try again later.",
                                              "timestamp": "2024-06-01T12:00:00Z"
                                            }
                                            """
                            )
                    }
            )
    )
    public ResponseEntity<Object> handleFeignClientException(RetryableException ex, WebRequest request) {
        log.error("External service is unavailable: {}", ex.getMessage(), ex);
        HttpStatus httpStatus = HttpStatus.SERVICE_UNAVAILABLE;
        ProblemDetailDTO problemDetailDTO = mapper.toProblemDetailDto(
                httpStatus.value(),
                "External Service Unavailable",
                ex.getMessage(),
                "The external service is currently unavailable. Please try again later.",
                OffsetDateTime.now()
        );
        return handleExceptionInternal(ex, problemDetailDTO, new HttpHeaders(), httpStatus, request);
    }
}
