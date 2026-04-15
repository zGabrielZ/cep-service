package br.com.gabrielferreira.cep.api.exception.mappers;

import br.com.gabrielferreira.cep.api.exception.dtos.ProblemDetailDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.time.OffsetDateTime;

@Mapper(componentModel = "spring")
public interface ProblemDetailMapper {

    ProblemDetailMapper INSTANCE = Mappers.getMapper(ProblemDetailMapper.class);

    ProblemDetailDTO toProblemDetailDto(
            Integer status,
            String title,
            String detail,
            String message,
            OffsetDateTime timestamp
    );
}
