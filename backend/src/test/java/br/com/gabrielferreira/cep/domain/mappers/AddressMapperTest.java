package br.com.gabrielferreira.cep.domain.mappers;

import br.com.gabrielferreira.cep.domain.dto.AddressResponse;
import br.com.gabrielferreira.cep.domain.entities.AddressEntity;
import br.com.gabrielferreira.cep.domain.entities.AddressLogEntity;
import br.com.gabrielferreira.cep.domain.enums.Status;
import br.com.gabrielferreira.cep.stub.AddressStub;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.time.OffsetDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DisplayName("Tests for AddressMapper")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AddressMapperTest {

    private final AddressMapper mapper = AddressMapper.INSTANCE;

    @Test
    @Order(1)
    void shouldMapAddressResponseToAddressEntity() {
        AddressResponse addressResponse = AddressStub.getAddressResponse();

        AddressEntity result = mapper.toEntity(addressResponse);
        assertNotNull(result);
        assertEquals(addressResponse.postalCode(), result.getPostalCode());
        assertEquals(addressResponse.street(), result.getStreet());
        assertEquals(addressResponse.number(), result.getNumber());
        assertEquals(addressResponse.complement(), result.getComplement());
        assertEquals(addressResponse.neighborhood(), result.getNeighborhood());
        assertEquals(addressResponse.city(), result.getCity());
        assertEquals(addressResponse.state(), result.getState());
    }

    @Test
    @Order(2)
    void shouldMapAddressResponseToAddressLogEntity() {
        AddressResponse addressResponse = AddressStub.getAddressResponse();
        OffsetDateTime loggedAt = OffsetDateTime.now();
        Status status = Status.ERROR;
        String errorMessage = "Test error message";

        AddressLogEntity result = mapper.toLogEntity(addressResponse, loggedAt, status, errorMessage);
        assertNotNull(result);
        assertEquals(addressResponse.postalCode(), result.getAddress().getPostalCode());
        assertEquals(addressResponse.street(), result.getAddress().getStreet());
        assertEquals(addressResponse.number(), result.getAddress().getNumber());
        assertEquals(addressResponse.complement(), result.getAddress().getComplement());
        assertEquals(addressResponse.neighborhood(), result.getAddress().getNeighborhood());
        assertEquals(addressResponse.city(), result.getAddress().getCity());
        assertEquals(addressResponse.state(), result.getAddress().getState());
        assertEquals(loggedAt, result.getLoggedAt());
        assertEquals(status, result.getStatus());
        assertEquals(errorMessage, result.getErrorMessage());
    }
}