CREATE TABLE IF NOT EXISTS "vanilla_bank".conta_especial
(
    cnte_id             bigint                NOT NULL GENERATED BY DEFAULT AS IDENTITY (INCREMENT BY 1 START WITH 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1),
    cnte_cartao_credito character varying(16) NOT NULL,
    cnte_saldo          numeric(19, 2)        NOT NULL,
    cnte_limite         numeric(19, 2)        NOT NULL,
    cnte_clt_id         bigint                NOT NULL,
    CONSTRAINT conta_especial_pkey PRIMARY KEY (cnte_id),
    CONSTRAINT uc_conta_especial_cnte_clt_id UNIQUE (cnte_clt_id),
    CONSTRAINT uc_conta_especial_cartao_credito UNIQUE (cnte_cartao_credito),
    CONSTRAINT fk_conta_especial_cliente_id FOREIGN KEY (cnte_clt_id)
        REFERENCES "vanilla_bank".cliente (clt_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);