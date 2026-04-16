package br.com.gabrielferreira.cep.stub;

import br.com.gabrielferreira.cep.domain.dto.AddressResponse;
import br.com.gabrielferreira.cep.domain.entities.AddressEntity;
import br.com.gabrielferreira.cep.domain.entities.AddressLogEntity;
import br.com.gabrielferreira.cep.domain.enums.Status;

public class AddressStub {

    private AddressStub() {}

    public static AddressResponse getAddressResponse() {
        return AddressResponse.builder()
                .postalCode("12345-678")
                .street("Main Street")
                .number("100")
                .complement("Apt 101")
                .neighborhood("Downtown")
                .city("Sample City")
                .state("SC")
                .build();
    }

    public static AddressEntity getAddressEntity() {
        return AddressEntity.builder()
                .postalCode("12345-678")
                .street("Main Street")
                .number("100")
                .complement("Apt 101")
                .neighborhood("Downtown")
                .city("Sample City")
                .state("SC")
                .build();
    }

    public static AddressLogEntity addressLogEntity(AddressEntity addressEntity, Status status, String errorMessage) {
        return AddressLogEntity.builder()
                .address(addressEntity)
                .addressLogExternalId(java.util.UUID.randomUUID())
                .status(status)
                .errorMessage(errorMessage)
                .build();
    }
}
