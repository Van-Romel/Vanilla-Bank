package br.com.akirodou.vanillabank.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;


@Entity
@Table(name = "conta_especial")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ContaEspecialEntity extends ContaEntity {

    @Column(name = "cnte_limite", nullable = false)
    protected BigDecimal limite;
    @OneToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "cnte_clt_id", referencedColumnName = "clt_id", nullable = false)
    private ClienteEntity titular;

    @Override
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

    @Override
    public String toString() {
        return "Conta Especial: " +
                "\n" + titular +
                ", \nSaldo: " + saldo +
                ", \nCartão de Crédito: '" + cartaoDeCredito +
                ", \nLimite: " + limite;
    }
}
