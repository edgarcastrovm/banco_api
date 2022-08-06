--drop database banco 
create database banco owner postgres;
create schema api;

--drop table api.persona;
create table api.persona(
id serial primary key,
nombre varchar(50),
genero varchar(15),
edad int,
identificacion varchar(15),
direccion varchar(200),
telefono varchar(30),
estado boolean
);
comment on table api.persona is 'Tabla que almacena los datos de la persona';
comment on column api.persona.id is 'Identificador de la tabla';
comment on column api.persona.nombre is 'Nombre completo de la persona';
comment on column api.persona.genero is 'Género con el cual se identifica la persona';
comment on column api.persona.edad is 'Edad de la persona registrada';
comment on column api.persona.identificacion is 'Número de identificación de la persona, puede ser cédula, pasaporte';
comment on column api.persona.direccion is 'Dirección donde reside la persona';
comment on column api.persona.telefono is 'Número de teléfono de la persona';

--drop table api.cliente;
create table api.cliente(
clienteid serial primary key,
contrasenia varchar(200),
estado boolean,
id_persona int,
FOREIGN KEY (id_persona)
      REFERENCES api.persona (id)
);
comment on table api.cliente is 'Tabla que almacena los datos del cliente';
comment on column api.cliente.clienteid is 'Id único de la tabla que identifica al cliente';
comment on column api.cliente.contraseña is 'Contraseña del cliente, dato encriptado';
comment on column api.cliente.estado is 'El estado del cliente';

--drop table api.cuenta;
create table api.cuenta(
id serial primary key,
numero_cuenta varchar(30),
tipo_cuenta varchar(30),
saldo_inicial decimal(18,4),
estado boolean,
clienteid int,
FOREIGN KEY (clienteid)
      REFERENCES api.cliente (clienteid)
);
comment on table api.cuenta is 'Tabla que almacena los datos de la cuenta';
comment on column api.cuenta.id is 'Id de la tabla';
comment on column api.cuenta.numero_cuenta is 'Número que identifica a la cuenta';
comment on column api.cuenta.tipo_cuenta is 'Identifica el tipo de cuenta del registro';
comment on column api.cuenta.saldo_inicial is 'Valor del saldo inicial de la cuenta';
comment on column api.cuenta.estado is 'Estado de la cuenta';

--drop table api.movimientos;
create table api.movimientos(
id serial primary key,
fecha timestamp,
tipo_movimiento varchar(15),
saldo_inicial numeric(18, 4) NULL,
valor decimal(18,4),
saldo decimal(18,4),
id_cuenta int,
estado boolean,
FOREIGN KEY (id_cuenta)
      REFERENCES api.cuenta (id)
);
comment on column api.movimientos.id is 'Id de la tabla';
comment on column api.movimientos.fecha is 'Marca temporal de cuando se realiza el movimiento';
comment on column api.movimientos.tipo_movimiento is 'Tipo de movimiento realizado';
comment on column api.movimientos.valor is 'Valor del movimiento';
comment on column api.movimientos.saldo is 'Saldo luego del movimiento';


CREATE OR REPLACE VIEW api.vw_detalle_movimiento
AS SELECT m.id AS mov_id,
    p.id AS per_id,
    cl.clienteid AS cli_id,
    cu.id AS cue_id,
    m.fecha,
    p.nombre,
    cu.numero_cuenta,
    cu.tipo_cuenta,
    m.saldo_inicial,
    p.estado,
    m.valor,
    m.saldo
   FROM api.persona p
     JOIN api.cliente cl ON p.id = cl.id_persona
     JOIN api.cuenta cu ON cl.clienteid = cu.clienteid
     JOIN api.movimientos m ON cu.id = m.id_cuenta;
