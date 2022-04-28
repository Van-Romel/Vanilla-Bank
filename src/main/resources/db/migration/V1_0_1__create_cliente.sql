CREATE TABLE cliente
(
    clt_id   BIGINT AUTO_INCREMENT NOT NULL,
    clt_nome VARCHAR(255)          NULL,
    clt_cpf  VARCHAR(255)          NULL,
    CONSTRAINT pk_cliente PRIMARY KEY (clt_id)
);

ALTER TABLE cliente
    ADD CONSTRAINT uc_cliente_clt_cpf UNIQUE (clt_cpf);