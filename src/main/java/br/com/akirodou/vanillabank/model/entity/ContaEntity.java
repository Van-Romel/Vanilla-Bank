package br.com.akirodou.vanillabank.model.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Getter
@Setter
public abstract class ContaEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "saldo", nullable = false)
    protected BigDecimal saldo;

    @Column(name = "cartao_credito", nullable = false, unique = true, length = 16)
    protected String cartaoDeCredito;

    public void depositar(BigDecimal valor){
        this.setSaldo(this.getSaldo().add(valor));
    }

    public abstract void sacar(BigDecimal valor);
}
