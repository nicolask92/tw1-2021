insert into calendario(id)
values    (1 );

/*-----------HORARIOS-----------------------*/
insert into horario (id, hora_fin, hora_inicio)
values (1, "10:30:20", "12:30:20");

insert into horario (id, hora_fin, hora_inicio)
values (2, "14:30:20", "16:30:20");

insert into horario (id, hora_fin, hora_inicio)
values (3, "14:30:20", "16:30:20");

insert into horario (id, hora_fin, hora_inicio)
values (4, "14:30:20", "16:30:20");

/*-----------ACTIVIDADES-------------*/

insert into actividad (id, descripcion, precio, tipo, calendario_id)
values (1, "CrossFit alto impacto", 2000, "CROSSFIT", 1);

insert into actividad (id, descripcion, precio, tipo, calendario_id)
values (2, "Aquagym", 4000, "NATACION", 1);

/*----------CLASES OCTUBRE-----------*/

insert into clase (cupoMaximo, diaClase, modalidad, actividad_id, horario_id)
values(20        , "2021-10-03T10:15:30", "PRESENCIAL", 1, 1);

insert into clase (cupoMaximo, diaClase, modalidad, actividad_id, horario_id)
values(20        , "2021-10-06T10:15:30", "PRESENCIAL", 2, 2);

insert into clase (cupoMaximo, diaClase, modalidad, actividad_id, horario_id)
values(20        , "2021-10-10T10:15:30", "PRESENCIAL", 1, 3);

insert into clase (cupoMaximo, diaClase, modalidad, actividad_id, horario_id)
values(20        , "2021-10-20T10:15:30", "PRESENCIAL", 1, 4);

insert into clase (cupoMaximo, diaClase, modalidad, actividad_id, horario_id)
values(20        , "2021-10-20T14:15:30", "PRESENCIAL", 2, 3);

insert into clase (cupoMaximo, diaClase, modalidad, actividad_id, horario_id)
values(20        , "2021-10-27T23:15:30", "PRESENCIAL", 1, 4);

insert into clase (cupoMaximo, diaClase, modalidad, actividad_id, horario_id)
values(20        , "2021-10-29T02:15:30", "PRESENCIAL", 1, 4);

insert into clase (cupoMaximo, diaClase, modalidad, actividad_id, horario_id)
values(20        , "2021-10-30T10:15:30", "PRESENCIAL", 1, 4);

/*----------CLASES NOVIEMBRE-----------*/

insert into clase (cupoMaximo, diaClase, modalidad, actividad_id, horario_id)
values(20        , "2021-11-03T10:15:30", "PRESENCIAL", 1, 1);

insert into clase (cupoMaximo, diaClase, modalidad, actividad_id, horario_id)
values(20        , "2021-11-06T10:15:30", "PRESENCIAL", 2, 2);

insert into clase (cupoMaximo, diaClase, modalidad, actividad_id, horario_id)
values(20        , "2021-11-10T10:15:30", "PRESENCIAL", 1, 3);

insert into clase (cupoMaximo, diaClase, modalidad, actividad_id, horario_id)
values(20        , "2021-11-20T10:15:30", "PRESENCIAL", 1, 4);

insert into clase (cupoMaximo, diaClase, modalidad, actividad_id, horario_id)
values(20        , "2021-11-20T14:15:30", "PRESENCIAL", 2, 3);

insert into clase (cupoMaximo, diaClase, modalidad, actividad_id, horario_id)
values(20        , "2021-11-27T23:15:30", "PRESENCIAL", 1, 4);

insert into clase (cupoMaximo, diaClase, modalidad, actividad_id, horario_id)
values(20        , "2021-11-29T13:15:30", "PRESENCIAL", 1, 4);

insert into clase (cupoMaximo, diaClase, modalidad, actividad_id, horario_id)
values(20        , "2021-11-30T10:15:30", "PRESENCIAL", 1, 4);

insert into clase (cupoMaximo, diaClase, modalidad, actividad_id, horario_id)
values(20        , "2021-11-26T10:15:30", "PRESENCIAL", 1, 4);
insert into clase (cupoMaximo, diaClase, modalidad, actividad_id, horario_id)
values(20        , "2021-11-24T10:15:30", "PRESENCIAL", 1, 4);
insert into clase (cupoMaximo, diaClase, modalidad, actividad_id, horario_id)
values(20        , "2021-11-25T10:15:30", "PRESENCIAL", 1, 4);

/*--------------------------CLASES DICIEMBRE----------------------------------*/
insert into clase (cupoMaximo, diaClase, modalidad, actividad_id, horario_id)
values(20        , "2021-12-03T10:15:30", "PRESENCIAL", 1, 1);

insert into clase (cupoMaximo, diaClase, modalidad, actividad_id, horario_id)
values(20        , "2021-12-06T10:15:30", "PRESENCIAL", 2, 2);

insert into clase (cupoMaximo, diaClase, modalidad, actividad_id, horario_id)
values(20        , "2021-12-09T23:59:30", "PRESENCIAL", 1, 3);

insert into clase (cupoMaximo, diaClase, modalidad, actividad_id, horario_id)
values(20        , "2021-12-09T10:15:30", "PRESENCIAL", 2, 3);

insert into clase (cupoMaximo, diaClase, modalidad, actividad_id, horario_id)
values(20        , "2021-12-10T10:15:30", "PRESENCIAL", 1, 3);

insert into clase (cupoMaximo, diaClase, modalidad, actividad_id, horario_id)
values(20        , "2021-12-20T10:15:30", "PRESENCIAL", 1, 4);

