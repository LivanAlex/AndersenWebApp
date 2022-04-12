drop table if exists car;
drop index if exists car_reg_num_uindex;


create table car
(
    reg_num      varchar(15) not null
        constraint car_pk
            primary key,
    manufacturer varchar(30),
    model        varchar(30),
    year         smallint
);

alter table car
    owner to postgres;

create unique index car_reg_num_uindex
    on car (reg_num);
