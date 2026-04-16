package br.com.gabrielferreira.cep.domain.services.impl;

import br.com.gabrielferreira.cep.domain.dto.AddressResponse;
import br.com.gabrielferreira.cep.domain.entities.AddressLogEntity;
import br.com.gabrielferreira.cep.domain.enums.Status;
import br.com.gabrielferreira.cep.domain.mappers.AddressMapper;
import br.com.gabrielferreira.cep.domain.repositories.AddressLogRepository;
import br.com.gabrielferreira.cep.stub.AddressStub;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@DisplayName("Tests for AddressLogServiceImpl")
@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AddressLogServiceImplTest {

    @InjectMocks
    private AddressLogServiceImpl addressLogService;

    @Mock
    private AddressLogRepository addressLogRepository;

    @Spy
    private AddressMapper mapper = AddressMapper.INSTANCE;

    @Test
    @Order(1)
    void shouldSaveAddressLogEntity() {
        AddressResponse address = AddressStub.getAddressResponse();
        Status status = Status.ERROR;
        String errorMessage = "Error";

        AddressLogEntity addressLogEntity = AddressStub.addressLogEntity(
                mapper.toEntity(address),
                status,
                errorMessage
        );
        when(addressLogRepository.save(addressLogEntity))
                .thenReturn(addressLogEntity);

        AddressLogEntity result = addressLogService.save(address, status, errorMessage);
        assertNotNull(result);
        assertEquals(addressLogEntity, result);
        verify(addressLogRepository).save(addressLogEntity);
    }
}