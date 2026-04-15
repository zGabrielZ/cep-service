package br.com.gabrielferreira.cep.domain.exceptions;

import java.io.Serial;

public class ConflictException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = -358255889321645006L;

    public ConflictException(String message) {
        super(message);
    }
}
