package br.com.gabrielferreira.cep.infrastructure.decoder;

import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

@Slf4j
public class AddressErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String methodKey, Response response) {
        try {
            String responseBody = Objects.nonNull(response.body()) ? response.body().toString() : "No response body";
            log.error("Error occurred during Feign client call. Method: {}, Status: {}, Response Body: {}",
                    methodKey, response.status(), responseBody);
        } catch (Exception e) {
            log.error("Failed to read response body for error decoding. Method: {}, Status: {}",
                    methodKey, response.status(), e);
        }
        return new RuntimeException("An error occurred while calling the external address service. Please try again later.");
    }
}
