package br.com.gabrielferreira.cep.api.mappers;

import br.com.gabrielferreira.cep.api.dtos.AddressDTO;
import br.com.gabrielferreira.cep.domain.entities.AddressEntity;
import br.com.gabrielferreira.cep.stub.AddressStub;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DisplayName("Tests for AddressDTOMapper")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AddressDTOMapperTest {

    private final AddressDTOMapper mapper = AddressDTOMapper.INSTANCE;

    @Test
    @Order(1)
    void shouldMapAddressEntityToAddressDTO() {
        AddressEntity addressEntity = AddressStub.getAddressEntity();

        AddressDTO result = mapper.toDTO(addressEntity);
        assertNotNull(result);
        assertEquals(addressEntity.getPostalCode(), result.postalCode());
        assertEquals(addressEntity.getStreet(), result.street());
        assertEquals(addressEntity.getNumber(), result.number());
        assertEquals(addressEntity.getComplement(), result.complement());
        assertEquals(addressEntity.getNeighborhood(), result.neighborhood());
        assertEquals(addressEntity.getCity(), result.city());
        assertEquals(addressEntity.getState(), result.state());
    }

}