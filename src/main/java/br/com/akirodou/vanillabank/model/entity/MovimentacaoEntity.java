package br.com.akirodou.vanillabank.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "movimentacao")
public class MovimentacaoEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mov_id")
    private Long id;
    @Column(name = "mov_tipoMovimentacao")
    private String tipoMovimentacao;
    @Column(name = "mov_numeroContaOrigem")
    private Long numeroContaOrigem;
    @Column(name = "valor")
    protected BigDecimal valor;
    @Column(name = "mov_numeroContaDestino")
    private Long numeroContaDestino;
    @Column(name = "mov_data")
    private Date data;


}
