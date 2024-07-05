create table persona
(
    identificacion varchar(10)  not null
        primary key,
    nombre         varchar(100) not null,
    genero         varchar(1)   not null,
    edad           integer      not null,
    direccion      varchar(500) not null,
    telefono       integer      not null
);

alter table persona
    owner to postgres;

create table cliente
(
    id         integer           not null
        primary key,
    contraseña varchar(100)      not null,
    estado     integer default 1 not null,
    personaid  varchar(10)
        constraint cliente_persona_identificacion_fk
            references persona
);

alter table cliente
    owner to postgres;

create table productos
(
    id     integer      not null
        primary key,
    nombre varchar(100) not null,
    active integer default 1
);

alter table productos
    owner to postgres;

create table movimientos
(
    id     integer not null
        primary key,
    cuenta integer not null,
    valor  double precision,
    fecha  timestamp(0) default CURRENT_TIMESTAMP,
    saldo  double precision
);

alter table movimientos
    owner to postgres;

create table cuenta
(
    numero  integer                    not null
        primary key,
    tipo    integer          default 1
        constraint cuenta_productos_id_fk
            references productos,
    saldo   double precision default 0 not null,
    estado  integer          default 1 not null,
    cliente integer                    not null
        constraint cuenta_cliente_id_fk
            references cliente
);

alter table cuenta
    owner to postgres;

