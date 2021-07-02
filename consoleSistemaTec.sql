# -------------- Consultas para el codigo en IntelliJ --------------
# Las consultas son hechas con valores iniciales para que en el codigo sean usadas como parametros

# Validar usuario/correo y contrasena

-- Obtener la asignacion del usuario validado
select A.nombre
    from Asignacion A inner join Usuario U on A.noUsuario = U.noUsuario
    where (U.usuario = 'admin1' and U.contra = 'contraAD1')
       or (U.correo='exampleAD1@example.com' and U.contra = 'contraseñaAD1');
-- v2
select A.nombre
    from Asignacion A inner join Usuario U on A.noUsuario = U.noUsuario
    where (U.usuario = 'admin1' or U.correo = 'exampleAD1@example.com')
        and U.contra = 'contraAD1';

# Obtener Estudiantes y Personales
-- Estudiantes
select U.noUsuario, E.noCont, A.nombre, U.usuario, U.contra, U.nombres, U.apellidos
    from Usuario U inner join Asignacion A on U.noUsuario = A.noUsuario
        inner join Estudiante E on A.cveAsignacion = E.cveAsignacion and A.noUsuario = E.noUsuario;
-- Personal
select U.noUsuario, P.noPersonal, A.nombre, U.usuario, U.contra, U.nombres, U.apellidos
    from Usuario U inner join Asignacion A on U.noUsuario = A.noUsuario
        inner join Personal P on A.cveAsignacion = P.cveAsignacion and A.noUsuario = P.noUsuario;
-- Ambos
select U.noUsuario, A.nombre, U.usuario, U.contra, U.nombres, U.apellidos
    from Usuario U inner join Asignacion A on U.noUsuario = A.noUsuario
    where A.nombre = 'Estudiante'
       or A.nombre = 'Personal';

# Mostrar asignacion del modelo
select *
    from Usuario
    where noUsuario = '1'
      and usuario = 'medico1'
      and contra = 'contraME1';

# Mostrar Carrera
select nombre
    from Carrera;

# Mostrar la carrera de un Estudiante
select C.nombre
    from Estudiante E inner join Carrera C on E.cveCarrera = C.cveCarrera
    where E.noUsuario = 13;

# Mostrar el Departamento de un Personal
select D.nombre
    from Personal P inner join Departamento D on P.cveDepa = D.cveDepa
    where P.noUsuario = 18;

# Mostrar las alertas generales
select A.noAlerta, A.noOrden, A.descripcion
    from Consulta C inner join Orden O on C.noConsulta = O.noConsulta
                    inner join Alerta A on O.noOrden = A.noOrden
    where noUsuario = 14;

# Mostrar las alertas monitoreadas
select AM.noAlertaMonitoreo, AM.noOrden, AM.descripcion
    from Consulta C inner join Orden O on C.noConsulta = O.noConsulta
        inner join AlertaMonitoreada AM on O.noOrden = AM.noOrden
    where noUsuario = 13;

# Pasar como parametro el suaurio a su respectiva interfaz
select *
    from Usuario
    where usuario = 'medico1'
      and contra = 'contraME1';
# Obtener el noCont de un estudiante con el noUsuario
select E.noCont, E.cveAsignacion
    from Usuario U inner join Asignacion A on U.noUsuario = A.noUsuario
        inner join Estudiante E on A.cveAsignacion = E.cveAsignacion and A.noUsuario = E.noUsuario
    where E.noUsuario = 13;
# Obtener cveAsignacion con nousuario
select cveAsignacion
    from Asignacion
    where noUsuario = 13;
# Obtener la clave de la carrera con el nombre
select cveCarrera
    from Carrera
    where nombre = 'Lic. Administracion';
# Obtener la clave del departamento con el nombre
select cveDepa
    from Departamento
    where nombre = 'Actividades Extraescolares';
# Obtener los datos de las ordenes
select O.noOrden, O.resultado, U.nombres, P.tipo
    from Orden O inner join Consulta C on O.noConsulta = C.noConsulta
        inner join Medico M on C.noCedula = M.noCedula
        inner join Usuario U on C.noUsuario = U.noUsuario
        inner join Prueba P on O.cvePrueba = P.cvePrueba;

# Obtener toda la informacion de las encuestas
select * from Encuesta;
# Obtener las pruebas existentes
select tipo from Prueba;
# Mostrar solicitudes de consulta
select * from Solicitud;
# Mostrar las consultas
select * from Consulta;
# Mostrar solicitudes de consulta
select * from Solicitud where noUsuario = 13;
# Mostrar las consultas
select * from Consulta where noUsuario = 13;

# Mostrar las ordenes
select O.noOrden, O.resultado, O.noConsulta, O.noCedula, O.cvePrueba
    from Orden O inner join Consulta C on O.noConsulta = C.noConsulta
    where C.noUsuario = 13;

-- NO SALIO :C
select C.nombre, count(E.noUsuario)
    from Usuario inner join Asignacion A on Usuario.noUsuario = A.noUsuario
        inner join Estudiante E on A.cveAsignacion = E.cveAsignacion and A.noUsuario = E.noUsuario
        inner join Carrera C on E.cveCarrera = C.cveCarrera
        inner join Encuesta E2 on Usuario.noUsuario = E2.noUsuario
    group by E.noUsuario and C.nombre;

