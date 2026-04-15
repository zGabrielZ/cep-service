package br.com.gabrielferreira.cep.domain.mappers;

import br.com.gabrielferreira.cep.domain.dto.AddressResponse;
import br.com.gabrielferreira.cep.domain.entities.AddressEntity;
import br.com.gabrielferreira.cep.domain.entities.AddressLogEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.time.OffsetDateTime;

@Mapper(componentModel = "spring")
public interface AddressMapper {

    AddressMapper INSTANCE = Mappers.getMapper(AddressMapper.class);

    @Named("toEntity")
    AddressEntity toEntity(AddressResponse addressResponse);

    @Mapping(target = "address", source = "addressResponse", qualifiedByName = "toEntity")
    @Mapping(target = "loggedAt", source = "loggedAt")
    AddressLogEntity toLogEntity(AddressResponse addressResponse, OffsetDateTime loggedAt);
}
