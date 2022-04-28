package br.com.akirodou.vanillabank.api.service;

import br.com.akirodou.vanillabank.exception.GlobalApplicationException;
import br.com.akirodou.vanillabank.model.dto.ValorDTO;
import br.com.akirodou.vanillabank.model.entity.MovimentacaoEntity;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class GerenciaContaService {

    private final ContaEspecialService contaEspecialService;
    private final ContaCorrenteService contaCorrenteService;
    private final MovimentacaoService movimentacaoService;

    public GerenciaContaService(ContaEspecialService contaEspecialService, ContaCorrenteService contaCorrenteService, MovimentacaoService movimentacaoService) {
        this.contaEspecialService = contaEspecialService;
        this.contaCorrenteService = contaCorrenteService;
        this.movimentacaoService = movimentacaoService;
    }

    public String transferir(Long id, Long contaDestinoId, ValorDTO valorDTO) {
        MovimentacaoEntity movimentacaoEntity;
        var str = "Você transferiu R$ %.2f para a conta %d. O seu saldo atual é de R$ %.2f.";
        if (contaCorrenteService.existsById(id)) {

            if (contaCorrenteService.existsById(contaDestinoId)) {
                var saldoAtual = contaCorrenteService.sacar(id, valorDTO, true);
                contaCorrenteService.depositar(contaDestinoId, valorDTO, true);
                movimentacaoService.save(id, contaDestinoId, "Transferencia", valorDTO.getValor());
                return String.format(str, valorDTO.getValor(), contaDestinoId, Double.valueOf(saldoAtual));

            } else if (contaEspecialService.existsById(contaDestinoId)) {
                var saldoAtual = contaCorrenteService.sacar(id, valorDTO, true);
                contaEspecialService.depositar(contaDestinoId, valorDTO, true);
                movimentacaoService.save(id, contaDestinoId, "Transferencia", valorDTO.getValor());
                return String.format(str, valorDTO.getValor(), contaDestinoId, Double.valueOf(saldoAtual));
            } else
                throw new GlobalApplicationException("Conta de destino não foi encontrada", HttpStatus.BAD_REQUEST);


        } else if (contaEspecialService.existsById(id)) {

            if (contaEspecialService.existsById(contaDestinoId)) {
                var saldoELimiteAtual = contaEspecialService.sacar(id, valorDTO, true);
                contaEspecialService.depositar(contaDestinoId, valorDTO, true);
                movimentacaoService.save(id, contaDestinoId, "Transferencia", valorDTO.getValor());
                return String.format(str + " Seu limite atual é de R$ %.2f.", valorDTO.getValor(), contaDestinoId,
                        Double.valueOf(saldoELimiteAtual.split(" ")[0]),
                                Double.valueOf(saldoELimiteAtual.split(" ")[1]));

            } else if (contaCorrenteService.existsById(contaDestinoId)) {
                var saldoELimiteAtual = contaEspecialService.sacar(id, valorDTO, true);
                contaCorrenteService.depositar(contaDestinoId, valorDTO, true);
                movimentacaoService.save(id, contaDestinoId, "Transferencia", valorDTO.getValor());
                return String.format(str + " Seu limite atual é de R$ %.2f.", valorDTO.getValor(), contaDestinoId,
                        Double.valueOf(saldoELimiteAtual.split(" ")[0]),
                        Double.valueOf(saldoELimiteAtual.split(" ")[1]));
            } else
                throw new GlobalApplicationException("Conta de destino não foi encontrada", HttpStatus.BAD_REQUEST);
        } else
            throw new GlobalApplicationException("Sua conta não foi encontrada.", HttpStatus.NOT_FOUND);
    }
}
