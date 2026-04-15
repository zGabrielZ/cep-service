package br.com.gabrielferreira.cep.domain.exceptions;

import java.io.Serial;

public class BadGatewayException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = -7755256941877828646L;

    public BadGatewayException(String message) {
        super(message);
    }
}
