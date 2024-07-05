-- SQLINES LICENSE FOR EVALUATION USE ONLY
create table if not exists Persona
(
    Identificacion varchar(10)  not null
    primary key,
    Nombre         varchar(100) not null,
    Genero         varchar(1)   not null,
    Edad           int          not null,
    Direccion      varchar(500) not null,
    Telefono       int          not null
    );

-- SQLINES LICENSE FOR EVALUATION USE ONLY
create table if not exists Cliente
(
    Id         int not null
    primary key,
    Contraseña varchar(100)  not null,
    Estado     int default 1 not null,
    PersonaID  varchar(10)   null,
    constraint Cliente_Persona_Identificacion_fk
    foreign key (PersonaID) references Persona (Identificacion)
    );

-- SQLINES LICENSE FOR EVALUATION USE ONLY
create table if not exists Productos
(
    id     int not null
    primary key,
    Nombre varchar(100)  not null,
    Active int default 1 null
    );


create table if not exists Cuenta
(
    Numero  int not null
    primary key,
    Tipo    int    default 1 null,
    saldo   double precision default 0 not null,
    Estado  int    default 1 not null,
    Cliente int              not null,
    constraint Cuenta_Cliente_Id_fk
    foreign key (Cliente) references Cliente (Id),
    constraint Cuenta_Productos_id_fk
    foreign key (Tipo) references Productos (id)
    );

-- SQLINES LICENSE FOR EVALUATION USE ONLY
create table if not exists Movimientos
(
    id     int not null
    primary key,
    cuenta int                                 not null,
    valor  double precision                              null,
    fecha  timestamp(0) default CURRENT_TIMESTAMP null,
    saldo  double precision                              null,
    constraint Movimientos_Cuenta_Numero_fk
    foreign key (cuenta) references Cuenta (Numero)
    );

