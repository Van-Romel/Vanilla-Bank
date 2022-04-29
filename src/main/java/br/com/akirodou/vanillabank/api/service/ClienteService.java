package br.com.akirodou.vanillabank.api.service;

import br.com.akirodou.vanillabank.exception.GlobalApplicationException;
import br.com.akirodou.vanillabank.model.entity.ClienteEntity;
import br.com.akirodou.vanillabank.model.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;
    private final ContaEspecialService contaEspecialService;
    private final ContaCorrenteService contaCorrenteService;

    @Autowired
    public ClienteService(ClienteRepository clienteRepository, ContaEspecialService contaEspecialService, ContaCorrenteService contaCorrenteService) {
        this.clienteRepository = clienteRepository;
        this.contaEspecialService = contaEspecialService;
        this.contaCorrenteService = contaCorrenteService;
    }


    public List<ClienteEntity> findAll() {
        return clienteRepository.findAll();
    }

    public ClienteEntity save(ClienteEntity cliente) {
        try {
            return clienteRepository.save(cliente);
        } catch (DataIntegrityViolationException e) {
            throw new GlobalApplicationException("Este CPF já é cadastrado.", HttpStatus.BAD_REQUEST);
        }
    }


    //    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public void update(Long id, ClienteEntity clienteEntity) {
        try {
            ClienteEntity byId = clienteRepository.findById(id)
                    .orElseThrow(() ->
                            new GlobalApplicationException("Cliente não encontrado", HttpStatus.NOT_FOUND));
            byId.setNome(clienteEntity.getNome());
            byId.setCpf(clienteEntity.getCpf());
            clienteRepository.save(byId);
        } catch (DataIntegrityViolationException e) {
            throw new GlobalApplicationException("caiu na DataIntegrityViolationException do Save", HttpStatus.BAD_REQUEST);
//        } catch (TransactionalException e) {
//            throw new TransactionalException("caiu na Transactional Exception do Save", HttpStatus.BAD_REQUEST);
        }
    }

    public ClienteEntity findById(Long id) {
        return clienteRepository.findById(id)
                .orElseThrow(() ->
                        new GlobalApplicationException("Cliente não encontrado", HttpStatus.NOT_FOUND));
    }

    public void delete(Long id) {
        var cliente = clienteRepository.findById(id)
                .orElseThrow(() ->
                        new GlobalApplicationException("Cliente não encontrado", HttpStatus.NOT_FOUND));
        if (contaCorrenteService.findByCliente(cliente).isEmpty() &&
                contaEspecialService.findByCliente(cliente).isEmpty())
            clienteRepository.delete(cliente);
        else
            throw new GlobalApplicationException("Cliente possui contas vinculadas, não pode ser excluído", HttpStatus.BAD_REQUEST);
    }

    public ClienteEntity findByCpf(String cpf) {
        return clienteRepository.findByCpf(cpf).orElseThrow(() ->
                new GlobalApplicationException("Cliente não encontrado", HttpStatus.NOT_FOUND));
    }
}
