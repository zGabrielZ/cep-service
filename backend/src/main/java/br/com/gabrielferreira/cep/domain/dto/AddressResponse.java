package br.com.gabrielferreira.cep.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.io.Serializable;

@Builder
public record AddressResponse(
        @JsonProperty("cep")
        String postalCode,

        @JsonProperty("logradouro")
        String street,

        @JsonProperty("numero")
        String number,

        @JsonProperty("complemento")
        String complement,

        @JsonProperty("bairro")
        String neighborhood,

        @JsonProperty("localidade")
        String city,

        @JsonProperty("estado")
        String state
) implements Serializable {
}
