package br.com.akirodou.vanillabank.api.service;

import br.com.akirodou.vanillabank.exception.GlobalException;
import br.com.akirodou.vanillabank.model.dto.ContaCorrentPostDTO;
import br.com.akirodou.vanillabank.model.dto.ValorDTO;
import br.com.akirodou.vanillabank.model.entity.ClienteEntity;
import br.com.akirodou.vanillabank.model.entity.ContaCorrenteEntity;
import br.com.akirodou.vanillabank.model.repository.ContaCorrenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Random;

@Service
public class ContaCorrenteService {

    public final String CONTA_CORRENTE_NUMBER_STARTS_WITH = "088011224444";

    ContaCorrenteRepository contaCorrenteRepository;
    ClienteService clienteService;

    @Autowired
    public ContaCorrenteService(ContaCorrenteRepository contaCorrenteRepository, ClienteService clienteService) {
        this.contaCorrenteRepository = contaCorrenteRepository;
        this.clienteService = clienteService;
    }

    public ContaCorrenteEntity save(ContaCorrentPostDTO dto) {
        Random random = new Random();
        ClienteEntity titular = clienteService.findByCpf(dto.getCpf());
        String cartaoNumero = CONTA_CORRENTE_NUMBER_STARTS_WITH +
                String.format("%04d", random.nextInt(9999));
        while (contaCorrenteRepository.existsByCartaoDeCredito(cartaoNumero)) {
            cartaoNumero = CONTA_CORRENTE_NUMBER_STARTS_WITH +
                    String.format("%04d", random.nextInt(9999));
        }
        var entity = new ContaCorrenteEntity();
        entity.setTitular(titular);
        // TODO ver como está gerando nas tabelas
        entity.setCartaoDeCredito(cartaoNumero);
        entity.setSaldo(new BigDecimal(0));
        return contaCorrenteRepository.save(entity);
    }

    public ContaCorrenteEntity findByClienteCpf(String cpf) {
        return contaCorrenteRepository.findByTitularId(
                clienteService.findByCpf(cpf).getId()).orElseThrow();
    }

    public ContaCorrenteEntity findById(Long id) {
        return contaCorrenteRepository.findById(id).orElseThrow();
    }

    public ContaCorrenteEntity findByCartao(String cartaoDeCredito) {
        return contaCorrenteRepository.findByCartaoDeCredito(cartaoDeCredito)
                .orElseThrow();
    }

    public String depositar(Long id, ValorDTO dto) {
        if (dto.getValor().compareTo(new BigDecimal(0)) <= 0)
            throw new GlobalException("O valor de depósito deve ser maior que zero", HttpStatus.BAD_REQUEST);
        // TODO fazer Exception handling
        var conta = findById(id);
        conta.depositar(dto.getValor());
        contaCorrenteRepository.save(conta);
//        ContaCorrenteEntity conta = contaCorrenteRepository.findById(id).orElseThrow();

        return "Você depositou R$ " + dto.getValor() + " na conta com o id: " + conta.getId();
    }

    public String sacar(Long id, ValorDTO dto) {
        if (dto.getValor().compareTo(new BigDecimal(0)) <= 0)
            throw new GlobalException("O valor de saque deve ser maior que zero", HttpStatus.BAD_REQUEST);
        // TODO fazer Exception handling
        var conta = findById(id);
        if (conta.getSaldo().compareTo(dto.getValor()) < 0)
            throw new GlobalException("Saldo insuficiente", HttpStatus.BAD_REQUEST);
        conta.sacar(dto.getValor());
        contaCorrenteRepository.save(conta);
//        ContaCorrenteEntity conta = contaCorrenteRepository.findById(id).orElseThrow();

        return "Você sacou R$ " + dto.getValor() + " na conta com o id: " + conta.getId()
                + " e seu saldo atual é de R$ " + conta.getSaldo();
    }

    public void delete(Long id) {
        // TODO fazer Try Catch
        contaCorrenteRepository.deleteById(id);
    }
}
