package br.com.akirodou.vanillabank.model.dto;

import br.com.akirodou.vanillabank.model.entity.ClienteEntity;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@Getter
@Setter
public class ClienteRespDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;

    private String nome;

    private String cpf;

    public ClienteRespDTO(Long id, String nome, String cpf) {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf.replaceAll("(\\d{3})(\\d{3})(\\d{3})(\\d{2})", "$1.$2.$3-$4");
    }

    public static ClienteRespDTO toDto(ClienteEntity cliente) {
        return new ClienteRespDTO(
                cliente.getId(),
                cliente.getNome(),
                cliente.getCpf()
        );
    }

    public static List<ClienteRespDTO> toDtoList(List<ClienteEntity> clientes) {
        return clientes.stream().map(ClienteRespDTO::toDto).collect(Collectors.toList());
    }

}
