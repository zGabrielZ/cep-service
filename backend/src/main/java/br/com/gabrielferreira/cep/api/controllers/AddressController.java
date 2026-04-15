package br.com.gabrielferreira.cep.api.controllers;

import br.com.gabrielferreira.cep.api.dtos.AddressDTO;
import br.com.gabrielferreira.cep.api.mappers.AddressDTOMapper;
import br.com.gabrielferreira.cep.domain.entities.AddressEntity;
import br.com.gabrielferreira.cep.domain.services.AddressService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Addresses", description = "Endpoints for address management")
@RestController
@RequestMapping("/v1/addresses")
@RequiredArgsConstructor
public class AddressController {

    private final AddressService addressService;

    private final AddressDTOMapper mapper;

    @Operation(summary = "Search Address by Postal Code")
    @ApiResponse(
            responseCode = "200",
            description = "Address found successfully"
    )
    @GetMapping("/{postalCode}")
    public ResponseEntity<AddressDTO> searchAddressByPostalCode(
            @Parameter(
                    description = "Postal code to search for the address",
                    example = "03342900",
                    required = true
            )
            @PathVariable String postalCode
    ) {
        AddressEntity addressEntity = addressService.searchAddressByPostalCode(postalCode);
        AddressDTO addressDTO = mapper.toDTO(addressEntity);
        return ResponseEntity.ok(addressDTO);
    }
}
