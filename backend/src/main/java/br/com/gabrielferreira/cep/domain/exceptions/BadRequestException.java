package br.com.gabrielferreira.cep.domain.exceptions;

import java.io.Serial;

public class BadRequestException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 176749358186175149L;

    public BadRequestException(String message) {
        super(message);
    }
}
