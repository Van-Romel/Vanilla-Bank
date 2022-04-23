package br.com.akirodou.vanillabank.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "conta_corrente")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ContaCorrenteEntity extends ContaEntity {
    private static final long serialVersionUID = 1L;

//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "cntc_id")
//    private Long id;
//
//    @Column(name = "cntc_saldo", nullable = false)
//    protected BigDecimal saldo;
//
//    @Column(name = "cntc_cartao_credito", nullable = false, unique = true, length = 16)
//    protected String cartaoDeCredito;

    @OneToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "cntc_clt_id", referencedColumnName = "clt_id", nullable = false)
    private ClienteEntity titular;

    @Override
    public void sacar(BigDecimal valor) {
        this.saldo = this.saldo.subtract(valor);
    }

//	@Override
//	public boolean saca(double valor) {
//		if (this.saldo >= valor) {
//			this.saldo -= valor;
//			return true;
//		}
//		return false;
//
//	}


    @Override
    public String toString() {
        return "Conta Corrente: " +
                "\nTitular: " + titular +
                ", \nSaldo: " + saldo +
                ", \nCartão de Crédito: '" + cartaoDeCredito;
    }
}
