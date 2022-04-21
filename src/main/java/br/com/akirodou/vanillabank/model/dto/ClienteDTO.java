package br.com.akirodou.vanillabank.model.dto;

import br.com.akirodou.vanillabank.model.entity.ClienteEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClienteDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private String nome;
    private String cpf;

    public ClienteDTO(ClienteEntity entity) {
        this.nome = entity.getNome();
        this.cpf = entity.getCpf();
    }

    public ClienteDTO() {
    }

    public static List<ClienteDTO> toDtoList(List<ClienteEntity> clientes) {
        return clientes.stream().map(ClienteDTO::new).collect(Collectors.toList());
    }

    public static ClienteEntity toEntity(ClienteDTO dto) {
        ClienteEntity clienteEntity = new ClienteEntity();
        clienteEntity.setNome(dto.getNome());
        clienteEntity.setCpf(dto.getCpf());
        return clienteEntity;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
}
