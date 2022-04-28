alter table conta_corrente
    add constraint FKr0t2fdcaavbqdicgwne3vogq0 foreign key (cntc_clt_id) references cliente (clt_id);
alter table conta_especial
    add constraint FKxx8kxaclimr0ruiw75grajq5 foreign key (cnte_clt_id) references cliente (clt_id);