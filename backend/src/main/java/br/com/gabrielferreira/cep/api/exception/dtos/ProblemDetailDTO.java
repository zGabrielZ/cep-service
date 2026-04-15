package br.com.gabrielferreira.cep.api.exception.dtos;

import lombok.Builder;

import java.io.Serializable;
import java.time.OffsetDateTime;

@Builder
public record ProblemDetailDTO(
        Integer status,
        String title,
        String detail,
        String message,
        OffsetDateTime timestamp
) implements Serializable {
}
