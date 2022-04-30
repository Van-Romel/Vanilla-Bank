package br.com.akirodou.vanillabank.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;


@Entity
@Table(name = "conta_especial", schema = "vanilla_bank")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ContaEspecialEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "conta_hibernate_sequence", sequenceName = "\"vanilla_bank\".conta_hibernate_sequence", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "conta_hibernate_sequence")
    @Column(name = "cnte_id")
    private Long id;

    @Column(name = "cnte_saldo", nullable = false)
    protected BigDecimal saldo;

    @Column(name = "cnte_cartao_credito", nullable = false, unique = true, length = 16)
    protected String cartaoDeCredito;


    @Column(name = "cnte_limite", nullable = false)
    protected BigDecimal limite;

    @OneToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "cnte_clt_id", referencedColumnName = "clt_id", nullable = false)
    private ClienteEntity titular;

    public void sacar(BigDecimal valor) {
        if (valor.compareTo(this.saldo) > 0) {
            this.limite = this.limite.subtract(valor.subtract(this.saldo));
            this.saldo = BigDecimal.ZERO;
            System.out.println("Teve de usar limite");
        } else {
            this.saldo = this.saldo.subtract(valor);
            System.out.println("Saque não utilizou limite");
        }
    }

    public void depositar(BigDecimal valor) {
        this.saldo = this.saldo.add(valor);
    }

    @Override
    public String toString() {
        return "Conta Especial: " +
                "\n" + titular +
                ", \nSaldo: " + saldo +
                ", \nCartão de Crédito: '" + cartaoDeCredito +
                ", \nLimite: " + limite;
    }
}
