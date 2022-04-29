ALTER TABLE conta_corrente
    ADD FOREIGN KEY (cntc_clt_id) REFERENCES cliente (clt_id);
ALTER TABLE conta_especial
    ADD FOREIGN KEY (cnte_clt_id) REFERENCES cliente (clt_id);