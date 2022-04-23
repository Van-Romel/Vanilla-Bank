package br.com.akirodou.vanillabank.model.repository;

import br.com.akirodou.vanillabank.model.entity.ClienteEntity;
import br.com.akirodou.vanillabank.model.entity.ContaCorrenteEntity;
import br.com.akirodou.vanillabank.model.entity.ContaEspecialEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ContaEspecialRepository extends JpaRepository<ContaEspecialEntity, Long> {

    Optional<ContaEspecialEntity> findByCartaoDeCreditoContainingIgnoreCase(String cartaoDeCredito);
    Boolean existsByCartaoDeCredito(String cartaoDeCredito);
    List<ContaEspecialEntity> findAllByTitularId (Long titularId);
}
