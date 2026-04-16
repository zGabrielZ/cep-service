package br.com.gabrielferreira.cep.domain.services.impl;

import br.com.gabrielferreira.cep.domain.client.AddressClient;
import br.com.gabrielferreira.cep.domain.dto.AddressResponse;
import br.com.gabrielferreira.cep.domain.entities.AddressEntity;
import br.com.gabrielferreira.cep.domain.entities.AddressLogEntity;
import br.com.gabrielferreira.cep.domain.enums.Status;
import br.com.gabrielferreira.cep.domain.services.AddressLogService;
import br.com.gabrielferreira.cep.stub.AddressStub;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@DisplayName("Tests for AddressServiceImpl")
@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AddressServiceImplTest {

    @InjectMocks
    private AddressServiceImpl addressService;

    @Mock
    private AddressClient addressClient;

    @Mock
    private AddressLogService addressLogService;

    @Test
    @Order(1)
    void shouldSearchAddressByPostalCode() {
        String postalCode = "123";

        AddressResponse addressResponse = AddressStub.getAddressResponse();
        when(addressClient.searchAddressByPostalCode(postalCode))
                .thenReturn(addressResponse);

        AddressLogEntity addressLogEntity = AddressStub.addressLogEntity(
                AddressStub.getAddressEntity(),
                Status.SUCCESS,
                null
        );
        when(addressLogService.save(addressResponse, Status.SUCCESS, null))
                .thenReturn(addressLogEntity);

        AddressEntity result = addressService.searchAddressByPostalCode(postalCode);
        assertNotNull(result);
        assertEquals(addressLogEntity.getAddress(), result);
        verify(addressClient).searchAddressByPostalCode(postalCode);
        verify(addressLogService).save(addressResponse, Status.SUCCESS, null);
    }

    @Test
    @Order(2)
    void shouldNotSearchAddressByPostalCodeWhenOccurredAnyError() {
        String postalCode = "123";

        when(addressClient.searchAddressByPostalCode(postalCode))
                .thenThrow(new RuntimeException("Error"));

        AddressLogEntity addressLogEntity = AddressStub.addressLogEntity(
                null,
                Status.ERROR,
                "Error"
        );
        when(addressLogService.save(null, Status.ERROR, "Error"))
                .thenReturn(addressLogEntity);

        assertThrows(RuntimeException.class, () -> addressService.searchAddressByPostalCode(postalCode));
        verify(addressClient).searchAddressByPostalCode(postalCode);
        verify(addressLogService, never()).save(any(), eq(Status.SUCCESS), any());
    }
}