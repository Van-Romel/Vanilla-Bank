create table hibernate_sequence
(
    next_val bigint
);
ALTER TABLE hibernate_sequence
    ENGINE = InnoDB;
insert into hibernate_sequence
values (1);
