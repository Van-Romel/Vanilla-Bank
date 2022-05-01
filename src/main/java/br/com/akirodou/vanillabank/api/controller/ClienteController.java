package br.com.akirodou.vanillabank.api.controller;

import br.com.akirodou.vanillabank.api.service.ClienteService;
import br.com.akirodou.vanillabank.exception.GlobalApplicationException;
import br.com.akirodou.vanillabank.model.dto.ClientePostDTO;
import br.com.akirodou.vanillabank.model.dto.ClientePutDTO;
import br.com.akirodou.vanillabank.model.dto.ClienteRespDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/cliente")
public class ClienteController {

    ClienteService clienteService;

    @Autowired
    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @PostMapping
    public ResponseEntity<ClienteRespDTO> save(@Valid @RequestBody ClientePostDTO clientePostDTO) {
        if (clientePostDTO.getCpf() == null || clientePostDTO.getNome() == null )
            throw new GlobalApplicationException("São nescessarios nome e cpf para o cadastro de um cliente", HttpStatus.BAD_REQUEST);
        if (clientePostDTO.getNome().isEmpty() || clientePostDTO.getNome().length() < 3 || clientePostDTO.getNome().length() > 255)
            throw new GlobalApplicationException("O nome deve conter no minimo 3 caracteres e no maximo 255", HttpStatus.BAD_REQUEST);
        clientePostDTO.setCpf(clientePostDTO.getCpf().replace(".", "").replace("-", ""));
        return ResponseEntity.status(HttpStatus.CREATED).body(
                ClienteRespDTO.toDto(clienteService.save(ClientePostDTO.toEntity(clientePostDTO))));
    }

    @GetMapping
    public ResponseEntity<List<ClienteRespDTO>> findAll() {
        return ResponseEntity.status(HttpStatus.OK).body(
                ClienteRespDTO.toDtoList(clienteService.findAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteRespDTO> getClientById(@PathVariable Long id) {
        return ResponseEntity.ok().body(
                ClienteRespDTO.toDto(clienteService.findById(id)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateClient(@PathVariable Long id, @Valid @RequestBody ClientePutDTO clientePutDTO) {
        if (clientePutDTO.getCpf() != null)
            clientePutDTO.setCpf(clientePutDTO.getCpf().replace(".", "").replace("-", ""));
        clienteService.update(id, ClientePutDTO.toEntity(clientePutDTO));
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClient(@PathVariable Long id) {
        clienteService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
