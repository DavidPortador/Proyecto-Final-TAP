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

create table Departamento(cveDepartamento varchar(5) not null,
			nombre varchar(100),
			constraint DepartamentoPK primary key (cveDepartamento)
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
			noUsuario varchar(5) not null,
			constraint EncuestaPK primary key (noEncuesta),
			constraint EncuestaFK foreign key (noUsuario) references Usuario (noUsuario)
			);

# Las 'cveAsignacion' tendran el formato (In###) {ej: ES001, PE001, ME001, AD001, MO001, DI001}

create table Asignacion(cveAsignacion varchar(5) not null,
			noUsuario varchar(5) not null,
			nombre varchar(50),
			constraint AsignacionPK primary key (cveAsignacion, noUsuario),
			constraint AsignacionFK (noUsuario) references Usuario (noUsuario)
			);

create table Estudiante(noCont varchar(5) not null,
			cveAsignacion varchar(5) not null,
			noUsuario  varchar(5) not null,
			cveCarrera varchar(5) not null,
			constraint EstudiantePK primary key (noCont, cveAsignacion, noUsuario)
			constraint EstudianteFK1 (cveAsignacion, noUsuario) 
						references Asignacion (cveAsignacion, noUsuario),
			constraint EstudianteFK2 (cveCarrera) references Carrera (cveCarrera)
			);

create table Personal(noPersonal varchar(5) not null,
			cveAsignacion varchar(5) not null,
			noUsuario  varchar(5) not null,
			cveDepartamento varchar(5) not null,
			constraint PersonalPK primary key (noPersonal, cveAsignacion, noUsuario),
			constraint PersonalFK1 (cveAsignacion, noUsuario)
						references Asignacion (cveAsignacion, noUsuario),
			constraint PersonalFK2 (cveDepartamento) references Departamento (cveDepartamento)
			);

create table Medico(noCedula varchar(5) not null,
			cveAsignacion varchar(5) not null,
			cveUsuario  varchar(5) not null,
			constraint MedicoPK primary key (noCedula),
			constraint MedicoFK (cveAsignacion, cveUsuario) 
						references Asignacion (cveAsignacion, cveUsuario)
			);

create table Administrador(noAdmin varchar(5) not null,
			cveAsignacion varchar(5) not null,
			cveUsuario  varchar(5) not null,
			constraint AdministradorPK primary key (noAdmin),
			constraint AdministradorFK (cveAsignacion, cveUsuario) 
						references Asignacion (cveAsignacion, cveUsuario)
			);

create table Monitoreo(noMonitoreo varchar(5) not null,
			cveAsignacion varchar(5) not null,
			cveUsuario  varchar(5) not null,
			constraint MonitoreoPK primary key (noMonitoreo),
			constraint MonitoreoFK (cveAsignacion, cveUsuario) 
						references Asignacion (cveAsignacion, cveUsuario)
			);

create table Directivo(noDirectivo varchar(5) not null,
			cveAsignacion varchar(5) not null,
			cveUsuario  varchar(5) not null,
			constraint DirectivoPK primary key (noDirectivo),
			constraint DirectivoFK (cveAsignacion, cveUsuario) 
						references Asignacion (cveAsignacion, cveUsuario)
			);

# Tablas a partir de las solicitudes

create table Solicitud(noSolicitud int not null auto_increment,
			cveAsignacion varchar(5) not null,
			cveUsuario varchar(5) not null,
			noCedula varchar(5) not null,
			estado varchar(30),
			tipo varchar(30),
			constraint SolicitudPK primary key (noSolicitud),
			constraint SolicitudFK1(cveAsignacion, cveUsuario) 
						references Asignacion (cveAsignacion, cveUsuario),
			constraint SolicitudFK2 (noCedula) references Medico (noCedula),
			constraint SolicitudCK check (tipo = 'Virtual' or tipo = 'Presencial')
			);

create table Archivos(cveArchivos varchar(5) not null,
			noSolicitud int not null,
			foto longblob,
			video longblob,
			constraint ArchivosPK primary key (cveArchivos),
			constraint ArchivosFK (noSolicitud) references Solicitud (noSolicitud)
			);

create table Consulta(noConsulta
			);










# Se usara una vista para generar reportes (pendiente...)

create view Reportes();