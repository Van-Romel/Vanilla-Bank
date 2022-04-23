package br.com.akirodou.vanillabank.model.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "cliente")
public class ClienteEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "clt_id")
    private Long id;
    @Column(name = "clt_nome")
    private String nome;
    @Column(name = "clt_cpf", unique = true)
    private String cpf;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    @Override
    public String toString() {
        return "Titular: " +
                "\nId: " + id +
                ", \nNome: '" + nome + '\'' +
                ", \nCpf: '" + cpf+ '\'';
    }
}
