# Elimina la base anterior en caso de existir

drop database SistemaTec;

# Creacion y uso la base de datos

create database SistemaTec;
use SistemaTec;

# Creacion de las tablas Independientes

create table Usuario(noUsuario int not null auto_increment,
			usuario varchar(30) not null,
			contraseña varchar(30) not null,
			nombres varchar(50),
			apellidos varchar(50),
			genero varchar(1),
			correo varchar(50),
			fechaNac date,
			fotoPerfil longblob,
			constraint UsuarioPK primary key (noUsuario),
			constraint UsuarioCK check (genero = 'F' or genero = 'M')
			);

create table Carrera(cveCarrera varchar(5) not null,
			nombre varchar(50),
			constraint CarreraPK primary key (cveCarrera)
			);

create table Departamento(cveDepa varchar(5) not null,
			nombre varchar(100),
			constraint DepartamentoPK primary key (cveDepa)
			);

create table Prueba(cvePrueba varchar(5) not null,
			tipo varchar(100),
			constraint PruebaPK primary key (cvePrueba)
			);

# Creacion de tablas dependientes

create table Encuesta(noEncuesta int not null auto_increment,
			respuesta1 int(1),
			respuesta2 int(1),
			respuesta3 int(1),
			respuesta4 int(1),
			respuesta5 int(1),
			respuesta6 int(1),
			respuesta7 int(1),
			respuesta8 int(1),
			respuesta9 int(1),
			respuesta10 int(1),
			respuesta11 int(1),
			respuesta12 int(1),
			respuesta13 int(1),
			otrosSintomas varchar(100),
			noUsuario int not null,
			constraint EncuestaPK primary key (noEncuesta),
			constraint EncuestaFK foreign key (noUsuario) references Usuario (noUsuario)
			);

# Las 'cveAsignacion' tendran el formato (In###) {ej: ES001, PE001, ME001, AD001, MO001, DI001}

create table Asignacion(cveAsignacion varchar(5) not null,
			noUsuario int not null,
			nombre varchar(50),
			constraint AsignacionPK primary key (cveAsignacion, noUsuario),
			constraint AsignacionFK foreign key (noUsuario) references Usuario (noUsuario)
			);

create table Estudiante(noCont varchar(5) not null,
			cveAsignacion varchar(5) not null,
			noUsuario int not null,
			cveCarrera varchar(5) not null,
			constraint EstudiantePK primary key (noCont, cveAsignacion, noUsuario),
			constraint EstudianteFK1 foreign key (cveAsignacion, noUsuario) 
						references Asignacion (cveAsignacion, noUsuario),
			constraint EstudianteFK2 foreign key (cveCarrera) references Carrera (cveCarrera)
			);

create table Personal(noPersonal varchar(5) not null,
			cveAsignacion varchar(5) not null,
			noUsuario int not null,
			cveDepa varchar(5) not null,
			constraint PersonalPK primary key (noPersonal, cveAsignacion, noUsuario),
			constraint PersonalFK1 foreign key (cveAsignacion, noUsuario)
						references Asignacion (cveAsignacion, noUsuario),
			constraint PersonalFK2 foreign key (cveDepa) references Departamento (cveDepa)
			);

create table Medico(noCedula varchar(5) not null,
			cveAsignacion varchar(5) not null,
			noUsuario int not null,
			constraint MedicoPK primary key (noCedula),
			constraint MedicoFK foreign key (cveAsignacion, noUsuario) 
						references Asignacion (cveAsignacion, noUsuario)
			);

create table Administrador(noAdmin varchar(5) not null,
			cveAsignacion varchar(5) not null,
			noUsuario int not null,
			constraint AdministradorPK primary key (noAdmin),
			constraint AdministradorFK foreign key (cveAsignacion, noUsuario) 
						references Asignacion (cveAsignacion, noUsuario)
			);

