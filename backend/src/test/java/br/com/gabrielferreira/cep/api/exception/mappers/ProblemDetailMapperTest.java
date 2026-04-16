package br.com.gabrielferreira.cep.api.exception.mappers;

import br.com.gabrielferreira.cep.api.exception.dtos.ProblemDetailDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.time.OffsetDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DisplayName("Tests for ProblemDetailMapper")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ProblemDetailMapperTest {

    private final ProblemDetailMapper mapper = ProblemDetailMapper.INSTANCE;

    @Test
    @Order(1)
    void shouldMapToProblemDetailDTO() {
        Integer status = 400;
        String title = "Bad Request";
        String detail = "Invalid input data";
        String message = "Validation failed for the request";
        OffsetDateTime timestamp = OffsetDateTime.now();

        ProblemDetailDTO result = mapper.toProblemDetailDto(status, title, detail, message, timestamp);

        assertNotNull(result);
        assertEquals(status, result.status());
        assertEquals(title, result.title());
        assertEquals(detail, result.detail());
        assertEquals(message, result.message());
        assertEquals(timestamp, result.timestamp());
    }
}