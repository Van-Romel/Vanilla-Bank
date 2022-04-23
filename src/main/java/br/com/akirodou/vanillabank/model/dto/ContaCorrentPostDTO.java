package br.com.akirodou.vanillabank.model.dto;


import lombok.*;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ContaCorrentPostDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private String cpf;

}