create table Monitoreo(noMonitoreo varchar(5) not null,
			cveAsignacion varchar(5) not null,
			noUsuario int not null,
			constraint MonitoreoPK primary key (noMonitoreo),
			constraint MonitoreoFK foreign key (cveAsignacion, noUsuario) 
						references Asignacion (cveAsignacion, noUsuario)
			);

create table Directivo(noDirectivo varchar(5) not null,
			cveAsignacion varchar(5) not null,
			noUsuario int not null,
			constraint DirectivoPK primary key (noDirectivo),
			constraint DirectivoFK foreign key (cveAsignacion, noUsuario) 
						references Asignacion (cveAsignacion, noUsuario)
			);

# Tablas a partir de las solicitudes

create table Solicitud(noSolicitud int not null auto_increment,
			estado varchar(30),
			tipo varchar(30),
			cveAsignacion varchar(5) not null,
			noUsuario int not null,
			noCedula varchar(5) not null,
			constraint SolicitudPK primary key (noSolicitud),
			constraint SolicitudFK1 foreign key (cveAsignacion, noUsuario) 
						references Asignacion (cveAsignacion, noUsuario),
			constraint SolicitudFK2 foreign key (noCedula) references Medico (noCedula),
			constraint SolicitudCK1 check (tipo = 'Virtual' or tipo = 'Presencial'),
			constraint SolicitudCK2 check (estado = 'Aceptado' or estado = 'Rechazado' or estado ='Espera')
			);

create table Archivos(cveArchivos varchar(5) not null,
			foto longblob,
			video longblob,
			noSolicitud int not null,
			constraint ArchivosPK primary key (cveArchivos),
			constraint ArchivosFK foreign key (noSolicitud) references Solicitud (noSolicitud)
			);

create table Consulta(noConsulta int not null auto_increment,
			sintomas varchar(100),
			fecha date,
			tipo varchar(30),
			cveAsignacion varchar(5) not null,
			noUsuario int not null,
			noCedula varchar(5) not null,
			constraint ConsultaPK primary key (noConsulta),
			constraint ConsultaFK1 foreign key(cveAsignacion, noUsuario) 
						references Asignacion (cveAsignacion, noUsuario),
			constraint ConsultaFK2 foreign key (noCedula) references Medico (noCedula),
			constraint ConsultaCK1 check (tipo = 'Virtual' or tipo = 'Presencial')
			);

create table Receta(noReceta int not null auto_increment, 
			indicaciones varchar(100),
			noConsulta int not null,
			constraint RecetaPK primary key (noReceta),
			constraint RecetaFK foreign key (noConsulta) references Consulta (noConsulta)
			);

create table Medicamento(cveMedicamento varchar(5) not null,
			nombre varchar(50),
			cantidad int(5),
			noReceta int not null,
			constraint MedicamentoPK primary key (cveMedicamento),
			constraint MedicamentoFK foreign key (noReceta) references Receta (noReceta)
			);

create table Orden(noOrden int not null auto_increment,
			resultado varchar(100),
			noConsulta int not null,
			noCedula varchar(5) not null,
			cvePrueba varchar(5) not null,
			constraint primary key (noOrden),
			constraint OrdenFK1 foreign key (noConsulta) references Consulta (noConsulta),
			constraint OrdenFK2 foreign key (noCedula) references Medico (noCedula),
			constraint OrdenFK3 foreign key (cvePrueba) references Prueba (cvePrueba)
			);

create table ContactoInterno(noUsuario int not null,
			noOrden int not null,
			constraint ContactoInternoPK primary key (noUsuario, noOrden),
			constraint ContactoInternoFK1 foreign key (noUsuario) references Usuario (noUsuario),
			constraint ContactoInternoFK2 foreign key (noOrden) references Orden (noOrden)
			);

create table ContactoExterno(cveExterno varchar(5),
			nombre varchar(50),
			correo varchar(50),
			noOrden int not null,
			constraint ContactoExternoPK primary key (cveExterno),
			constraint ContactoExternoFK foreign key (noOrden)
						references Orden (noOrden)
			);

