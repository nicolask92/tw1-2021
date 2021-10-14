
insert into usuario (DTYPE, activo, apellido, email, nombre, password, rol, plan)
values ("Cliente", true, "Cliente", "cliente@gmail.com", "Jorge", "cliente123", "Rol de cliente", "BASICO");

insert into calendario(id)
values    (1 );

insert into horario (id, horaFin, horaInicio)
values (1, "10:30:20", "12:30:20");

insert into horario (id, horaFin, horaInicio)
values (2, "14:30:20", "16:30:20");

insert into horario (id, horaFin, horaInicio)
values (3, "14:30:20", "16:30:20");

insert into horario (id, horaFin, horaInicio)
values (4, "14:30:20", "16:30:20");

insert into actividad (id, descripcion, precio, tipo, calendario_id)
values (1, "CrossFit alto impacto", 2000, "CROSSFIT", 1);

insert into actividad (id, descripcion, precio, tipo, calendario_id)
values (2, "Aquagym", 4000, "NATACION", 1);

insert into clase (cupoMaximo, diaClase, modalidad, actividad_id, horario_id)
values(20        , "2021-10-03T10:15:30", "PRESENCIAL", 1, 1);

insert into clase (cupoMaximo, diaClase, modalidad, actividad_id, horario_id)
values(20        , "2021-10-06T10:15:30", "PRESENCIAL", 2, 2);

insert into clase (cupoMaximo, diaClase, modalidad, actividad_id, horario_id)
values(20        , "2021-10-10T10:15:30", "PRESENCIAL", 1, 3);

insert into clase (cupoMaximo, diaClase, modalidad, actividad_id, horario_id)
values(20        , "2021-10-20T10:15:30", "PRESENCIAL", 1, 4);

insert into diayhorario (id, dia, horario_id)
values (1, "Lunes", 1);

insert into actividad_diayhorario (Actividad_id, diaYHorario_id)
values (1, 1);

INSERT INTO turno(id, asisitio, fechaYHoraDeReserva, clase_id, cliente_id) VALUES (1, 'false', '2021-10-03 10:15:30.000000', 4, 1);

insert into usuario  (id, DTYPE, activo, apellido, email, nombre, password, rol)
values (2, "Entrenador", true, "Entrenador 1", "entranador@gmail.com", "Lopez", "entrenador123", "Rol de entrenador");

insert into usuario  (id, DTYPE, activo, apellido, email, nombre, password, rol)
values (3, "Entrenador", true, "Entrenador 2", "entranador2@gmail.com", "Luis", "entrenador123", "Rol de entrenador");


insert into clase_usuario (Clase_id, profesores_id, clientes_id)
values (1, 2, 1);

insert into clase_usuario (Clase_id, profesores_id, clientes_id)
values (2, 3, 1);

