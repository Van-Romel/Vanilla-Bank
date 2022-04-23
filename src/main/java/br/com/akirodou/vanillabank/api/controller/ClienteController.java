package br.com.akirodou.vanillabank.api.controller;

import br.com.akirodou.vanillabank.api.service.ClienteService;
import br.com.akirodou.vanillabank.model.dto.ClienteDTO;
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
    public ResponseEntity<ClienteDTO> save(@Valid @RequestBody ClienteDTO clienteDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                new ClienteDTO(clienteService.save(ClienteDTO.toEntity(clienteDTO))));
    }

    @GetMapping
    public ResponseEntity<List<ClienteDTO>> findAll() {
        return ResponseEntity.status(HttpStatus.OK).body(
                ClienteDTO.toDtoList(clienteService.findAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteDTO> getClientById(@PathVariable Long id) {
        return ResponseEntity.ok().body(
                new ClienteDTO(clienteService.findById(id)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateClient(@PathVariable Long id, @RequestBody ClienteDTO clienteDTO) throws SQLIntegrityConstraintViolationException {
        clienteService.update(id, ClienteDTO.toEntity(clienteDTO));
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClient(@PathVariable Long id) {
        clienteService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
