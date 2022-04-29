package br.com.akirodou.vanillabank.api.service;

import br.com.akirodou.vanillabank.exception.GlobalApplicationException;
import br.com.akirodou.vanillabank.model.dto.ContaEspecialPutDTO;
import br.com.akirodou.vanillabank.model.dto.ValorDTO;
import br.com.akirodou.vanillabank.model.entity.ClienteEntity;
import br.com.akirodou.vanillabank.model.entity.ContaEspecialEntity;
import br.com.akirodou.vanillabank.model.repository.ContaEspecialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.Random;

@Service
public class ContaEspecialService {

    public final String CONTA_ESPECIAL_NUMBER_STARTS_WITH = "088011220000";

    private final ContaEspecialRepository contaEspecialRepository;
    private final MovimentacaoService movimentacaoService;

    @Autowired
    public ContaEspecialService(ContaEspecialRepository contaEspecialRepository, MovimentacaoService movimentacaoService) {
        this.contaEspecialRepository = contaEspecialRepository;
        this.movimentacaoService = movimentacaoService;
    }

    public ContaEspecialEntity save(ClienteEntity titular, BigDecimal limite) {
        Random random = new Random();
        String cartaoNumero = CONTA_ESPECIAL_NUMBER_STARTS_WITH +
                String.format("%04d", random.nextInt(9999));
        while (contaEspecialRepository.existsByCartaoDeCredito(cartaoNumero)) {
            cartaoNumero = CONTA_ESPECIAL_NUMBER_STARTS_WITH +
                    String.format("%04d", random.nextInt(9999));
        }
        var entity = new ContaEspecialEntity();
        entity.setTitular(titular);
        entity.setCartaoDeCredito(cartaoNumero);
        entity.setLimite(limite);
        entity.setSaldo(new BigDecimal(0));
        return contaEspecialRepository.save(entity);
    }

    public List<ContaEspecialEntity> findAll() {
        return contaEspecialRepository.findAll();
    }

    public ContaEspecialEntity findByClienteId(Long id) {
        return contaEspecialRepository.findByTitularId(id).orElseThrow((() ->
                new GlobalApplicationException("Conta não encontrada", HttpStatus.NOT_FOUND)));
    }

    public ContaEspecialEntity findById(Long id) {
        return contaEspecialRepository.findById(id).orElseThrow(() ->
                new GlobalApplicationException("Conta não encontrado", HttpStatus.NOT_FOUND));
    }

    public ContaEspecialEntity findByCartao(String cartaoDeCredito) {
        return contaEspecialRepository.findByCartaoDeCredito(cartaoDeCredito)
                .orElseThrow(() ->
                        new GlobalApplicationException("Conta não encontrado", HttpStatus.NOT_FOUND));
    }

    public Boolean existsById(Long id) {
        return contaEspecialRepository.existsById(id);
    }

    public Optional<ContaEspecialEntity> findByCliente(ClienteEntity clienteEntity) {
        return contaEspecialRepository.findByTitular(clienteEntity);
    }

    public ContaEspecialEntity updateLimite(Long id, ContaEspecialPutDTO especialPutDTO) {
        var conta = contaEspecialRepository.findById(id).orElseThrow((() ->
                new GlobalApplicationException("Conta não encontrada", HttpStatus.NOT_FOUND)));
        conta.setLimite(especialPutDTO.getLimite());
        return contaEspecialRepository.save(conta);

    }

    public String depositar(Long id, ValorDTO dto) {
        return depositar(id, dto, false);
    }

    public String depositar(Long id, ValorDTO dto, boolean isTransf) {
        if (dto.getValor().compareTo(new BigDecimal(0)) <= 0)
            throw new GlobalApplicationException("O valor de depósito deve ser maior que zero", HttpStatus.BAD_REQUEST);
        // TODO fazer Exception handling
        var conta = findById(id);
        conta.depositar(dto.getValor());
        contaEspecialRepository.save(conta);
//        ContaEspecialEntity conta = contaEspecialRepository.findById(id).orElseThrow();
        if (isTransf)
            return String.format(Locale.US, "%.2f %.2f", conta.getSaldo(), conta.getLimite());
        movimentacaoService.save(id, null, "Depósito", dto.getValor());
        return "Você depositou R$ " + dto.getValor() + " na conta com o id: " + conta.getId();
    }

    public String sacar(Long id, ValorDTO dto) {
        return sacar(id, dto, false);
    }

    public String sacar(Long id, ValorDTO dto, boolean isTransf) {
        if (dto.getValor().compareTo(new BigDecimal(0)) <= 0)
            throw new GlobalApplicationException("O valor de saque deve ser maior que zero", HttpStatus.BAD_REQUEST);
        // TODO fazer Exception handling
        var conta = findById(id);

        if (conta.getSaldo().add(conta.getLimite()).compareTo(dto.getValor()) < 0)
            throw new GlobalApplicationException("Limite insuficiente", HttpStatus.BAD_REQUEST);
        conta.sacar(dto.getValor());
        contaEspecialRepository.save(conta);
        if (isTransf)
            return String.format(Locale.US, "%.2f %.2f", conta.getSaldo(), conta.getLimite());
        movimentacaoService.save(id, null, "Saque", dto.getValor());

        return "Você sacou R$ " + dto.getValor() + " na conta com o id: " + conta.getId()
                + " e seu saldo atual é de R$ " + conta.getSaldo();
    }


    public void delete(Long id) {
        try {
            contaEspecialRepository.deleteById(id);
        } catch (Exception e) {
            throw new GlobalApplicationException("Conta não encontrada", HttpStatus.NOT_FOUND);
        }
    }
}
