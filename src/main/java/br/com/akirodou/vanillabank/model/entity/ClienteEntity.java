package br.com.akirodou.vanillabank.model.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "cliente")
@Getter @Setter
public class ClienteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "clt_id")
    private UUID id;
    @Column(name = "clt_nome", unique = true)
    private String nome;
    @Column(name = "clt_cpf", unique = true)
    private String cpf;
}
