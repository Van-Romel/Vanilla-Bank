package br.com.akirodou.vanillabank.api.service;

import br.com.akirodou.vanillabank.exception.GlobalException;
import br.com.akirodou.vanillabank.model.dto.ContaEspecialPostDTO;
import br.com.akirodou.vanillabank.model.dto.ValorDTO;
import br.com.akirodou.vanillabank.model.entity.ClienteEntity;
import br.com.akirodou.vanillabank.model.entity.ContaEspecialEntity;
import br.com.akirodou.vanillabank.model.repository.ContaEspecialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Random;

@Service
public class ContaEspecialService {

    public final String CONTA_ESPECIAL_NUMBER_STARTS_WITH = "088011220000";

    ContaEspecialRepository contaEspecialRepository;
    ClienteService clienteService;

    @Autowired
    public ContaEspecialService(ContaEspecialRepository contaEspecialRepository, ClienteService clienteService) {
        this.contaEspecialRepository = contaEspecialRepository;
        this.clienteService = clienteService;
    }

    public ContaEspecialEntity save(ContaEspecialPostDTO dto) {
        Random random = new Random();
        ClienteEntity titular = clienteService.findByCpf(dto.getCpf());
        String cartaoNumero = CONTA_ESPECIAL_NUMBER_STARTS_WITH +
                String.format("%04d", random.nextInt(9999));
        while (contaEspecialRepository.existsByCartaoDeCredito(cartaoNumero)) {
            cartaoNumero = CONTA_ESPECIAL_NUMBER_STARTS_WITH +
                    String.format("%04d", random.nextInt(9999));
        }
        var entity = new ContaEspecialEntity();
        entity.setTitular(titular);
        // TODO ver como está gerando nas tabelas
        entity.setCartaoDeCredito(cartaoNumero);
        entity.setLimite(dto.getLimite());
        entity.setSaldo(new BigDecimal(0));
        return contaEspecialRepository.save(entity);
    }

    public List<ContaEspecialEntity> findAllByClienteCpf(String cpf) {
        return contaEspecialRepository.findAllByTitularId(
                clienteService.findByCpf(cpf).getId());
    }

    public ContaEspecialEntity findById(Long id) {
        return contaEspecialRepository.findById(id).orElseThrow();
    }

    public ContaEspecialEntity findByCartao(String cartaoDeCredito) {
        return contaEspecialRepository.findByCartaoDeCreditoContainingIgnoreCase(cartaoDeCredito)
                .orElseThrow();
    }

    public Boolean existsById(Long id) {
        return contaEspecialRepository.existsById(id);
    }

    public ContaEspecialEntity update(ContaEspecialPostDTO especialPostDTO) {

        //TODO fazer a regra aqui
        return null;

    }

    public String depositar(Long id, ValorDTO dto) {
        return depositar(id, dto, false);
    }

    public String depositar(Long id, ValorDTO dto, boolean transf) {
        if (dto.getValor().compareTo(new BigDecimal(0)) <= 0)
            throw new GlobalException("O valor de depósito deve ser maior que zero", HttpStatus.BAD_REQUEST);
        // TODO fazer Exception handling
        var conta = findById(id);
        conta.depositar(dto.getValor());
        contaEspecialRepository.save(conta);
//        ContaEspecialEntity conta = contaEspecialRepository.findById(id).orElseThrow();
        if (transf) return null;
        // TODO add como Saque no historico
        return "Você depositou R$ " + dto.getValor() + " na conta com o id: " + conta.getId();
    }

    public String sacar(Long id, ValorDTO dto) {
        return sacar(id, dto, false);
    }
    public String sacar(Long id, ValorDTO dto, boolean transf) {
        if (dto.getValor().compareTo(new BigDecimal(0)) <= 0)
            throw new GlobalException("O valor de saque deve ser maior que zero", HttpStatus.BAD_REQUEST);
        // TODO fazer Exception handling
        var conta = findById(id);

        if (conta.getSaldo().add(conta.getLimite()).compareTo(dto.getValor()) < 0)
            throw new GlobalException("Limite insuficiente", HttpStatus.BAD_REQUEST);
        conta.sacar(dto.getValor());
        contaEspecialRepository.save(conta);
        if (transf) return null;
        // TODO add como Saque no historico

        return "Você sacou R$ " + dto.getValor() + " na conta com o id: " + conta.getId()
                + " e seu saldo atual é de R$ " + conta.getSaldo();
    }


    public void delete(Long id) {
        // TODO fazer Try Catch
        contaEspecialRepository.deleteById(id);
    }
}
