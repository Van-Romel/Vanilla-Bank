package br.com.akirodou.vanillabank.model.dto;

import br.com.akirodou.vanillabank.model.entity.ClienteEntity;
import lombok.*;
import org.hibernate.validator.constraints.br.CPF;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ClienteRespDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotNull
    private Long id;

    @NotNull
    @NotBlank
    private String nome;

    @NotNull
    @CPF
    private String cpf;

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
