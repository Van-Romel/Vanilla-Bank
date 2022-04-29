package br.com.akirodou.vanillabank.model.entity;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "conta_especial")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ContaEspecialEntity extends ContaEntity {
//	private static final long serialVersionUID = 1L;

//	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	@Column(name = "cnte_id")
//	protected Long id;
//
//	@Column(name = "cnte_saldo", nullable = false)
//	protected BigDecimal saldo;

	@Column(name = "cnte_limite", nullable = false)
	protected BigDecimal limite;
//
//	@Column(name = "cnte_cartao_credito", nullable = false, length = 16)
//	protected String cartaoDeCredito;

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

//	public void deposita (double valor) {
//		this.saldo += valor;
//	}
//
//	public boolean saca (double valor) {
//		if (this.saldo >= valor) {
//			this.saldo -= valor;
//			return true;
//		}
//		return false;
//
//	}
//
//	public void informacaoDaConta() {
//		System.out.printf("Número da conta: ".concat("\n Saldo na conta é no valor de: ").concat("\nSeu cartão de crédito é: ")
//				,this.id, this.saldo, this.cartaoDeCredito);
//	}
//
//	public long getId() {
//		return id;
//	}
//
//	public double getSaldo() {
//		return saldo;
//	}
//
//	public String getCartaoDeCredito() {
//		return cartaoDeCredito;
//	}

	@Override
	public String toString() {
		return "Conta Especial: " + "\nTitular: " + titular + ", \nSaldo: " + saldo + ", \nCartão de Crédito: '"
				+ cartaoDeCredito + ", \nLimite: " + limite;
	}
}
