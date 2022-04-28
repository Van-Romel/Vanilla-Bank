CREATE TABLE conta_corrente
(
    id             BIGINT         NOT NULL,
    cartao_credito VARCHAR(16)    NOT NULL,
    saldo          DECIMAL(19, 2) NOT NULL,
    cntc_clt_id    BIGINT         NOT NULL,
    CONSTRAINT pk_conta_corrente primary key (id)
);
alter table conta_corrente
    add constraint uc_conta_corrente_cntc_clt_id unique (cntc_clt_id);
alter table conta_corrente
    add constraint uc_conta_corrente_cartao_credito unique (cartao_credito);