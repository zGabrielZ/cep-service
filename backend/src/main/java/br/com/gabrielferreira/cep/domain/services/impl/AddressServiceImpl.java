package br.com.gabrielferreira.cep.domain.services.impl;

import br.com.gabrielferreira.cep.domain.client.AddressClient;
import br.com.gabrielferreira.cep.domain.dto.AddressResponse;
import br.com.gabrielferreira.cep.domain.entities.AddressEntity;
import br.com.gabrielferreira.cep.domain.entities.AddressLogEntity;
import br.com.gabrielferreira.cep.domain.enums.Status;
import br.com.gabrielferreira.cep.domain.services.AddressLogService;
import br.com.gabrielferreira.cep.domain.services.AddressService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AddressServiceImpl implements AddressService {

    private final AddressClient addressClient;

    private final AddressLogService addressLogService;

    @Override
    public AddressEntity searchAddressByPostalCode(String postalCode) {
        log.info("Searching address by postal code: {}", postalCode);
        AddressResponse addressResponse = searchAddress(postalCode);
        AddressLogEntity addressLogEntity = addressLogService.save(addressResponse, Status.SUCCESS, null);
        return addressLogEntity.getAddress();
    }

    private AddressResponse searchAddress(String postalCode) {
        AddressResponse addressResponse = null;
        try {
            addressResponse = addressClient.searchAddressByPostalCode(postalCode);
            log.info("Address found: {}", addressResponse);
        } catch (Exception e) {
            addressLogService.save(addressResponse, Status.ERROR, e.getMessage());
            throw e;
        }
        return addressResponse;
    }
}
