CREATE TABLE conta_especial
(
    id             BIGINT         NOT NULL,
    cartao_credito VARCHAR(16)    NOT NULL,
    saldo          DECIMAL(19, 2) NOT NULL,
    cnte_limite    DECIMAL(19, 2) NOT NULL,
    cnte_clt_id    BIGINT         NOT NULL,
    CONSTRAINT pk_conta_especial PRIMARY KEY (id)
) ;
alter table conta_especial
    add constraint uc_conta_especial_cnte_clt_id unique (cnte_clt_id);
alter table conta_especial
    add constraint uc_conta_especial_cartao_credito unique (cartao_credito);