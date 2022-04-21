package br.com.akirodou.vanillabank.api.service;

import br.com.akirodou.vanillabank.exception.GlobalException;
import br.com.akirodou.vanillabank.model.entity.ClienteEntity;
import br.com.akirodou.vanillabank.model.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

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
            throw new GlobalException("caiu na DataIntegrityViolationException", HttpStatus.BAD_REQUEST);
        }
    }


    public ClienteEntity findById(Long id) {
        var entity = clienteRepository.findById(id);
        if (entity.isEmpty())
            throw new GlobalException("Cliente não encontrado", HttpStatus.NOT_FOUND);
        return entity.get();
    }

    @Transactional
    public ClienteEntity update(Long id, ClienteEntity clienteEntity) {
        try {
            Optional<ClienteEntity> byId = clienteRepository.findById(id);
            if (byId.isEmpty())
                throw new GlobalException("Cliente não encontrado", HttpStatus.NOT_FOUND);
            byId.get().setNome(clienteEntity.getNome());
            byId.get().setCpf(clienteEntity.getCpf());
            return clienteRepository.save(byId.get());
        } catch (DataIntegrityViolationException e) {
            throw new HttpMessageConversionException("caiu na DataIntegrityViolationException");
//            throw new GlobalException("caiu na DataIntegrityViolationException do Save", HttpStatus.BAD_REQUEST);
        }
    }

    public void delete(Long id) {
        try {
            clienteRepository.deleteById(id);
        } catch (Exception e) {
            throw new GlobalException("Cliente não encontrado", HttpStatus.NOT_FOUND);
        }
    }
}
