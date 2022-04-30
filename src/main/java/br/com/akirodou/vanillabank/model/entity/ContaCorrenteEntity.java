package br.com.akirodou.vanillabank.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "conta_corrente", schema = "vanilla_bank")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ContaCorrenteEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "conta_hibernate_sequence", sequenceName = "\"vanilla_bank\".conta_hibernate_sequence", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "conta_hibernate_sequence")
    @Column(name = "cntc_id")
    private Long id;

    @Column(name = "cntc_saldo", nullable = false)
    protected BigDecimal saldo;

    @Column(name = "cntc_cartao_credito", nullable = false, unique = true, length = 16)
    protected String cartaoDeCredito;

    @OneToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "cntc_clt_id", referencedColumnName = "clt_id", nullable = false)
    private ClienteEntity titular;

    public void sacar(BigDecimal valor) {
        this.saldo = this.saldo.subtract(valor);
    }

    public void depositar(BigDecimal valor){
        this.saldo = this.saldo.add(valor);
    }

    @Override
    public String toString() {
        return "Conta Corrente: " +
                "\n" + titular +
                ", \nSaldo: " + saldo +
                ", \nCartão de Crédito: '" + cartaoDeCredito;
    }
}
