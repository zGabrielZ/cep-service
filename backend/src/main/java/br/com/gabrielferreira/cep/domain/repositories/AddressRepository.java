package br.com.gabrielferreira.cep.domain.repositories;

import br.com.gabrielferreira.cep.domain.entities.AddressEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<AddressEntity, Long> {
}
