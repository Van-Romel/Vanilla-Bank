package br.com.akirodou.vanillabank.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "conta_corrente")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ContaCorrenteEntity extends ContaEntity {
	private static final long serialVersionUID = 1L;

	@OneToOne(optional = false, fetch = FetchType.EAGER)
	@JoinColumn(name = "cntc_clt_id", referencedColumnName = "clt_id", nullable = false)
	private ClienteEntity titular;

	@Override
	public void sacar(BigDecimal valor) {
		this.saldo = this.saldo.subtract(valor);
	}

	@Override
	public String toString() {
		return "Conta Corrente: " + "\nTitular: " + titular + ", \nSaldo: " + saldo + ", \nCartão de Crédito: '"
				+ cartaoDeCredito;
	}
}
