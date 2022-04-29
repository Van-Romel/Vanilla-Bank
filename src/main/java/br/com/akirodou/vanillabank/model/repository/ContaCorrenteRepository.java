package br.com.akirodou.vanillabank.model.repository;

import br.com.akirodou.vanillabank.model.entity.ClienteEntity;
import br.com.akirodou.vanillabank.model.entity.ContaCorrenteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ContaCorrenteRepository extends JpaRepository<ContaCorrenteEntity, Long> {

    Optional<ContaCorrenteEntity> findByCartaoDeCredito(String cartaoDeCredito);
    Boolean existsByCartaoDeCredito(String cartaoDeCredito);
    Optional<ContaCorrenteEntity> findByTitular (ClienteEntity titularId);
    Optional<ContaCorrenteEntity> findByTitularId (Long titularId);
}