create table Alerta(noAlerta int not null auto_increment,
			descripcion varchar(100),
			noOrden int not null,
			constraint AlertaPK primary key (noAlerta),
			constraint AlertaFK foreign key (noOrden) references Orden (noOrden)
			);

create table AlertaMonitoreada(noAlertaMonitoreo int not null auto_increment,
			descripcion varchar(100),
			noOrden int not null,
			noMonitoreo varchar(5) not null,
			constraint AlertaMonitoreadaPK primary key (noAlertaMonitoreo),
			constraint AlertaMonitoreadaFK1 foreign key (noOrden) references Orden (noOrden),
			constraint AlertaMonitoreadaFK2 foreign key (noMonitoreo) references Monitoreo (noMonitoreo)
			);

# Crear conexion con el usuario de DataGrip

grant all privileges on SistemaTec.* to topicos_progra;

# Creacion de los registros de la tablas independientes

insert into Carrera values 
			('CA001', 'Lic. Administracion'),
			('CA002', 'Ing. Ambiental'),
			('CA003', 'Ing. Bioquimica'),
			('CA004', 'Ing. En Electronica'),
			('CA005', 'Ing. Gestion Empresarial'),
			('CA006', 'Ing. Industrial'),
			('CA007', 'Ing. Mecanica'),
			('CA008', 'Ing. Mecatronica'),
			('CA009', 'Ing. Quimica'),
			('CA010', 'Ing. Sistemas computacionales');

insert into Departamento values 
			('DE001', 'Actividades Extraescolares'),
			('DE002', 'Calidad'),
			('DE003', 'Centro de computo'),
			('DE004', 'Centro de informacion'),
			('DE005', 'Ciencias basicas'),
			('DE006', 'Comunicacion y Difusion'),
			('DE007', 'Departamento de Ciencias Economico Administrativas'),
			('DE009', 'Departamento de Ingenieria Bioquimica'),
			('DE010', 'Departamento de Ingenieria Electronica'),
			('DE011', 'Departamento de Ingenieria en Mecatronica'),
			('DE012', 'Departamento de Ingenieria en Sistemas Computacionales e Informatica'),
			('DE013', 'Departamento de Ingenieria Industrial'),
			('DE014', 'Departamento de Ingenieria Mecanica'),
			('DE015', 'Departamento de Ingenieria Quimica'),
			('DE016', 'Desarrollo Academico'),
			('DE017', 'Direccion'),
			('DE018', 'Division de Estudios de Posgrado e Investigacion'),
			('DE019', 'Division de Estudios Profesionales'),
			('DE020', 'Division de Estudios Profesionales / Propedeutico'),
			('DE021', 'Gestion Tecnologica y Vinculacion'),
			('DE022', 'Ingenieria Ambiental'),
			('DE023', 'Mantenimiento de Equipo'),
			('DE024', 'Planeacion, Programacion y Presupuestacion'),
			('DE025', 'Recursos Financieros'),
			('DE026', 'Recursos Humanos'),
			('DE027', 'Recursos Materiales y Servicios'),
			('DE028', 'Servicios Escolares'),
			('DE029', 'Sindicato'),
			('DE030', 'Subdireccion Academica'),
			('DE031', 'Subdireccion de Planeacion y Vinculacion'),
			('DE032', 'Subdireccion de Servicios Administrativos');

insert into Prueba values 
			('PR001', 'Prueba rapida de sangre'),
			('PR002', 'Prueba rapida de antigeno'),
			('PR003', 'Prueba PCR (Reaccion en cadena de la polimerasa)');



# Creacion de los usuarios 'admins' (No se pueden registrar)

#insert into Usuario () values ();



# Se usara una vista para generar reportes (pendiente...)

#create view Reportes();



# Exportar
-- mysqldump -u root -p SistemaTec > SistemaTec.sql
# Importar (debe estar creada la base de datos)
-- mysql -u root -p SistemaTec < SistemaTec.sql
