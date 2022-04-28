package br.com.akirodou.vanillabank.model.dto;


import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ContaEspecialPutDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private BigDecimal limite;

}
