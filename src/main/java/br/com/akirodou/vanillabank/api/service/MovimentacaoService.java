package br.com.akirodou.vanillabank.api.service;

import br.com.akirodou.vanillabank.model.entity.MovimentacaoEntity;
import br.com.akirodou.vanillabank.model.repository.ClienteRepository;
import br.com.akirodou.vanillabank.model.repository.MovimentacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovimentacaoService {

    MovimentacaoRepository movimentacaoRepository;

    @Autowired
    public MovimentacaoService(MovimentacaoRepository movimentacaoRepository) {
        this.movimentacaoRepository = movimentacaoRepository;
    }

    public List<MovimentacaoEntity> findAll() {
        return movimentacaoRepository.findAll();
    }

    public MovimentacaoEntity findById(Long id) {
        return movimentacaoRepository.findById(id).orElseThrow();
    }

    public MovimentacaoEntity save(MovimentacaoEntity movimentacao) {
        return movimentacaoRepository.save(movimentacao);
    }

}
