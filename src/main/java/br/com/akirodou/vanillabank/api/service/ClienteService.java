package br.com.akirodou.vanillabank.api.service;

import br.com.akirodou.vanillabank.exception.GlobalException;
import br.com.akirodou.vanillabank.model.entity.ClienteEntity;
import br.com.akirodou.vanillabank.model.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteService {

    ClienteRepository clienteRepository;

    @Autowired
    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }


    public List<ClienteEntity> findAll() {
        return clienteRepository.findAll();
    }

    public ClienteEntity save(ClienteEntity cliente) {
        try {
            return clienteRepository.save(cliente);
        } catch (DataIntegrityViolationException e) {
            throw new GlobalException("Este CPF já é cadastrado.", HttpStatus.BAD_REQUEST);
        }
    }


//    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public void update(Long id, ClienteEntity clienteEntity) {
        try {
            ClienteEntity byId = clienteRepository.findById(id)
                    .orElseThrow(() ->
                            new GlobalException("Cliente não encontrado", HttpStatus.NOT_FOUND));
            byId.setNome(clienteEntity.getNome());
            byId.setCpf(clienteEntity.getCpf());
            clienteRepository.save(byId);
        } catch (DataIntegrityViolationException e) {
            throw new GlobalException("caiu na DataIntegrityViolationException do Save", HttpStatus.BAD_REQUEST);
//        } catch (TransactionalException e) {
//            throw new TransactionalException("caiu na Transactional Exception do Save", HttpStatus.BAD_REQUEST);
        }
    }

    public ClienteEntity findById(Long id) {
        return clienteRepository.findById(id)
                .orElseThrow(() ->
                        new GlobalException("Cliente não encontrado", HttpStatus.NOT_FOUND));
    }

    public void delete(Long id) {
        try {
            clienteRepository.deleteById(id);
        } catch (Exception e) {
            throw new GlobalException("Cliente não encontrado", HttpStatus.NOT_FOUND);
        }
    }

    public ClienteEntity findByCpf(String cpf) {
        // TODO fazer trycatch
        return clienteRepository.findByCpf(cpf).orElseThrow();
    }
}
