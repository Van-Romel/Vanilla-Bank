package br.com.akirodou.vanillabank.model.entity.dto;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CPF;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class ClientePostDTO {

    @NotBlank
    protected String nome;
    @NotBlank
    @CPF
    protected String cpf;

}
