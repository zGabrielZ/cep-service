package br.com.gabrielferreira.cep.domain.exceptions;

import java.io.Serial;

public class NotFoundException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 5635091913769083608L;

    public NotFoundException(String message) {
        super(message);
    }
}
