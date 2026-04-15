package br.com.gabrielferreira.cep.domain.client;

import br.com.gabrielferreira.cep.domain.dto.AddressResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "addressClient", url = "${clients.address.url}")
public interface AddressClient {

    @GetMapping("/{cep}/json")
    AddressResponse searchAddressByPostalCode(@PathVariable("cep") String postalCode);
}
