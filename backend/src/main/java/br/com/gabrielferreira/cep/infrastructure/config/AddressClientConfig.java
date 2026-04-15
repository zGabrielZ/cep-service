package br.com.gabrielferreira.cep.infrastructure.config;

import br.com.gabrielferreira.cep.infrastructure.decoder.AddressErrorDecoder;
import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Bean;

public class AddressClientConfig {

    @Bean
    public ErrorDecoder errorDecoder() {
        return new AddressErrorDecoder();
    }
}
