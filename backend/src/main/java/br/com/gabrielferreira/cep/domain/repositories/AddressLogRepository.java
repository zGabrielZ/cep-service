package br.com.gabrielferreira.cep.domain.repositories;

import br.com.gabrielferreira.cep.domain.entities.AddressLogEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressLogRepository extends JpaRepository<AddressLogEntity, Long> {
}
