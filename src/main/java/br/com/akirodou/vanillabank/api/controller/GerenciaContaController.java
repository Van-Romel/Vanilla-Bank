package br.com.akirodou.vanillabank.api.controller;

import br.com.akirodou.vanillabank.api.service.ContaCorrenteService;
import br.com.akirodou.vanillabank.api.service.ContaEspecialService;
import br.com.akirodou.vanillabank.api.service.GerenciaContaService;
import br.com.akirodou.vanillabank.api.service.MovimentacaoService;
import br.com.akirodou.vanillabank.exception.GlobalApplicationException;
import br.com.akirodou.vanillabank.model.dto.TransferenciaDTO;
import br.com.akirodou.vanillabank.model.dto.ValorDTO;
import br.com.akirodou.vanillabank.model.entity.MovimentacaoEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/conta")
public class GerenciaContaController {

    private final MovimentacaoService movimentacaoService;
    private final GerenciaContaService gerenciaContaService;

    @Autowired
    public GerenciaContaController(MovimentacaoService movimentacaoService, GerenciaContaService gerenciaContaService) {
        this.movimentacaoService = movimentacaoService;
        this.gerenciaContaService = gerenciaContaService;
    }

    @GetMapping("/movimentacoes")
    public ResponseEntity<List<MovimentacaoEntity>> findAll() {
        return ResponseEntity.status(HttpStatus.OK).body(movimentacaoService.findAll());
    }

    @GetMapping("/movimentacoes/{id}")
    public ResponseEntity<MovimentacaoEntity> findById(@PathVariable Long id) {
        return ResponseEntity.ok().body(movimentacaoService.findById(id));
    }

    @PutMapping("/transf/{id}")
    public ResponseEntity<?> transferir(@PathVariable Long id, @RequestBody TransferenciaDTO transferenciaDTO) {
        return ResponseEntity.ok(
                gerenciaContaService.transferir(id, transferenciaDTO.getIdContaDestino(),
                        new ValorDTO() {{
                            setValor(transferenciaDTO.getValor());
                        }}));
    }
}
