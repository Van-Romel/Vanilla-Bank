package br.com.akirodou.vanillabank.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "movimentacao")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MovimentacaoEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mov_id", nullable = false, insertable = false, updatable = false)
    private Long id;
    @Column(name = "mov_tipoMovimentacao", nullable = false, updatable = false)
    private String tipoMovimentacao;
    @Column(name = "mov_valor")
    protected BigDecimal valor;
    @Column(name = "mov_numeroContaOrigem", nullable = false, updatable = false)
    private Long numeroContaOrigem;
    @Column(name = "mov_numeroContaDestino", updatable = false)
    private Long numeroContaDestino;
    @Column(name = "mov_data", nullable = false, updatable = false)
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime dataHora;

}
