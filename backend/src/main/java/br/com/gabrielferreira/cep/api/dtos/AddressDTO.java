package br.com.gabrielferreira.cep.api.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.io.Serializable;

@Builder
public record AddressDTO(
        @Schema(
                description = "Postal code of the address",
                example = "03342900"
        )
        String postalCode,

        @Schema(
                description = "Street name of the address",
                example = "Avenida Regente Feijó"
        )
        String street,

        @Schema(
                description = "Number of the address",
                example = "123"
        )
        String number,

        @Schema(
                description = "Complement of the address",
                example = "Shopping"
        )
        String complement,

        @Schema(
                description = "Neighborhood of the address",
                example = "Vila Regente Feijó"
        )
        String neighborhood,

        @Schema(
                description = "City of the address",
                example = "São Paulo"
        )
        String city,

        @Schema(
                description = "State of the address",
                example = "SP"
        )
        String state
) implements Serializable {
}
