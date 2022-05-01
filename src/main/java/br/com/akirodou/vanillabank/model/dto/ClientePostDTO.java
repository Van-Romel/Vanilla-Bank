package br.com.akirodou.vanillabank.model.dto;

import br.com.akirodou.vanillabank.model.entity.ClienteEntity;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.br.CPF;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ClientePostDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private String nome;

    @CPF(message = "CPF inválido")
    private String cpf;

    public static ClienteEntity toEntity(ClientePostDTO dto) {
        ClienteEntity clienteEntity = new ClienteEntity();
        clienteEntity.setNome(dto.getNome());
        clienteEntity.setCpf(dto.getCpf());
        return clienteEntity;
    }

}
