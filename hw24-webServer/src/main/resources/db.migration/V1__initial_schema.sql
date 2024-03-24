create sequence sequenceRes start with 1 increment by 1;

create table if not exists address
(
    id   bigserial,
    street varchar(50),
    primary key (id)
);

create table if not exists  client
(
    id   bigint not null primary key,
    name varchar(50),
    address_id bigint references address (id) on delete cascade
);

create table if not exists phone
(
    id   bigserial not null primary key,
    number varchar(50),
    client_id bigint references client (id)
);

insert into address(street)
values ('address1'), ('address2'), ('address3');

insert into client(id, name, address_id)
values(nextval('sequenceRes'), 'client1', 1), (nextval('sequenceRes'), 'client2', 2),(nextval('sequenceRes'), 'client3', 3);

insert into phone(number, client_id)
values('111111', 1), ('111222', 2), ('222222', 2), ('333333', 3);