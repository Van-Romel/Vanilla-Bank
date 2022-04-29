package br.com.akirodou.vanillabank.api.controller;

import br.com.akirodou.vanillabank.api.service.ClienteService;
import br.com.akirodou.vanillabank.api.service.ContaEspecialService;
import br.com.akirodou.vanillabank.exception.GlobalApplicationException;
import br.com.akirodou.vanillabank.model.dto.ContaEspecialPostDTO;
import br.com.akirodou.vanillabank.model.dto.ContaEspecialPutDTO;
import br.com.akirodou.vanillabank.model.dto.ValorDTO;
import br.com.akirodou.vanillabank.model.entity.ContaEspecialEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;


@RestController //A camada Controller é responsável pela comunicação com o client ( Postiman, Angular etc)
@RequestMapping("/conta/especial")
public class ContaEspecialController {

    private final ContaEspecialService contaEspecialService;
    private final ClienteService clienteService;

    @Autowired
    public ContaEspecialController(ContaEspecialService contaEspecialService, ClienteService clienteService) {
        this.contaEspecialService = contaEspecialService;
        this.clienteService = clienteService;
    }

    @PostMapping
    public ResponseEntity<ContaEspecialEntity> post(@RequestBody ContaEspecialPostDTO contaEspecialDTO) {
        contaEspecialDTO.setCpf(contaEspecialDTO.getCpf().replace(".", "").replace("-", ""));
        if (contaEspecialDTO.getLimite().compareTo(BigDecimal.ZERO) < 0)
            throw new GlobalApplicationException("Limite não pode ser negativo", HttpStatus.BAD_REQUEST);
        var entity = contaEspecialService.save(clienteService.findByCpf(contaEspecialDTO.getCpf()),
                contaEspecialDTO.getLimite());
        entity.getTitular().setCpf(entity.getTitular().getCpf().replaceAll("(\\d{3})(\\d{3})(\\d{3})(\\d{2})", "$1.$2.$3-$4"));
        return ResponseEntity.status(HttpStatus.CREATED).body(entity);
    }

    @GetMapping
    public ResponseEntity<List<ContaEspecialEntity>> findAll() {
        return ResponseEntity.status(HttpStatus.OK).body(contaEspecialService.findAll());
    }

    @GetMapping("/cliente/{cpf}")
    public ResponseEntity<ContaEspecialEntity> findByClienteCpf(@PathVariable String cpf) {
        cpf = cpf.replace(".", "").replace("-", "");
        return ResponseEntity.ok(contaEspecialService.findByClienteId(
                clienteService.findByCpf(cpf).getId()));
    }

    @GetMapping("/{id}")
    //Quando quermos passar um valor pela URI (URL), usamos a anotação @PathVariable
    public ResponseEntity<ContaEspecialEntity> findById(@PathVariable long id) {
        return ResponseEntity.ok(contaEspecialService.findById(id));
//                .map(resp -> ResponseEntity.ok(resp)).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/cartao-de-credito/{cartaoDeCredito}")
    public ResponseEntity<ContaEspecialEntity> findByCartaoDeCredito(@PathVariable String cartaoDeCredito) {
        return ResponseEntity.ok(contaEspecialService.findByCartao(cartaoDeCredito));
    }

    // o Spring considera que o retorno do método é o nome da página que ele deve carregar, mas ao utilizar a anotação @ResponseBody, indicamos que o retorno do método deve ser serializado e devolvido no corpo da resposta.
    @PutMapping("/limite/{id}")
    public ResponseEntity<ContaEspecialEntity> put(@PathVariable Long id, @RequestBody ContaEspecialPutDTO contaPut) {
        if (contaPut.getLimite().compareTo(BigDecimal.ZERO) < 0)
            throw new GlobalApplicationException("Limite não pode ser negativo", HttpStatus.BAD_REQUEST);
        if (contaEspecialService.existsById(id))
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(
                    contaEspecialService.updateLimite(id, contaPut));
        else
            throw new GlobalApplicationException("Conta não encontrada", HttpStatus.NOT_FOUND);
    }

    //o Spring considera que o retorno do método é o nome da página que ele deve carregar, mas ao utilizar a anotação @ResponseBody, indicamos que o retorno do método deve ser serializado e devolvido no corpo da resposta.
    @PutMapping("/depositar/{id}")
    public ResponseEntity<String> depositar(@PathVariable Long id, @RequestBody ValorDTO dto) {
        return ResponseEntity.status(HttpStatus.OK).body(
                contaEspecialService.depositar(id, dto));
    }

    @PutMapping("/sacar/{id}")
    public ResponseEntity<String> sacar(@PathVariable Long id, @RequestBody ValorDTO dto) {
        return ResponseEntity.status(HttpStatus.OK).body(
                contaEspecialService.sacar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        contaEspecialService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}

