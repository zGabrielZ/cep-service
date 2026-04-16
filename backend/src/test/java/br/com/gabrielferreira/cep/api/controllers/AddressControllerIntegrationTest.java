package br.com.gabrielferreira.cep.api.controllers;

import br.com.gabrielferreira.cep.domain.client.AddressClient;
import br.com.gabrielferreira.cep.domain.dto.AddressResponse;
import br.com.gabrielferreira.cep.stub.AddressStub;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("Tests for AddressController Integration")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AddressControllerIntegrationTest extends BaseControllerIntegrationTest {

    private static final String URL = "/v1/addresses";

    @MockitoBean
    private AddressClient addressClient;

    private String postalCode;

    @BeforeEach
    void setUp() {
        postalCode = "123";
    }

    @Test
    @Order(1)
    @SneakyThrows
    void shouldSearchAddressByPostalCode() {
        AddressResponse expectedAddressResponse = AddressStub.getAddressResponse();
        when(addressClient.searchAddressByPostalCode(postalCode))
                .thenReturn(expectedAddressResponse);

        mockMvc.perform(get(URL.concat("/{postalCode}"), postalCode)
                        .contentType(MEDIA_TYPE_JSON)
                        .accept(MEDIA_TYPE_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.postalCode").value(expectedAddressResponse.postalCode()))
                .andExpect(jsonPath("$.street").value(expectedAddressResponse.street()))
                .andExpect(jsonPath("$.number").value(expectedAddressResponse.number()))
                .andExpect(jsonPath("$.complement").value(expectedAddressResponse.complement()))
                .andExpect(jsonPath("$.neighborhood").value(expectedAddressResponse.neighborhood()))
                .andExpect(jsonPath("$.city").value(expectedAddressResponse.city()))
                .andExpect(jsonPath("$.state").value(expectedAddressResponse.state()));
    }
}