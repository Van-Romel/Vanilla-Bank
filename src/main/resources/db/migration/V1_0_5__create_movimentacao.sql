create table movimentacao
(
    mov_id                   bigint       not null auto_increment,
    mov_data                 datetime(6) not null,
    mov_numero_conta_destino bigint,
    mov_numero_conta_origem  bigint       not null,
    mov_tipo_movimentacao    varchar(255) not null,
    mov_valor                decimal(19, 2),
    CONSTRAINT pk_movimentacao PRIMARY KEY (mov_id)
)