#Obtiene la cuenta de las consultas de cada medico
select M.noCedula, count(C.noConsulta) as consultas
    from Consulta C inner join Medico M on C.noCedula = M.noCedula
        inner join Usuario U on C.noUsuario = U.noUsuario
    group by M.noCedula;

-- No salio
select U.nombres, count(C.noConsulta)
    from Consulta C inner join Usuario U on C.noUsuario = U.noUsuario
    where U.noUsuario in (select M.noUsuario
                                from Consulta C inner join Medico M on C.noCedula = M.noCedula)
                            group by U.nombres;

# Obtiene el nombre del medico con el noCedula
select U.nombres, U.apellidos
    from Medico M inner join Usuario U on M.noUsuario = U.noUsuario
    where M.noCedula='NME01';

# Obtener * noCedula
select noCedula from Medico;
#  Obtener noCedula con parametro
select noCedula from Medico where noUsuario = 1;
# Obtener cvePrueba
select cvePrueba from Prueba where tipo = 'Prueba rapida de sangre';
# Obtener cvePrueba con parametro
select cveAsignacion from Asignacion where noUsuario = 13;

# Obtener una Orden con el noUsuario
select O.noOrden, O.resultado, O.noConsulta, O.noCedula, O.cvePrueba from Orden O inner join Consulta C on O.noConsulta = C.noConsulta where noUsuario = 16;
# Obtener una Receta con el noUsuario
select R.noReceta, R.indicaciones, R.noConsulta from Receta R inner join Consulta C on R.noConsulta = C.noConsulta where C.noUsuario = 13;

# Operaciones del CRUD
# Estudiante
insert into Usuario (usuario, contra, nombres, apellidos, genero, correo, fechaNac) values ('', '', '', '', '', '', '');
insert into Asignacion values ('', 0, '');
insert into Estudiante values ('', '', 0, '');
insert into Personal values  ('', '', 0, '');
insert into Solicitud (estado, tipo, cveAsignacion, noUsuario, noCedula) values('', '', '', 0, '');
update Solicitud set estado = 'Aceptado' where noSolicitud = 1;
insert into Encuesta (respuesta1, respuesta2, respuesta3, respuesta4, respuesta5, respuesta6, respuesta7, respuesta8, respuesta9, respuesta10, respuesta11, respuesta12, respuesta13, otrosSintomas, noUsuario) values (0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, null, 13);

# Conusltas para dashboard
# 1 Mostrar cuantos Usuarios Contagiados hay de cada Carrera (Estudiantes)
-- v1 obtiene los estudiantes de cada carrera
select C.nombre, count(E.cveCarrera) from Carrera C inner join Estudiante E on C.cveCarrera = E.cveCarrera group by nombre;
-- v2
select C.nombre, count(E.cveCarrera) as contagiados from Carrera C inner join Estudiante E on C.cveCarrera = E.cveCarrera inner join Asignacion A on E.cveAsignacion = A.cveAsignacion and E.noUsuario = A.noUsuario inner join Consulta C2 on A.cveAsignacion = C2.cveAsignacion and A.noUsuario = C2.noUsuario inner join Orden O on C2.noConsulta = O.noConsulta where O.resultado = 'Contagiado' group by C.nombre;
# 2 Mostrar cuantos Usuarios Contagiados hay de cada Departamento (Personal)
select D.nombre, count(P.cveDepa) as contagiados from Departamento D inner join Personal P on D.cveDepa = P.cveDepa inner join Asignacion A on P.cveAsignacion = A.cveAsignacion and P.noUsuario = A.noUsuario inner join Consulta C on A.cveAsignacion = C.cveAsignacion and A.noUsuario = C.noUsuario inner join Orden O on C.noConsulta = O.noConsulta where O.resultado = 'Contagiado' group by D.nombre;
# 3 Estudiantes contagiados
select U.nombres, U.apellidos, C.fecha, O.resultado, C2.nombre from Estudiante E inner join Asignacion A on E.cveAsignacion = A.cveAsignacion and E.noUsuario = A.noUsuario inner join Consulta C on A.cveAsignacion = C.cveAsignacion and A.noUsuario = C.noUsuario inner join Orden O on C.noConsulta = O.noConsulta inner join Usuario U on A.noUsuario = U.noUsuario inner join Carrera C2 on E.cveCarrera = C2.cveCarrera where O.resultado = 'Contagiado';
# 4 Personalcontagiado
select U.nombres, U.apellidos, C.fecha, O.resultado, D.nombre from Personal P inner join Asignacion A on P.cveAsignacion = A.cveAsignacion and P.noUsuario = A.noUsuario inner join Consulta C on A.cveAsignacion = C.cveAsignacion and A.noUsuario = C.noUsuario inner join Orden O on C.noConsulta = O.noConsulta inner join Usuario U on A.noUsuario = U.noUsuario inner join Departamento D on P.cveDepa = D.cveDepa where O.resultado = 'Contagiado';

# Vistas
# Reporte 1
select * from Reporte1Estudiantes;
# Reporte 2
select * from Reporte2Personal;
# Reporte 3
select * from Reporte3Carreras;
# Reporte 4
select * from Reporte4Departamentos;
# Grafica 1
select * from Grafica1;