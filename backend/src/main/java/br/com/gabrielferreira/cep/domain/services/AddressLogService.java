package br.com.gabrielferreira.cep.domain.services;

import br.com.gabrielferreira.cep.domain.dto.AddressResponse;
import br.com.gabrielferreira.cep.domain.entities.AddressLogEntity;
import br.com.gabrielferreira.cep.domain.enums.Status;

public interface AddressLogService {

    AddressLogEntity save(AddressResponse address, Status status, String errorMessage);
}
