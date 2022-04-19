package br.com.akirodou.vanillabank.api.controller;

import br.com.akirodou.vanillabank.api.service.ClienteService;
import br.com.akirodou.vanillabank.model.entity.ClienteEntity;
import br.com.akirodou.vanillabank.model.entity.dto.ClientePostDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("/cliente")
public class ClienteController {

    ClienteService clienteService;

    @Autowired
    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @PostMapping
    public ResponseEntity<ClientePostDTO> saveClient(@RequestBody @Valid ClientePostDTO clientePostDTO) {
        ClienteEntity clienteEntity = new ClienteEntity();
        BeanUtils.copyProperties(clientePostDTO, clienteEntity);
        ClienteEntity save = clienteService.save(clienteEntity);
        BeanUtils.copyProperties(save, clientePostDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(clientePostDTO);
    }

    @GetMapping
    public ResponseEntity<List<ClientePostDTO>> getAllClients() {
        // TODO Regra de negocio de Listar clientes
        List<ClienteEntity> clienteServiceAll = clienteService.findAll();
        var clientesDTO = Stream.of(clienteServiceAll).map(clienteEntity -> {
            ClientePostDTO clientePostDTO = new ClientePostDTO();
            BeanUtils.copyProperties(clienteEntity, clientePostDTO);
            return clientePostDTO;
        }).collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(clientesDTO);
    }
//
//    public ResponseEntity<ClientePostDTO> getClientById(String id) {
//        // TODO Regra de negocio de buscar cliente
//        return new ResponseEntity<>("Cliente listado com sucesso", HttpStatus.OK);
//    }
//
//    // TODO Atualização de clientes;
//    public ResponseEntity<Void> updateClient(String id) {
//        return new ResponseEntity<>("Cliente atualizado com sucesso", HttpStatus.OK);
//    }
//
//    // TODO Deletar clientes;
//    public ResponseEntity<Void> deleteClient(String id) {
//        return new ResponseEntity<>("Cliente deletado com sucesso", HttpStatus.OK);
//    }

}
