drop table if exists car_of_the_day;
drop index if exists car_of_the_day_id_uindex;

create table car_of_the_day
(
    reg_num      varchar(15),
    manufacturer varchar(30),
    model        varchar(30),
    year         smallint,
    id           serial
);

alter table car_of_the_day
    owner to postgres;

create unique index car_of_the_day_id_uindex
    on car_of_the_day (id);

