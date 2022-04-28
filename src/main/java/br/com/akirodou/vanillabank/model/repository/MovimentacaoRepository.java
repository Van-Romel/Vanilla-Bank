package br.com.akirodou.vanillabank.model.repository;

import br.com.akirodou.vanillabank.model.entity.MovimentacaoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovimentacaoRepository extends JpaRepository<MovimentacaoEntity, Long> {
}
