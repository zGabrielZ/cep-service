package br.com.gabrielferreira.cep.domain.services.impl;

import br.com.gabrielferreira.cep.domain.repositories.AddressRepository;
import br.com.gabrielferreira.cep.domain.services.AddressService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;
}
