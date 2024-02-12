create table test
(
    id   int,
    name varchar(50)
);
create table Client
(
    id   bigserial not null primary key,
    name varchar(50)
);

create table manager
(
    id   bigserial not null primary key,
    name varchar(50)
);