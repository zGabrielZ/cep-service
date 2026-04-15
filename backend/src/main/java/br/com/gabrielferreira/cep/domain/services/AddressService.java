package br.com.gabrielferreira.cep.domain.services;

import br.com.gabrielferreira.cep.domain.entities.AddressEntity;

public interface AddressService {

    AddressEntity searchAddressByPostalCode(String postalCode);
}
