package br.com.akirodou.vanillabank.api.controller;

import br.com.akirodou.vanillabank.api.service.ClienteService;
import br.com.akirodou.vanillabank.api.service.ContaCorrenteService;
import br.com.akirodou.vanillabank.api.service.ContaEspecialService;
import br.com.akirodou.vanillabank.exception.GlobalException;
import br.com.akirodou.vanillabank.model.dto.TransferenciaDTO;
import br.com.akirodou.vanillabank.model.dto.ValorDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/conta")
public class GerenciaContaController {

    private ClienteService clienteService;
    private ContaCorrenteService contaCorrenteService;
    private ContaEspecialService contaEspecialService;

    @Autowired
    public GerenciaContaController(ClienteService clienteService, ContaCorrenteService contaCorrenteService, ContaEspecialService contaEspecialService) {
        this.clienteService = clienteService;
        this.contaCorrenteService = contaCorrenteService;
        this.contaEspecialService = contaEspecialService;
    }

    @GetMapping("/info/{id}")
    public ResponseEntity<?> getInfo(@PathVariable Long id) {
        if (contaCorrenteService.existsById(id))
            return ResponseEntity.ok(contaCorrenteService.findById(id).toString());
        else if (contaEspecialService.existsById(id))
            return ResponseEntity.ok(contaEspecialService.findById(id).toString());
        else throw new GlobalException("Conta não encontrada", HttpStatus.NOT_FOUND);
    }

    @PutMapping("/transf/{id}")
    public ResponseEntity<?> transferir(@PathVariable Long id, @RequestBody TransferenciaDTO transferenciaDTO) {
        if (contaCorrenteService.existsById(id)) {
            if (contaCorrenteService.existsById(transferenciaDTO.getIdContaDestino())) {
                ValorDTO valorDTO = new ValorDTO();
                valorDTO.setValor(transferenciaDTO.getValor());
                contaCorrenteService.sacar(id, valorDTO);
                contaCorrenteService.depositar(transferenciaDTO.getIdContaDestino(), valorDTO);
                return new ResponseEntity<Void>(HttpStatus.OK);
            } else if (contaEspecialService.existsById(transferenciaDTO.getIdContaDestino())) {
                ValorDTO valorDTO = new ValorDTO();
                valorDTO.setValor(transferenciaDTO.getValor());
                contaCorrenteService.sacar(id, valorDTO);
                contaEspecialService.depositar(transferenciaDTO.getIdContaDestino(), valorDTO);
                return new ResponseEntity<Void>(HttpStatus.OK);
            } else
                throw new GlobalException("Conta de destino encontrada", HttpStatus.BAD_REQUEST);
        } else if (contaEspecialService.existsById(id)) {
            if (contaCorrenteService.existsById(transferenciaDTO.getIdContaDestino())) {
                ValorDTO valorDTO = new ValorDTO();
                valorDTO.setValor(transferenciaDTO.getValor());
                contaEspecialService.sacar(id, valorDTO);
                contaCorrenteService.depositar(transferenciaDTO.getIdContaDestino(), valorDTO);
                return new ResponseEntity<Void>(HttpStatus.OK);
            } else if (contaEspecialService.existsById(transferenciaDTO.getIdContaDestino())) {
                ValorDTO valorDTO = new ValorDTO();
                valorDTO.setValor(transferenciaDTO.getValor());
                contaEspecialService.sacar(id, valorDTO);
                contaEspecialService.depositar(transferenciaDTO.getIdContaDestino(), valorDTO);
                return new ResponseEntity<Void>(HttpStatus.OK);
            } else
                throw new GlobalException("Conta de destino encontrada", HttpStatus.BAD_REQUEST);
        } else
            throw new GlobalException("Sua conta não foi encontrada.", HttpStatus.NOT_FOUND);
    }
}
