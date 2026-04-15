package br.com.gabrielferreira.cep.api.mappers;

import br.com.gabrielferreira.cep.api.dtos.AddressDTO;
import br.com.gabrielferreira.cep.domain.entities.AddressEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface AddressDTOMapper {

    AddressDTOMapper INSTANCE = Mappers.getMapper(AddressDTOMapper.class);

    AddressDTO toDTO(AddressEntity addressEntity);
}
