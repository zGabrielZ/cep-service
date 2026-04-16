package br.com.gabrielferreira.cep.infrastructure.decoder;

import br.com.gabrielferreira.cep.domain.exceptions.BadGatewayException;
import br.com.gabrielferreira.cep.domain.exceptions.BadRequestException;
import br.com.gabrielferreira.cep.domain.exceptions.ConflictException;
import br.com.gabrielferreira.cep.domain.exceptions.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

@DisplayName("Tests for AddressErrorDecoder")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AddressErrorDecoderTest {

    private AddressErrorDecoder addressErrorDecoder;

    @BeforeEach
    void setUp() {
        addressErrorDecoder = new AddressErrorDecoder();
    }

    @Test
    @Order(1)
    void shouldReturnBadRequestExceptionFor400Status() {
        Exception exception = addressErrorDecoder.decode("methodKey", createResponse(400));
        assertInstanceOf(BadRequestException.class, exception);
        assertEquals("Invalid request to external address service. Please check the provided data and try again.", exception.getMessage());
    }

    @Test
    @Order(2)
    void shouldReturnNotFoundExceptionFor404Status() {
        Exception exception = addressErrorDecoder.decode("methodKey", createResponse(404));
        assertInstanceOf(NotFoundException.class, exception);
        assertEquals("Address not found for the provided postal code. Please verify the postal code and try again.", exception.getMessage());
    }

    @Test
    @Order(3)
    void shouldReturnConflictExceptionFor409Status() {
        Exception exception = addressErrorDecoder.decode("methodKey", createResponse(409));
        assertInstanceOf(ConflictException.class, exception);
        assertEquals("Conflict occurred while processing the request to the external address service. Please try again later.", exception.getMessage());
    }

    @Test
    @Order(4)
    void shouldReturnBadGatewayExceptionForOtherStatus() {
        Exception exception = addressErrorDecoder.decode("methodKey", createResponse(500));
        assertInstanceOf(BadGatewayException.class, exception);
        assertEquals("Client error occurred while calling the external address service. Please check your request and try again.", exception.getMessage());
    }

    private feign.Response createResponse(int status) {
        feign.Request request = feign.Request.create(
                feign.Request.HttpMethod.GET,
                "https://example.com/cep",
                Map.of(),
                null,
                java.nio.charset.StandardCharsets.UTF_8,
                null
        );

        return feign.Response.builder()
                .status(status)
                .reason("Mocked response")
                .request(request)
                .headers(Map.of())
                .body("Error mock", java.nio.charset.StandardCharsets.UTF_8)
                .build();
    }
}