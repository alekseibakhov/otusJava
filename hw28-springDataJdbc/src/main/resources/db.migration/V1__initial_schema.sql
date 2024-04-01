create table if not exists client
(
    id   bigserial not null primary key,
    name varchar(50)
);

create table if not exists address
(
    id   bigserial not null primary key,
    street varchar(50),
    client_id bigint references client (id)
);

create table if not exists phone
(
    id   bigserial not null primary key,
    number varchar(50),
    client_id bigint references client (id)
);


insert into client(id, name) values(1, 'Aleksey'), (2, 'Bakhov'), (3, 'Kolbasov');

insert into address(id, street, client_id) values (1, 'Polyarnikov',1), (2, 'Alychevaya', 2), (3, 'Vyborgskaya',3);

insert into phone(id, number, client_id) values(1, '11111111', 1), (2, '222222222', 2),(3, '3333333333', 3), (4, '4444444444', 3);