insert into clase (cupoMaximo, diaClase, modalidad, actividad_id, horario_id)
values(20        , "2021-12-20T14:15:30", "PRESENCIAL", 2, 3);

insert into clase (cupoMaximo, diaClase, modalidad, actividad_id, horario_id)
values(20        , "2021-12-27T23:15:30", "PRESENCIAL", 1, 4);

insert into clase (cupoMaximo, diaClase, modalidad, actividad_id, horario_id)
values(20        , "2021-12-29T13:15:30", "PRESENCIAL", 1, 4);

insert into clase (cupoMaximo, diaClase, modalidad, actividad_id, horario_id)
values(20        , "2021-12-30T10:15:30", "PRESENCIAL", 1, 4);

insert into clase (cupoMaximo, diaClase, modalidad, actividad_id, horario_id)
values(20        , "2021-12-24T10:15:30", "PRESENCIAL", 2, 4);

insert into clase (cupoMaximo, diaClase, modalidad, actividad_id, horario_id)
values(20        , "2021-12-24T10:15:30", "PRESENCIAL", 1, 4);

insert into clase (cupoMaximo, diaClase, modalidad, actividad_id, horario_id)
values(20        , "2021-12-25T10:15:30", "PRESENCIAL", 1, 4);

/*------------------CLASE ENERO 2022------------------------------------*/
insert into clase (cupoMaximo, diaClase, modalidad, actividad_id, horario_id)
values(20        , "2022-1-03T10:15:30", "PRESENCIAL", 1, 1);

insert into clase (cupoMaximo, diaClase, modalidad, actividad_id, horario_id)
values(20        , "2022-1-06T10:15:30", "PRESENCIAL", 2, 2);

insert into clase (cupoMaximo, diaClase, modalidad, actividad_id, horario_id)
values(20        , "2022-1-10T10:15:30", "PRESENCIAL", 1, 3);

insert into clase (cupoMaximo, diaClase, modalidad, actividad_id, horario_id)
values(20        , "2022-1-20T10:15:30", "PRESENCIAL", 1, 4);

insert into clase (cupoMaximo, diaClase, modalidad, actividad_id, horario_id)
values(20        , "2022-1-20T14:15:30", "PRESENCIAL", 2, 3);

insert into clase (cupoMaximo, diaClase, modalidad, actividad_id, horario_id)
values(20        , "2022-1-27T23:15:30", "PRESENCIAL", 1, 4);

insert into clase (cupoMaximo, diaClase, modalidad, actividad_id, horario_id)
values(20        , "2022-1-29T13:15:30", "PRESENCIAL", 1, 4);

insert into clase (cupoMaximo, diaClase, modalidad, actividad_id, horario_id)
values(20        , "2022-1-28T10:15:30", "PRESENCIAL", 1, 4);

insert into clase (cupoMaximo, diaClase, modalidad, actividad_id, horario_id)
values(20        , "2022-1-24T10:15:30", "PRESENCIAL", 1, 4);

insert into clase (cupoMaximo, diaClase, modalidad, actividad_id, horario_id)
values(20        , "2022-1-24T10:15:30", "PRESENCIAL", 1, 4);

insert into clase (cupoMaximo, diaClase, modalidad, actividad_id, horario_id)
values(20        , "2022-1-25T10:15:30", "PRESENCIAL", 1, 4);


/*----------------------------------------------------------------------------------*/

insert into diayhorario (id, dia, horario_id)
values (1, "Lunes", 1);

insert into actividad_diayhorario (Actividad_id, diaYHorario_id)
values (1, 1);

/*-------USUARIOS-----------*/

insert into usuario (DTYPE, activo, apellido, email, nombre, password, rol)
values ("Cliente", true, "Cliente", "cliente@gmail.com", "Jorge", "cliente123", "Rol de cliente");

insert into usuario  (id, DTYPE, activo, apellido, email, nombre, password, rol)
values (2, "Entrenador", true, "Entrenador 1", "entranador@gmail.com", "Lopez", "entrenador123", "Rol de entrenador");

insert into usuario  (id, DTYPE, activo, apellido, email, nombre, password, rol)
values (3, "Entrenador", true, "Entrenador 2", "entranador2@gmail.com", "Luis", "entrenador123", "Rol de entrenador");

insert into usuario(DTYPE, activo, apellido, email, nombre, password, rol)
values ("Cliente", true, "clienteSinPlan", "cliente2@gmail.com", "Manuel", "cliente123", "Rol de cliente");

insert into usuario(DTYPE, activo, apellido, email, nombre, password, rol)
values ("Cliente", true, "clientePlanEstandar", "clienteestandar@gmail.com", "Pedro", "cliente123", "Rol de cliente");

insert into usuario(DTYPE, activo, apellido, email, nombre, password, rol)
values ("Cliente", true, "clientePlanPremium", "clientepremium@gmail.com", "Roberto", "cliente123", "Rol de cliente");

INSERT INTO pagos(id, activo, anio,fechaDeFinalizacion, mes, plan, cliente_id) VALUES (1, 1,'2021','2021-12-30',11,'NINGUNO',1);
INSERT INTO pagos(id, activo, anio,fechaDeFinalizacion, mes, plan, cliente_id) VALUES (2, 1,'2021','2021-12-30',11,'NINGUNO',4);
INSERT INTO pagos(id, activo, anio,fechaDeFinalizacion, mes, plan, cliente_id) VALUES (3, 1,'2021','2021-12-30',11,'NINGUNO',5);
INSERT INTO pagos(id, activo, anio,fechaDeFinalizacion, mes, plan, cliente_id) VALUES (4, 1,'2021','2021-12-30',11,'NINGUNO',6);
