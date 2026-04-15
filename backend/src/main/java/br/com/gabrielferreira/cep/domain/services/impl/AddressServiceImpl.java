package br.com.gabrielferreira.cep.domain.services.impl;

import br.com.gabrielferreira.cep.domain.client.AddressClient;
import br.com.gabrielferreira.cep.domain.dto.AddressResponse;
import br.com.gabrielferreira.cep.domain.entities.AddressEntity;
import br.com.gabrielferreira.cep.domain.entities.AddressLogEntity;
import br.com.gabrielferreira.cep.domain.mappers.AddressMapper;
import br.com.gabrielferreira.cep.domain.repositories.AddressLogRepository;
import br.com.gabrielferreira.cep.domain.services.AddressService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class AddressServiceImpl implements AddressService {

    private final AddressLogRepository addressLogRepository;

    private final AddressClient addressClient;

    private final AddressMapper mapper;

    @Override
    public AddressEntity searchAddressByPostalCode(String postalCode) {
        log.info("Searching address by postal code: {}", postalCode);
        AddressResponse addressResponse = addressClient.searchAddressByPostalCode(postalCode);
        log.info("Address found: {}", addressResponse);

        AddressLogEntity addressLogEntity = mapper.toLogEntity(addressResponse, OffsetDateTime.now());
        log.info("Saving address log: {}", addressLogEntity);
        addressLogEntity = addressLogRepository.save(addressLogEntity);
        return addressLogEntity.getAddress();
    }
}
