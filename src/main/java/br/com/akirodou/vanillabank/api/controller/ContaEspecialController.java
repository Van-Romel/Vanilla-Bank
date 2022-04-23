package br.com.akirodou.vanillabank.api.controller;

import br.com.akirodou.vanillabank.api.service.ContaEspecialService;
import br.com.akirodou.vanillabank.model.dto.ContaEspecialPostDTO;
import br.com.akirodou.vanillabank.model.dto.ValorDTO;
import br.com.akirodou.vanillabank.model.entity.ContaEspecialEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController //A camada Controller é responsável pela comunicação com o client ( Postiman, Angular etc)
@RequestMapping("/conta/especial")
public class ContaEspecialController {

    private ContaEspecialService contaEspecialService;

    @Autowired
    public ContaEspecialController(ContaEspecialService contaEspecialService) {
        this.contaEspecialService = contaEspecialService;
    }

    //Sempre quando formos realizar um Post/Put enviaremos os dados via Body (Postman)
    @PostMapping
    public ResponseEntity<ContaEspecialEntity> post(@RequestBody ContaEspecialPostDTO contaEspecialDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                contaEspecialService.save(contaEspecialDTO));
    }

    @GetMapping("/cliente/{cpf}")
    public ResponseEntity<List<ContaEspecialEntity>> findAllByClienteCpf(@PathVariable String cpf) {
        return ResponseEntity.ok(contaEspecialService.findAllByClienteCpf(cpf));
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

    //o Spring considera que o retorno do método é o nome da página que ele deve carregar, mas ao utilizar a anotação @ResponseBody, indicamos que o retorno do método deve ser serializado e devolvido no corpo da resposta.
//    @PutMapping
//    // TODO alterar limite
//    public ResponseEntity<ContaEspecialEntity> put(@RequestBody ContaEspecialPostDTO contaPut) {
//        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(contaEspecialService.update(contaPut));
//    }

    //o Spring considera que o retorno do método é o nome da página que ele deve carregar, mas ao utilizar a anotação @ResponseBody, indicamos que o retorno do método deve ser serializado e devolvido no corpo da resposta.
    @PutMapping("/depositar/{id}")
    public ResponseEntity<?> depositar(@PathVariable Long id, @RequestBody ValorDTO dto) {
        return ResponseEntity.status(HttpStatus.OK).body(
                contaEspecialService.depositar(id, dto));
    }

    @PutMapping("/sacar/{id}")
    public ResponseEntity<?> sacar(@PathVariable Long id, @RequestBody ValorDTO dto) {
        return ResponseEntity.status(HttpStatus.OK).body(
                contaEspecialService.sacar(id, dto));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable long id) {
        contaEspecialService.delete(id);
    }

}
