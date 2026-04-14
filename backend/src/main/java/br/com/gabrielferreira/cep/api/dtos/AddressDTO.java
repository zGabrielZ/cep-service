package br.com.gabrielferreira.cep.api.dtos;

import lombok.Builder;

import java.io.Serializable;

@Builder
public record AddressDTO(
        String postalCode,
        String street,
        String number,
        String complement,
        String neighborhood,
        String city,
        String state
) implements Serializable {
}
