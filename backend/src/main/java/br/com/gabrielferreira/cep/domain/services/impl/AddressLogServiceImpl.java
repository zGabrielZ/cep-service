package br.com.gabrielferreira.cep.domain.services.impl;

import br.com.gabrielferreira.cep.domain.dto.AddressResponse;
import br.com.gabrielferreira.cep.domain.entities.AddressLogEntity;
import br.com.gabrielferreira.cep.domain.enums.Status;
import br.com.gabrielferreira.cep.domain.mappers.AddressMapper;
import br.com.gabrielferreira.cep.domain.repositories.AddressLogRepository;
import br.com.gabrielferreira.cep.domain.services.AddressLogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class AddressLogServiceImpl implements AddressLogService {

    private final AddressLogRepository addressLogRepository;

    private final AddressMapper mapper;

    @Transactional
    @Override
    public AddressLogEntity save(AddressResponse address, Status status, String errorMessage) {
        AddressLogEntity addressLogEntity = mapper.toLogEntity(
                address, OffsetDateTime.now(),
                status, errorMessage
        );
        log.info("Saving address log: {}", addressLogEntity);
        return addressLogRepository.save(addressLogEntity);
    }
}
