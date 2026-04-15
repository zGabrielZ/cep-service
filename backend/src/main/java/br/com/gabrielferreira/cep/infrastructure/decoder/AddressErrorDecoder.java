package br.com.gabrielferreira.cep.infrastructure.decoder;

import br.com.gabrielferreira.cep.domain.exceptions.BadGatewayException;
import br.com.gabrielferreira.cep.domain.exceptions.BadRequestException;
import br.com.gabrielferreira.cep.domain.exceptions.ConflictException;
import br.com.gabrielferreira.cep.domain.exceptions.NotFoundException;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

@Slf4j
public class AddressErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String methodKey, Response response) {
        return handleClientError(response.status());
    }

    private RuntimeException handleClientError(Integer status) {
        Map<Integer, RuntimeException> map = Map.of(
                400, new BadRequestException("Invalid request to external address service. Please check the provided data and try again."),
                404, new NotFoundException("Address not found for the provided postal code. Please verify the postal code and try again."),
                409, new ConflictException("Conflict occurred while processing the request to the external address service. Please try again later.")
        );
        return map.getOrDefault(
                status, new BadGatewayException("Client error occurred while calling the external address service. Please check your request and try again.")
        );
    }
}
