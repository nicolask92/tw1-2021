insert into usuario (DTYPE, activo, apellido, email, nombre, password, rol, plan)
values ("Cliente", true, "Cliente", "cliente@gmail.com", "Jorge", "cliente123", "Rol de cliente", "BASICO");

insert into calendario(id)
values    (1 );

insert into actividad (id, descripcion, precio, tipo, calendario_id)
values (1, "CrossFit alto impacto", 2000, "CROSSFIT", 1);

insert into actividad (id, descripcion, precio, tipo, calendario_id)
values (2, "Aquagym", 4000, "NATACION", 1);

insert into clase (cupoMaximo, diaClase, modalidad, actividad_id)
values(20        , "2021-10-03T10:15:30", "PRESENCIAL", 1);

insert into clase (cupoMaximo, diaClase, modalidad, actividad_id)
values(20        , "2021-10-06T10:15:30", "PRESENCIAL", 2);

insert into clase (cupoMaximo, diaClase, modalidad, actividad_id)
values(20        , "2021-10-10T10:15:30", "PRESENCIAL", 1);

insert into clase (cupoMaximo, diaClase, modalidad, actividad_id)
values(20        , "2021-10-20T10:15:30", "PRESENCIAL", 1);