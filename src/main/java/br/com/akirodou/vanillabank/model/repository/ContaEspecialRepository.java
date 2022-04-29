package br.com.akirodou.vanillabank.model.repository;

import br.com.akirodou.vanillabank.model.entity.ClienteEntity;
import br.com.akirodou.vanillabank.model.entity.ContaEspecialEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ContaEspecialRepository extends JpaRepository<ContaEspecialEntity, Long> {

    Optional<ContaEspecialEntity> findByCartaoDeCredito(String cartaoDeCredito);
    Boolean existsByCartaoDeCredito(String cartaoDeCredito);
    Optional<ContaEspecialEntity> findByTitular (ClienteEntity titular);
    Optional<ContaEspecialEntity> findByTitularId (Long titularId);
}
