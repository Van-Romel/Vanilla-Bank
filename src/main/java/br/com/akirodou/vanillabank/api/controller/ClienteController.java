package br.com.akirodou.vanillabank.api.controller;

import br.com.akirodou.vanillabank.api.service.ClienteService;
import br.com.akirodou.vanillabank.model.dto.ClientePostDTO;
import br.com.akirodou.vanillabank.model.dto.ClienteRespDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.sql.SQLIntegrityConstraintViolationException;
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
        var clienteEntity = clienteService.save(ClientePostDTO.toEntity(clientePostDTO));
        System.out.println(clienteEntity);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                ClienteRespDTO.toDto(clienteEntity));
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
    public ResponseEntity<Void> updateClient(@PathVariable Long id, @RequestBody ClientePostDTO clientePostDTO) throws SQLIntegrityConstraintViolationException {
        clienteService.update(id, ClientePostDTO.toEntity(clientePostDTO));
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClient(@PathVariable Long id) {
        clienteService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
