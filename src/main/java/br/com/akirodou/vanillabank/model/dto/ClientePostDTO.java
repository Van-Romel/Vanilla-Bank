package br.com.akirodou.vanillabank.model.dto;

import br.com.akirodou.vanillabank.model.entity.ClienteEntity;
import lombok.*;
import org.hibernate.validator.constraints.br.CPF;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ClientePostDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotNull
    @NotBlank
    private String nome;

    @NotNull
    @CPF(message = "CPF inválido")
    private String cpf;

    public static ClienteEntity toEntity(ClientePostDTO dto) {
        ClienteEntity clienteEntity = new ClienteEntity();
        clienteEntity.setNome(dto.getNome());
        clienteEntity.setCpf(dto.getCpf());
        return clienteEntity;
    }

}
