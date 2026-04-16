package br.com.gabrielferreira.cep.api.exception.handler;

import br.com.gabrielferreira.cep.api.exception.dtos.ProblemDetailDTO;
import br.com.gabrielferreira.cep.api.exception.mappers.ProblemDetailMapper;
import br.com.gabrielferreira.cep.domain.exceptions.BadGatewayException;
import br.com.gabrielferreira.cep.domain.exceptions.BadRequestException;
import br.com.gabrielferreira.cep.domain.exceptions.ConflictException;
import br.com.gabrielferreira.cep.domain.exceptions.NotFoundException;
import feign.RetryableException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.ServletWebRequest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@DisplayName("Tests for ApiExceptionHandler class")
@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ApiExceptionHandlerTest {

    @InjectMocks
    private ApiExceptionHandler handler;

    @Spy
    private ProblemDetailMapper mapper = ProblemDetailMapper.INSTANCE;

    @Test
    @Order(1)
    void shouldHandleUncaught() {
        RuntimeException runtimeException = new RuntimeException("Error");
        ServletWebRequest webRequest = createWebRequest();

        ResponseEntity<Object> result = handler.handleUncaught(runtimeException, webRequest);
        assertNotNull(result);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, result.getStatusCode());

        ProblemDetailDTO problemDetail = (ProblemDetailDTO) result.getBody();
        assertNotNull(problemDetail);
        assertEquals(500, problemDetail.status());
        assertEquals("An unexpected error occurred", problemDetail.title());
        assertNotNull(problemDetail.timestamp());
        verify(mapper, times(1)).toProblemDetailDto(
                eq(500),
                eq("An unexpected error occurred"),
                eq("Error"),
                eq("An internal system error has occurred. Please try again later."),
                any()
        );
    }

    @Test
    @Order(2)
    void shouldHandleBadRequest() {
        BadRequestException badRequestException = new BadRequestException("Error");
        ServletWebRequest webRequest = createWebRequest();

        ResponseEntity<Object> result = handler.handleBadRequest(badRequestException, webRequest);
        assertNotNull(result);
        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());

        ProblemDetailDTO problemDetail = (ProblemDetailDTO) result.getBody();
        assertNotNull(problemDetail);
        assertEquals(400, problemDetail.status());
        assertEquals("Bad Request", problemDetail.title());
        assertNotNull(problemDetail.timestamp());
        verify(mapper, times(1)).toProblemDetailDto(
                eq(400),
                eq("Bad Request"),
                eq("Error"),
                eq("The request could not be processed due to invalid input. Please check your request and try again."),
                any()
        );
    }

    @Test
    @Order(3)
    void shouldHandleNotFound() {
        NotFoundException notFoundException = new NotFoundException("Resource not found");
        ServletWebRequest webRequest = createWebRequest();

        ResponseEntity<Object> result = handler.handleNotFound(notFoundException, webRequest);
        assertNotNull(result);
        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());

        ProblemDetailDTO problemDetail = (ProblemDetailDTO) result.getBody();
        assertNotNull(problemDetail);
        assertEquals(404, problemDetail.status());
        assertEquals("Resource Not Found", problemDetail.title());
        assertNotNull(problemDetail.timestamp());
        verify(mapper, times(1)).toProblemDetailDto(
                eq(404),
                eq("Resource Not Found"),
                eq("Resource not found"),
                eq("The requested resource was not found. Please verify the request and try again."),
                any()
        );
    }

    @Test
    @Order(4)
    void shouldHandleConflict() {
        ConflictException conflictException = new ConflictException("Conflict occurred");
        ServletWebRequest webRequest = createWebRequest();

        ResponseEntity<Object> result = handler.handleConflict(conflictException, webRequest);
        assertNotNull(result);
        assertEquals(HttpStatus.CONFLICT, result.getStatusCode());

        ProblemDetailDTO problemDetail = (ProblemDetailDTO) result.getBody();
        assertNotNull(problemDetail);
        assertEquals(409, problemDetail.status());
        assertEquals("Conflict", problemDetail.title());
        assertNotNull(problemDetail.timestamp());
        verify(mapper, times(1)).toProblemDetailDto(
                eq(409),
                eq("Conflict"),
                eq("Conflict occurred"),
                eq("A conflict occurred while processing the request. Please try again later."),
                any()
        );
    }

    @Test
    @Order(5)
    void shouldHandleBadGateway() {
        BadGatewayException badGatewayException = new BadGatewayException("External service error");
        ServletWebRequest webRequest = createWebRequest();

        ResponseEntity<Object> result = handler.handleBadGateway(badGatewayException, webRequest);
        assertNotNull(result);
        assertEquals(HttpStatus.BAD_GATEWAY, result.getStatusCode());

        ProblemDetailDTO problemDetail = (ProblemDetailDTO) result.getBody();
        assertNotNull(problemDetail);
        assertEquals(502, problemDetail.status());
        assertEquals("Bad Gateway", problemDetail.title());
        assertNotNull(problemDetail.timestamp());
        verify(mapper, times(1)).toProblemDetailDto(
                eq(502),
                eq("Bad Gateway"),
                eq("External service error"),
                eq("An error occurred while communicating with the external service. Please try again later."),
                any()
        );
    }

    @Test
    @Order(6)
    void shouldHandleFeignClientException() {
        RetryableException retryableException = mock(RetryableException.class);
        when(retryableException.getMessage()).thenReturn("Service unavailable");
        ServletWebRequest webRequest = createWebRequest();

        ResponseEntity<Object> result = handler.handleFeignClientException(retryableException, webRequest);
        assertNotNull(result);
        assertEquals(HttpStatus.SERVICE_UNAVAILABLE, result.getStatusCode());

        ProblemDetailDTO problemDetail = (ProblemDetailDTO) result.getBody();
        assertNotNull(problemDetail);
        assertEquals(503, problemDetail.status());
        assertEquals("External Service Unavailable", problemDetail.title());
        assertNotNull(problemDetail.timestamp());
        verify(mapper, times(1)).toProblemDetailDto(
                eq(503),
                eq("External Service Unavailable"),
                eq("The external service is currently unavailable. Please try again later."),
                eq("The external service is currently unavailable. Please try again later."),
                any()
        );
    }

    private ServletWebRequest createWebRequest() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        return new ServletWebRequest(request);
    }
}